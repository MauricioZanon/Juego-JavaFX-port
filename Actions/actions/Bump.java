package actions;

import components.MovementC;
import components.PositionC;
import effects.Effects;
import factories.FeatureFactory;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

//TODO test
public abstract class Bump {
	
	public static void execute(PositionC startingPos, Direction dir) {
		PositionC nextPos = Map.getPosition(startingPos, dir);
		Tile nextTile = nextPos.getTile();
		Entity bumper = startingPos.getTile().get(Type.ACTOR);
		
		if(nextTile.has(Type.ACTOR)) {
			bumpActor(bumper, nextTile.get(Type.ACTOR));
		}
		else if(nextTile.has(Type.FEATURE) && !nextTile.isTransitable(bumper.get(MovementC.class).movementType)) {
			bumpFeature(bumper, nextTile);
		}
		else if(nextTile.has(Type.TERRAIN)) {
			bumpTerrain(startingPos, nextPos);
		}
	}
	
	private static void bumpActor(Entity bumper, Entity bumped) {//TODO: agregar condiciones segun allignement o faction
		Attack.execute(bumper, bumped);
	}
	
	private static void bumpFeature(Entity bumper, Tile bumpedTile) {
		Entity bumpedFeature = bumpedTile.get(Type.FEATURE);
		if(bumpedFeature.TYPE.is(Type.DOOR)) {
			String name = bumpedFeature.name;
			bumpedTile.put(FeatureFactory.createFeature(name.replace("closed", "open")));
		}
		EndTurn.execute(bumper, ActionType.WALK);
	}
	
	private static void bumpTerrain(PositionC startingPos, PositionC nextPos) {
		Entity bumper = startingPos.getTile().get(Type.ACTOR);
		if(nextPos.getTile().isTransitable(bumper.get(MovementC.class).movementType)) {
			walk(startingPos, nextPos);
		}
	}
	
	private static void walk(PositionC oldPos, PositionC newPos){
		Tile oldTile = oldPos.getTile();
		Entity actor = oldTile.get(Type.ACTOR);
		Effects.move(actor, newPos);
		EndTurn.execute(actor, ActionType.WALK);
	}

}
