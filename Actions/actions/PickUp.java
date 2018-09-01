package actions;

import components.ContainerComponent;
import components.PositionComponent;
import console.Console;
import main.Entity;
import main.Flags;
import map.Map;
import tile.Tile;
import world.Direction;

public abstract class PickUp {
	
	public static void execute(Entity actor, Direction dir) {
		PositionComponent actorPos = actor.get(PositionComponent.class);
		Tile itemTile = Map.getPosition(actorPos, dir).getTile();
		
		pickUp(actor, itemTile);
	}
	
	public static void execute(Entity actor) {
		pickUp(actor, actor.get(PositionComponent.class).getTile());
	}
	
	private static void pickUp(Entity actor, Tile tile) {
		for(Entity e : tile.getEntities()) {
			if(e.is(Flags.PICKUPABLE)) {
				actor.get(ContainerComponent.class).add(e);
				tile.remove(e.TYPE);
				Console.getInstance().addText("PlayerPicksUp", e.name);
				return;
			}
		}
		Console.getInstance().addText("NoItemHere");
	}
}
