package actions;

import components.AIC;
import components.VisionC;
import gameScreen.DrawUtils;
import main.Entity;
import main.Type;
import world.Direction;

public abstract class Turn {
	
	public static void freeExecute(Entity actor, Direction dir) {
		actor.get(VisionC.class).faceDir = dir;
		float elapsedTime = EndTurn.calculateTime(actor, ActionType.TURN);
		actor.get(AIC.class).nextTurn += elapsedTime;
		if(actor.type == Type.PLAYER) {
			DrawUtils.fullRedraw = true;
		}
	}
	
	public static void execute(Entity actor, Direction dir) {
		actor.get(VisionC.class).faceDir = dir;
		EndTurn.execute(actor, ActionType.WAIT);
		if(actor.type == Type.PLAYER) {
			DrawUtils.fullRedraw = true;
		}
	}

}
