package actions;

import application.Main;
import components.PositionComponent;
import effects.Effects;
import factories.FeatureFactory;
import gameScreen.Console;
import main.Entity;
import main.Type;
import tile.Tile;
import world.Direction;

public abstract class Kick {
	
	public static void execute(Entity actor, Tile tile) {
		if(tile.getPos().equals(actor.get(PositionComponent.class))) {
			return;
		}
		if(tile.get(Type.ACTOR) != null) {
			Entity kicked = tile.get(Type.ACTOR);
			int kickerSTR = (int) actor.get("STR");
			int kickedCON = (int) kicked.get("CON");
			
			if(kickerSTR > kickedCON) {
				Console.addMessage("You pushed the " + kicked.name + ".\n");
				Effects.push(kicked, kickerSTR - kickedCON, Direction.get(actor.get(PositionComponent.class).getTile(), tile));
			}
			else {
				Console.addMessage("The " + kicked.name + " resists your push attempt.\n");
			}
		}
		else if(tile.get(Type.FEATURE) != null && tile.get(Type.FEATURE).ID == 2003) {
			Console.addMessage("You kick the door open\n");
			tile.put(FeatureFactory.createFeature("broken door"));
		}
		EndTurn.execute(Main.player, ActionType.ATTACK);
	}

}
