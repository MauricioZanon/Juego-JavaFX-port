package actions;

import effects.Effects;
import main.Entity;
import tile.Tile;

public abstract class Walk {
	
	public static void execute(Entity actor, Tile nextTile){
		Effects.move(actor, nextTile);
		EndTurn.execute(actor, ActionType.WALK);
	}

}
