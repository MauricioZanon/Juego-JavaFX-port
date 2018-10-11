package actions;

import java.util.ArrayList;

import application.Main;
import components.ContainerComponent;
import components.PositionComponent;
import effects.Effects;
import gameScreen.Console;
import gameScreen.GameScreenController;
import main.Entity;
import main.Type;
import map.Map;
import system.RenderSystem;
import tile.Tile;

public abstract class Throw {
	
	private static String playerThrownItemName;
	
	public static void execute(Entity actor, Tile target, String thrownItemName) {
		ArrayList<Tile> itemPath = Map.getStraigthLine(actor.get(PositionComponent.class), target.getPos());
		Entity thrownItem = actor.get(ContainerComponent.class).remove(thrownItemName, 1);
		
		for(int i = 1; i < itemPath.size(); i++) {
			Tile t = itemPath.get(i);
			if(t.get(Type.ACTOR) != null) {
				t.put(thrownItem);
				Effects.receiveDamage(t.get(Type.ACTOR), thrownItem.get("damage"));
				Console.addMessage("The " + thrownItem.name + " hits the " + t.get(Type.ACTOR).name);
				EndTurn.execute(actor, ActionType.WALK);
				return;
			}
		}
		itemPath.get(itemPath.size()-1).put(thrownItem);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	public static void playerExecute(Entity player, Tile target) {
		execute(player, target, playerThrownItemName);
	}
	
	public static void setListener(Entity thrownItem) {
		playerThrownItemName = thrownItem.name;
		int maxDistance = (int) ((Main.player.get("STR") / thrownItem.get("weight")) * 10);
		
		GameScreenController controller = (GameScreenController) RenderSystem.getInstance().getController();
		controller.startTileSelection("throw", maxDistance, true, 4);
	}


}
