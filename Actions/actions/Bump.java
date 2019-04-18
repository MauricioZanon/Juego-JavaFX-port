package actions;

import java.util.List;

import components.MovementC;
import components.PositionC;
import components.UsesC;
import components.UsesC.UseType;
import components.VisionC;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

//TODO test
public abstract class Bump {
	
	public static void execute(PositionC startingPos, Direction dir, boolean turn) {
		PositionC nextPos = Map.getPosition(startingPos, dir);
		Tile nextTile = nextPos.getTile();
		Entity bumper = startingPos.getTile().get(Type.ACTOR);
		
		if(turn && bumper.get(VisionC.class).faceDir != dir) {
			Turn.freeExecute(bumper, dir);
		}
		
		if(nextTile.has(Type.ACTOR)) {
			bumpActor(bumper, nextTile.get(Type.ACTOR));
		}
		else if(nextTile.has(Type.FEATURE) && !nextTile.isTransitable(bumper.get(MovementC.class).movementType)) {
			bumpFeature(bumper, nextTile);
		}
		else if(nextTile.has(Type.TERRAIN)) {
			bumpTerrain(bumper, nextTile);
		}
	}
	
	private static void bumpActor(Entity bumper, Entity bumped) {//TODO: agregar condiciones segun allignement o faction
		Attack.execute(bumper, bumped);
	}
	
	private static void bumpFeature(Entity bumper, Tile bumpedTile) {
		Entity bumpedFeature = bumpedTile.get(Type.FEATURE);
		if(bumpedFeature.type.is(Type.DOOR)) {
			List<UseType> uses = bumpedFeature.get(UsesC.class).uses;
			if(uses.contains(UseType.OPEN)) {
				Open.execute(bumper, bumpedFeature);
			}
			else if(uses.contains(UseType.OPEN)) {
				Close.execute(bumper, bumpedFeature);
			}
		}
	}
	
	private static void bumpTerrain(Entity bumper, Tile nextTile) {
		if(nextTile.isTransitable(bumper.get(MovementC.class).movementType)) {
			Walk.execute(bumper, nextTile);
		}
	}
	
}
