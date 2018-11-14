package actions;

import factories.FeatureFactory;
import main.Entity;
import main.Type;
import tile.Tile;

public class Close {
	
	/**
	 * pre: el tile tiene que contener una entidad que pueda ser cerrada
	 * post: se intenta cerrar la entidad
	 */
	public static void execute(Entity actor, Tile tile) {
		tile.put(FeatureFactory.createFeature(tile.get(Type.FEATURE).name.replace("open", "closed")));
		EndTurn.execute(actor, ActionType.USE_ITEM);
	}

}
