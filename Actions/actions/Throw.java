package actions;

import java.util.ArrayList;

import Menus.ItemList;
import application.Main;
import components.ContainerComponent;
import components.PositionComponent;
import console.Console;
import effects.Effects;
import gameScreen.GameScreen;
import input.SelectTargetListener;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public abstract class Throw {
	
	public static void execute(Entity actor, Tile target) {
		Entity thrownItem = actor.get(ContainerComponent.class).remove(ItemList.getInstance().getSelectedItem().name, 1);
		
		ArrayList<Tile> itemPath = Map.getStraigthLine(actor.get(PositionComponent.class), target.getPos());
		
		for(int i = 1; i < itemPath.size(); i++) {
			Tile t = itemPath.get(i);
			if(t.get(Type.ACTOR) != null) {
				t.put(thrownItem);
				Effects.receiveDamage(t.get(Type.ACTOR), thrownItem.get("damage"));
				Console.getInstance().addMessage("The " + thrownItem.name + " hits the " + t.get(Type.ACTOR).name);
				EndTurn.execute(actor, ActionType.WALK);
				return;
			}
		}
		itemPath.get(itemPath.size()-1).put(thrownItem);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	public static void setListener() {
		Entity thrownItem = ItemList.getInstance().getSelectedItem();
		int maxDistance = (int) ((Main.player.get("STR") / thrownItem.get("weight")) * 10);
		
		GameScreen.getInstance().setOnKeyPressed(new SelectTargetListener(true, 1, maxDistance, (e, t) -> execute(e, t)));
	}

}
