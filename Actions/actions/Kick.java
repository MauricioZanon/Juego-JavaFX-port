package actions;

import application.Main;
import components.PositionC;
import effects.Effects;
import gameScreen.Console;
import main.Att;
import main.Entity;
import main.Type;
import tile.Tile;
import world.Direction;

public abstract class Kick {
	
	public static void execute(Entity actor, Tile kickedTile) {
		if(kickedTile.pos.equals(actor.get(PositionC.class))) {
			return;
		}
		if(kickedTile.get(Type.ACTOR) != null) {
			Entity kicked = kickedTile.get(Type.ACTOR);
			int kickerSTR = (int) actor.get(Att.STR);
			int kickedCON = (int) kicked.get(Att.CON);
			
			if(kickerSTR > kickedCON) {
				Console.addMessage("You pushed the " + kicked.name + ".\n");
				Effects.push(kicked, kickerSTR - kickedCON, Direction.get(actor.get(PositionC.class), kickedTile.pos));
			}
			else {
				Console.addMessage("The " + kicked.name + " resists your push attempt.\n");
			}
		}
		
		if(kickedTile.get(Type.DOOR) != null) {
			Console.addMessage("You kick the door open\n");
			Effects.shatter(kickedTile.get(Type.DOOR), kickedTile);
		}
		EndTurn.execute(Main.player, ActionType.ATTACK);
	}

}
