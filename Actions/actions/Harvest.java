package actions;

import java.util.ArrayDeque;

import components.ContainerC;
import components.PositionC;
import main.Entity;
import main.Flag;
import tile.Tile;

public abstract class Harvest {
	
	public static void execute(Entity harvester, Entity harvestable) {
		Tile harvesterTile = harvester.get(PositionC.class).getTile();
		ArrayDeque<Entity> items = harvestable.get(ContainerC.class).removeAll(e -> e.is(Flag.EDIBLE));
		harvesterTile.put(items);
		
		EndTurn.execute(harvester, ActionType.USE_ITEM);
	}

}
