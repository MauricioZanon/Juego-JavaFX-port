package actions;

import components.ContainerC;
import components.PositionC;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public abstract class PickUp {
	
	public static void execute(Entity actor, Direction dir) {
		PositionC actorPos = actor.get(PositionC.class);
		Tile itemTile = Map.getPosition(actorPos, dir).getTile();
		
		pickUp(actor, itemTile);
	}
	
	public static void execute(Entity actor) {
		pickUp(actor, actor.get(PositionC.class).getTile());
	}
	
	private static void pickUp(Entity actor, Tile tile) {
		Entity item = tile.remove(Type.ITEM);
		if(item != null) {
			actor.get(ContainerC.class).add(item);
			Console.addMessage("You pick up a -" + item.name + " -.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}else {
			Console.addMessage("There is nothing to pick up here.\n");
		}
	}
}
