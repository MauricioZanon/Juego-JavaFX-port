package effects;

import java.util.Set;

import actions.Die;
import components.DropC;
import components.HealthC;
import components.MovementC;
import components.PositionC;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Att;
import main.Entity;
import main.Type;
import map.Map;
import player.PlayerInfo;
import tile.Tile;
import world.Direction;

public abstract class Effects {
	
	public static void move(Entity actor, PositionC newPos) { //TODO test
		Tile oldTile = actor.get(PositionC.class).getTile();
		Tile newTile = newPos.getTile();
		oldTile.remove(Type.ACTOR);
		newTile.put(actor);
		
		if(actor.TYPE == Type.PLAYER) {
			if(newTile.has(Type.ITEM)) {
				Entity item = newTile.get(Type.ITEM);
				Console.addMessage("There is a- " + item.name + "- on the ground.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
			}
			Map.refresh();
		}
	}
	
	public static void receiveDamage(Entity actor, float damage) {
		HealthC hp = actor.get(HealthC.class);
		float totalDamage = damage - actor.get(Att.DEFENSE);
		if(totalDamage > 0) {
			hp.changeCurHP(-damage);
		}
		if(actor.TYPE == Type.PLAYER) {
			PlayerInfo.CUR_HP.set(hp.getCurHP());
		}
		else if(hp.getCurHP() <= 0) {
			Die.execute(actor);
		}
	}
	
	public static void push(Entity entity, int distance, Direction dir) {
		for(int i = 0; i < distance; i++) {
			PositionC entityPos = entity.get(PositionC.class);
			PositionC nextPos = Map.getPosition(entityPos, dir);
			if(nextPos.getTile().isTransitable(entity.get(MovementC.class).movementType)) {
				move(entity, nextPos);
			}else {
				break;
			}
		}
	}
	
	public static void heal(Entity entity, float value) {
		HealthC hp = entity .get(HealthC.class);
		hp.changeCurHP(value);
		if(entity.TYPE == Type.PLAYER) {
			PlayerInfo.CUR_HP.set(hp.getCurHP());
		}
	}
	
	public static void shatter(Entity entity, Tile tile) {
		tile.remove(entity);
		if(entity.has(DropC.class)) {
			Set<Entity> items = entity.get(DropC.class).getOnBreakItems();
			items.forEach(i -> tile.put(i));
		}
	}

}
