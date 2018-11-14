package actions;

import components.AIComponent;
import components.StatusEffectsComponent;
import eventSystem.EventSystem;
import main.Entity;
import main.Type;
import time.Clock;

//TODO TEST
public abstract class EndTurn {
	
	public static void execute(Entity actor, ActionType type) {
		actor.get(StatusEffectsComponent.class).diminishDurations();
		
		float actorSpeed = actor.get(type.asociatedStat);
		if(actorSpeed <= 0) actorSpeed = 1;
		float elapsedTime = 10 / actorSpeed;
		
		actor.get(AIComponent.class).nextTurn += elapsedTime;
		if(actor.TYPE == Type.PLAYER) {
			EventSystem.setPlayerTurn(false);
			Clock.advanceTime(elapsedTime);
		}
	}
	
}
