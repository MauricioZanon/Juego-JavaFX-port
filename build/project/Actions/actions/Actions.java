package actions;

import components.PositionComponent;
import effects.Effects;
import main.Entity;
import main.Type;
import map.Map;
import map.Tile;
import world.Direction;

public abstract class Actions {

	public static void bump(PositionComponent startingPos, Direction dir) {
		PositionComponent nextPos = Map.getPosition(startingPos, dir);
		
		walk(startingPos, nextPos);
		
		
	}
	
	public static void walk(PositionComponent oldPos, PositionComponent newPos){
		Tile oldTile = oldPos.getTile();
		Entity actor = oldTile.get(Type.ACTOR);
		Effects.move(actor, newPos);
		endTurn(actor, ActionType.WALK);
	}
	
	
	public static void endTurn(Entity actor, ActionType type) {
		
	}
}
