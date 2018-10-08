package actions;

import components.BodyComponent;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;

public abstract class Wear {
	
	public static void execute(Entity actor, Entity armor) {
		Entity removedArmor = actor.get(BodyComponent.class).equip(armor);
		if(removedArmor != null) {
			Console.addMessage("You take off the -" + removedArmor.name + "- and wear the -" + armor.name + "-.\n",
											Color.WHITE, Color.CADETBLUE, Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}else {
			Console.addMessage("You wear the -" + armor.name + "-.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
