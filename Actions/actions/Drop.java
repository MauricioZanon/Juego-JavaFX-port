package actions;

import components.ContainerC;
import components.PositionC;
import main.Entity;
import tile.Tile;

public abstract class Drop {

	//TODO hacer que se puedan dropear varios items
	
	public static void execute(Entity actor, Entity item) {
		Tile actorTile = actor.get(PositionC.class).getTile();
		
		actorTile.put(actor.get(ContainerC.class).remove(item.name, 1).getFirst());
		
		EndTurn.execute(actor, ActionType.WALK);
	}
}
