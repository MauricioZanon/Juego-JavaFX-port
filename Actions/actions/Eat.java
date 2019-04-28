package actions;

import application.Main;
import components.ContainerC;
import main.Att;
import main.Entity;
import player.PlayerInfo;

public abstract class Eat {

	public static void execute(Entity food) {
		PlayerInfo.HUNGER.set(PlayerInfo.HUNGER.floatValue() - food.get(Att.NUTRITION));
		Main.player.get(ContainerC.class).remove(food.name, 1);
		EndTurn.execute(Main.player, ActionType.USE_ITEM);
	}
	

}
