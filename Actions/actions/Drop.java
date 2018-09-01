package actions;

import components.ContainerComponent;
import components.PositionComponent;
import main.Entity;
import tile.Tile;

public abstract class Drop {

	//TODO hacer que se puedan dropear varios items
	
	public static void execute(Entity actor, Entity item) {
		Tile actorTile = actor.get(PositionComponent.class).getTile();
		
		actorTile.put(actor.get(ContainerComponent.class).remove(item.name));
		
		EndTurn.execute(actor, ActionType.WALK);
	}
}
