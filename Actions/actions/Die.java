package actions;

import java.util.Set;

import components.DropC;
import components.PositionC;
import eventSystem.EventSystem;
import main.Entity;
import main.Type;
import tile.Tile;

public abstract class Die {
	
	public static void execute(Entity actor) {
		EventSystem.getEntities().remove(actor);
		Tile t = actor.get(PositionC.class).getTile();
		t.remove(Type.ACTOR);

		Set<Entity> drops = actor.get(DropC.class).getOnDeathItems();
		drops.forEach(i -> t.put(i));
	}

}
