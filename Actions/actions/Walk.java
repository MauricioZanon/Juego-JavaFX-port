package actions;

import effects.Effects;
import main.Entity;
import tile.Tile;

public class Walk {
	
	public static void execute(Entity actor, Tile nextTile){
		Effects.move(actor, nextTile);
		EndTurn.execute(actor, ActionType.WALK);
	}

}
