package actions;

import components.BodyComponent;
import components.ContainerComponent;
import console.Console;
import main.Entity;

public abstract class Wield {
	
	public static void execute(Entity actor, Entity item) {
		Entity removedWeapon = actor.get(BodyComponent.class).equip(item);
		item.changeAttribute("quantity", -1);
		if(removedWeapon != null) {
			actor.get(ContainerComponent.class).add(removedWeapon);
			Console.getInstance().addLiteralText("You put away the " + removedWeapon.name + " and wield the " + item.name + ".");
		}else {
			Console.getInstance().addLiteralText("You wield the " + item.name + ".");
		}
		EndTurn.execute(actor, ActionType.WALK);
	}

}
