package actions;

import application.Main;
import crafts.BuildRecipe;
import factories.EntityFactory;
import gameScreen.Console;
import tile.Tile;

public abstract class Build {
	
	public static BuildRecipe recipe = null;

	public static void execute(Tile tile) {
		if(recipe.canBeBuiltIn(tile)) {
			tile.put(EntityFactory.create(recipe.name));
			recipe.consumeMaterials();
			EndTurn.execute(Main.player, ActionType.CRAFT);
		}else {
			Console.addMessage("You can't build there");
			EndTurn.execute(Main.player, 0);
		}
		
	}
	
}
