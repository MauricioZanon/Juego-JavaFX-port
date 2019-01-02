package actions;

import application.Main;
import behaviours.CraftingBeh;
import components.AIC;
import player.Recipe;

public class Craft {
	
	public static void execute(Recipe recipe) {
		Main.player.get(AIC.class).changeBeh(new CraftingBeh(recipe));
		EndTurn.execute(Main.player, ActionType.CRAFT);
	}

}
