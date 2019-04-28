package actions;

import application.Main;
import crafts.CraftRecipe;

public abstract class Craft {
	
	public static void execute(CraftRecipe recipe) {
//		Main.player.get(AIC.class).changeBeh(new CraftingState(recipe));
		EndTurn.execute(Main.player, ActionType.CRAFT);
	}

}
