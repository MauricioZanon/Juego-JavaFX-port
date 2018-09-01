package actions;

import java.util.Set;

import RNG.RNG;
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
			System.out.println("Nombre de pocion desconocido " + item.name);
			break;
		}
		item.changeAttribute("quantity", -1);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
