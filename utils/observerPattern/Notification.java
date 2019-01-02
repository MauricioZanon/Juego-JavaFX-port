package observerPattern;

import java.util.function.Consumer;

import eventSystem.EventSystem;
import main.Entity;

public enum Notification {

	RECALCULATE_LIGHT(e -> {
		EventSystem.addLight(e);
	});
	
	private Consumer<Entity> action;
	
	Notification(Consumer<Entity> a) {
		action = a;
	}
	
	public void resolve(Entity e) {
		action.accept(e);
	}
	
}
