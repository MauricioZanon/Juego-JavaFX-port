package actions;

import java.util.Set;

import components.DropComponent;
import components.PositionComponent;
import eventSystem.EventSystem;
import main.Entity;
import main.Type;
import tile.Tile;

public abstract class Die {
	
	public static void execute(Entity actor) {
		EventSystem.getEntities().remove(actor);
		Tile t = actor.get(PositionComponent.class).getTile();
		t.remove(Type.ACTOR);

		Set<Entity> drops = actor.get(DropComponent.class).getDrops();
		drops.forEach(i -> t.put(i));
		
	}

}
