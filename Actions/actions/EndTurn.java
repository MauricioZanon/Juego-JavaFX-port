package actions;

import components.AIC;
import components.MovementC;
import components.MovementC.MovementType;
import components.PositionC;
import components.StatusEffectsC;
import eventSystem.EventSystem;
import main.Entity;
import main.Type;
import time.Clock;

//TODO TEST
public abstract class EndTurn {
	
	public static void execute(Entity actor, ActionType type) {
		float actorSpeed = actor.get(type.asociatedStat);
		if(actorSpeed <= 0) actorSpeed = 1;
		float elapsedTime = 10 / actorSpeed;
		
		if(type == ActionType.WALK) {
			MovementType movType = actor.get(MovementC.class).movementType;
			elapsedTime *= actor.get(PositionC.class).getTile().getMovementCost(movType);
		}
		
		actor.get(AIC.class).nextTurn += elapsedTime;
		if(actor.type == Type.PLAYER) {
			EventSystem.setPlayerTurn(false);
//			Clock.advanceTime(elapsedTime);
			Clock.advanceTime(100);
		}
		
		actor.get(StatusEffectsC.class).diminishDurations();
	}
	
}
