package actions;

import application.Main;
import player.Recipe;

public class Craft {
	
	public static void execute(Recipe recipe) {
//		Main.player.get(AIC.class).changeBeh(new CraftingState(recipe));
		EndTurn.execute(Main.player, ActionType.CRAFT);
	}

}
