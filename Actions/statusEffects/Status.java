package statusEffects;

import main.Entity;

public abstract class Status {
	
	protected int duration;
	protected StTrigger trigger;
	protected boolean isDetrimental;
	protected Entity affected;
	
	public abstract void makeEffect();
	
	public int getDuration() {
		return duration;
	}
	
	public StTrigger getTrigger() {
		return trigger;
	}

	public boolean isDetrimental() {
		return isDetrimental;
	}

	public void diminishDuration() {
		duration--;
	}

}
