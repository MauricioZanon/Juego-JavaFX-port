package actions;

import application.Main;
import components.PositionComponent;
import effects.Effects;
import factories.FeatureFactory;
import gameScreen.Console;
import gameScreen.GameScreenController;
import main.Entity;
import main.Type;
import system.RenderSystem;
import tile.Tile;
import world.Direction;

public abstract class Kick {
	
	public static void execute(Entity actor, Tile tile) {
		if(tile.get(Type.ACTOR) != null) {
			Entity kicked = tile.get(Type.ACTOR);
			int kickerSTR = (int) actor.get("STR");
			int kickedCON = (int) kicked.get("CON");
			
			if(kickerSTR > kickedCON) {
				Console.addMessage("You pushed the " + kicked.name + ".");
				Effects.push(kicked, kickerSTR - kickedCON, Direction.get(actor.get(PositionComponent.class).getTile(), tile));
			}
			else {
				Console.addMessage("The " + kicked.name + " resists your push attempt.");
			}
		}
		else if(tile.get(Type.FEATURE) != null && tile.get(Type.FEATURE).ID == 2003) {
			Console.addMessage("You kick the door open");
			tile.put(FeatureFactory.createFeature("broken door"));
		}
		EndTurn.execute(Main.player, ActionType.ATTACK);
	}
	
	public static void setListener() {
		GameScreenController controller = (GameScreenController) RenderSystem.getInstance().getController();
		controller.startTileSelection("kick", 1, true, 1);
	}

}
