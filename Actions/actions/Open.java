package actions;

import components.PositionC;
import factories.EntityFactory;
import main.Entity;
import tile.Tile;

public class Open {
	
	public static void execute(Entity actor, Entity opened) {
		open(opened);
		EndTurn.execute(actor, ActionType.USE_ITEM);
	}
	
	public static void open(Entity oepenedEntity) {
		Tile t = oepenedEntity.get(PositionC.class).getTile();
		t.remove(oepenedEntity);
		t.put(EntityFactory.create("open door"));
	}

}
