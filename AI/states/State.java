package states;

import FOV.ShadowCasting;
import components.HealthC;
import components.StatusEffectsC;
import components.VisionC;
import main.Entity;
import statusEffects.StTrigger;

public abstract class State {
	
	protected Entity actor;
	
	public void update() {
		ShadowCasting.calculateFOV(actor);
		actor.get(StatusEffectsC.class).triggerStatus(StTrigger.START_TURN);
		actor.get(HealthC.class).regenerate();
	}
	
	public abstract void enterState();
	public abstract void exitState();
	
	protected boolean enemySighted() {
		return !actor.get(VisionC.class).enemyTiles.isEmpty();
	}
	
	public void setOwner(Entity actor) {
		this.actor = actor;
	}

}
