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
	
	/** 
	 * @param time: el tiempo para el siguiente turno en milisegundos
	 */
	public static void execute(Entity actor, float time) {
		endTurn(actor, time);
	}
	
	public static void execute(Entity actor, ActionType type) {
		float elapsedTime = calculateTime(actor, type);
		endTurn(actor, elapsedTime);
		
	}
	
	public static float calculateTime(Entity actor, ActionType type) {
		float actorSpeed = actor.get(type.asociatedStat);
		if(actorSpeed <= 0) actorSpeed = 1;
		float elapsedTime = type.timeNeeded*10 / actorSpeed;
		
		if(type == ActionType.WALK) {
			MovementType movType = actor.get(MovementC.class).movementType;
			elapsedTime *= actor.get(PositionC.class).getTile().getMovementCost(movType);
		}
		
		return elapsedTime;
	}
	
	private static void endTurn(Entity actor, float time) {
		actor.get(AIC.class).nextTurn += time;
		if(actor.type == Type.PLAYER) {
			EventSystem.setPlayerTurn(false);
			Clock.advanceTime(time);
//			Clock.advanceTime(100);
		}
		
		actor.get(StatusEffectsC.class).diminishDurations();
	}
	
}
