package actions;

import components.BodyComponent;
import console.Console;
import javafx.scene.paint.Color;
import main.Entity;

public abstract class Wear {
	
	public static void execute(Entity actor, Entity armor) {
		Entity removedArmor = actor.get(BodyComponent.class).equip(armor);
		if(removedArmor != null) {
			Console.getInstance().addMessage("You take off the -" + removedArmor.name + "- and wear the -" + armor.name + "-.\n",
											Color.WHITE, Color.CADETBLUE, Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}else {
			Console.getInstance().addMessage("You wear the -" + armor.name + "-.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
