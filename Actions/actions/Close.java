package actions;

import components.PositionC;
import factories.EntityFactory;
import main.Entity;
import tile.Tile;

public class Close {
	
	/**
	 * pre: el tile tiene que contener una entidad que pueda ser cerrada
	 * post: se intenta cerrar la entidad
	 */
	public static void execute(Entity actor, Entity closedEntity) {
		close(closedEntity);
		EndTurn.execute(actor, ActionType.USE_ITEM);
	}
	
	public static void close(Entity closedEntity) {
		Tile t = closedEntity.get(PositionC.class).getTile();
		t.remove(closedEntity);
		t.put(EntityFactory.create("closed door"));
	}

}
