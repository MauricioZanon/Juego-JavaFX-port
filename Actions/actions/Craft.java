package actions;

import application.Main;
import components.ContainerC;
import crafts.CraftRecipe;
import factories.EntityFactory;

public abstract class Craft {
	
	public static void execute(CraftRecipe recipe) {
		Main.player.get(ContainerC.class).add(EntityFactory.create(recipe.name));
		EndTurn.execute(Main.player, ActionType.CRAFT);
	}

}
