package actions;

import components.BodyComponent;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;

public abstract class Wield {
	
	public static void execute(Entity actor, Entity item) {
		Entity removedWeapon = actor.get(BodyComponent.class).equip(item);
		if(removedWeapon != null) {
			Console.addMessage("You put away the -" + removedWeapon.name + "- and wield the -" + item.name + "-.\n",
											Color.WHITE, Color.CADETBLUE, Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}else {
			Console.addMessage("You wield the -" + item.name + "-.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}
		EndTurn.execute(actor, ActionType.WALK);
	}

}
