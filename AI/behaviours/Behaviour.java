package behaviours;

import FOV.ShadowCastingFOV;
import components.StatusEffectsComponent;
import main.Entity;
import statusEffects.StTrigger;

public abstract class Behaviour {
	
	protected Entity actor;
	
	public void update() {
		ShadowCastingFOV.getInstance().calculateVision(actor);
		actor.get(StatusEffectsComponent.class).triggerStatus(StTrigger.START_TURN);
	}

}
