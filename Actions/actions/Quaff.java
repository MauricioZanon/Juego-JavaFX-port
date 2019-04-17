package actions;

import java.util.Set;

import RNG.RNG;
import components.ContainerC;
import components.MovementC;
import components.MovementC.MovementType;
import components.PositionC;
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
			Set<Tile> area = Map.getCircundatingAreaAsSet(15, actor.get(PositionC.class).getTile(), true);
			MovementType movType = actor.get(MovementC.class).movementType;
			Tile selectedTile = RNG.getRandom(area, t-> t.isTransitable(movType));
			Effects.move(actor, selectedTile);
			break;
		default:
			break;
		}
		actor.get(ContainerC.class).remove(item.name, 1);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
