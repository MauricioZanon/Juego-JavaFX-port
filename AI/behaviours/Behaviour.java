package behaviours;

import FOV.ShadowCasting;
import components.HealthC;
import components.StatusEffectsC;
import main.Entity;
import statusEffects.StTrigger;

public abstract class Behaviour {
	
	protected Entity actor;
	
	public void update() {
		ShadowCasting.calculateFOV(actor);
		actor.get(StatusEffectsC.class).triggerStatus(StTrigger.START_TURN);
		actor.get(HealthC.class).regenerate();
	}

}
