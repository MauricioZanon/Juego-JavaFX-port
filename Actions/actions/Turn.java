package actions;

import components.VisionC;
import main.Entity;
import world.Direction;

public abstract class Turn {
	
	public static void execute(Entity actor, Direction dir) {
		actor.get(VisionC.class).faceDir = dir;
		EndTurn.execute(actor, ActionType.WAIT);
	}

}
