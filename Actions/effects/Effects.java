package effects;

import java.util.Set;

import actions.Die;
import components.BreakC;
import components.HealthC;
import components.MovementC;
import components.PositionC;
import components.StatusEffectsC;
import factories.ItemFactory;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Att;
import main.Entity;
import main.Type;
import map.Map;
import player.PlayerInfo;
import statusEffects.StTrigger;
import tile.Tile;
import world.Direction;

public abstract class Effects {
	
	public static void receiveDamage(Entity actor, float damage) {
		//TODO agregar modificador de hardness por el material de la armadura
		HealthC hp = actor.get(HealthC.class);
		float totalDamage = damage - actor.get(Att.DEFENSE);
		if(totalDamage > 0) {
			hp.changeCurHP(-damage);
			actor.get(StatusEffectsC.class).triggerStatus(StTrigger.RECEIVE_DAMAGE);
		}
		if(actor.type == Type.PLAYER) {
			PlayerInfo.CUR_HP.set(hp.getCurHP());
		}
		else if(hp.getCurHP() <= 0) {
			Die.execute(actor);
		}
	}
	
	public static void heal(Entity entity, float value) {
		HealthC hp = entity .get(HealthC.class);
		hp.changeCurHP(value);
		if(entity.type == Type.PLAYER) {
			PlayerInfo.CUR_HP.set(hp.getCurHP());
		}
	}
	
	public static void move(Entity actor, Tile newTile) { //TODO test
		Tile oldTile = actor.get(PositionC.class).getTile();
		oldTile.remove(Type.ACTOR);
		newTile.put(actor);
		
		if(actor.type == Type.PLAYER) {
			if(newTile.has(Type.ITEM)) {
				Entity item = newTile.get(Type.ITEM);
				Console.addMessage("There is a- " + item.name + "- on the ground.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
			}
			Map.refresh();
		}
	}
	
	public static void push(Entity entity, int distance, Direction dir) {
		for(int i = 0; i < distance; i++) {
			PositionC entityPos = entity.get(PositionC.class);
			Tile nextTile = Map.getPosition(entityPos, dir).getTile();
			if(nextTile.isTransitable(entity.get(MovementC.class).movementType)) {
				move(entity, nextTile);
			}else {
				break;
			}
		}
	}
	
	public static void shatter(Entity entity, Tile tile) {
		tile.remove(entity);
		if(entity.has(BreakC.class)) {
			Set<Entity> items = ItemFactory.getItems(entity.get(BreakC.class).items);
			items.forEach(i -> tile.put(i));
		}
	}

}
