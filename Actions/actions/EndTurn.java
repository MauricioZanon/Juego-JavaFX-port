package actions;

import components.AIComponent;
import components.StatusEffectsComponent;
import eventSystem.EventSystem;
import main.Entity;
import time.Clock;

//TODO TEST
public abstract class EndTurn {
	
	public static void execute(Entity actor, ActionType type) {
		actor.get(StatusEffectsComponent.class).diminishDurations();
		actor.get(AIComponent.class).nextTurn += 6; //TODO vincular al tipo de accion
		if(actor.ID == -1) {
			EventSystem.waitingOnPlayerInput = false;
			Clock.advanceTime(60); //TODO el parametro que se pasa aca tiene que depender del tipo de accion y la velocidad del pj
		}
	}

}
