package statusEffects;

import main.Entity;

public abstract class Status {
	
	protected int duration;
	protected StTrigger trigger;
	protected boolean isDetrimental;
	protected String name;
	
	public abstract void makeEffect(Entity affected);
	
	public void setDuration(int d) {
		duration = d;
	}
	
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

	public String getName() {
		return name;
	}
	
	//TODO quitar el switch y hacer esto con Reflections
	public static Status get(String statusName) {
		return switch(statusName) {
		case "poisoned" -> new Poisoned(10);	
		default -> null; 
		};
	}

}
