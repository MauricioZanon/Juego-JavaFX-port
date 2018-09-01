package actions;

import components.PositionComponent;
import effects.Effects;
import factories.FeatureFactory;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

//TODO test
public abstract class Bump {
	
	public static void execute(PositionComponent startingPos, Direction dir) {
		PositionComponent nextPos = Map.getPosition(startingPos, dir);
		Tile nextTile = nextPos.getTile();
		Entity bumper = startingPos.getTile().get(Type.ACTOR);
		
		if(nextTile.has(Type.ACTOR)) {
			bumpActor(bumper, nextTile.get(Type.ACTOR));
		}
		else if(nextTile.has(Type.FEATURE) && !nextTile.isTransitable()) {
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
		switch(bumpedTile.get(Type.FEATURE).name) {
		case "closed door":
			bumpedTile.put(FeatureFactory.createFeature("open door"));
			break;
		}
		EndTurn.execute(bumper, ActionType.WALK);
	}
	
	private static void bumpTerrain(PositionComponent startingPos, PositionComponent nextPos) {
		if(nextPos.getTile().isTransitable()) {
			walk(startingPos, nextPos);
		}
	}
	
	private static void walk(PositionComponent oldPos, PositionComponent newPos){
		Tile oldTile = oldPos.getTile();
		Entity actor = oldTile.get(Type.ACTOR);
		Effects.move(actor, newPos);
		EndTurn.execute(actor, ActionType.WALK);
	}

}
