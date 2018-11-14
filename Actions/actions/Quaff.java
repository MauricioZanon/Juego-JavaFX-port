package actions;

import java.util.Set;

import RNG.RNG;
import components.ContainerComponent;
import components.PositionComponent;
import effects.Effects;
import main.Entity;
import map.Map;
import tile.Tile;

public abstract class Quaff {

	public static void execute(Entity actor, Entity item) {
		switch(item.name) {
		case "healing potion":
			Effects.heal(actor, 20);
			break;
		case "teleportation potion":
			Set<Tile> area = Map.getCircundatingAreaAsSet(15, actor.get(PositionComponent.class).getTile(), true);
			Tile selectedTile = RNG.getRandom(area, t-> t.isTransitable());
			Effects.move(actor, selectedTile.getPos());
			break;
		default:
			break;
		}
		actor.get(ContainerComponent.class).remove(item.name, 1);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
