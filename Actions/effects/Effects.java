package effects;

import actions.Die;
import components.HealthComponent;
import components.PositionComponent;
import console.Console;
import gameScreen.GameScreen;
import gameScreen.PlayerPosLabel;
import javafx.application.Platform;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public abstract class Effects {
	
	public static void move(Entity actor, PositionComponent newPos) { //TODO test
		Tile oldTile = actor.get(PositionComponent.class).getTile();
		Tile newTile = newPos.getTile();
		oldTile.remove(Type.ACTOR);
		newTile.put(actor);
		actor.addComponent(newPos);
		
		if(actor.TYPE == Type.PLAYER) {
			if(newTile.has(Type.ITEM)) {
				Entity item = newTile.get(Type.ITEM);
				Console.getInstance().addText("StandingOnItem", item.name);
			}
			Map.refresh();
			Platform.runLater(() -> PlayerPosLabel.getInstance().refresh());
		}
	}
	
	public static void receiveDamage(Entity actor, float damage) {
		HealthComponent hp = actor.get(HealthComponent.class);
		hp.curHP -= damage;
		if(actor.TYPE == Type.PLAYER) {
			Platform.runLater(() -> GameScreen.SIDE_BAR.refreshResourceBar("health"));
		}
		else if(hp.curHP <= 0) {
			Die.execute(actor);
		}
	}
	
	public static void push(Entity entity, int distance, Direction dir) {
		for(int i = 0; i < distance; i++) {
			PositionComponent entityPos = entity.get(PositionComponent.class);
			PositionComponent nextPos = Map.getPosition(entityPos, dir);
			if(nextPos.getTile().isTransitable()) {
				move(entity, nextPos);
			}else {
				break;
			}
		}
	}
	
	public static void heal(Entity entity, float value) {
		HealthComponent hp = entity .get(HealthComponent.class);
		hp.curHP += value;
		if(hp.curHP > hp.maxHP) {
			hp.curHP = hp.maxHP;
		}
		if(entity.TYPE == Type.PLAYER) {
			Platform.runLater(() -> GameScreen.SIDE_BAR.refreshResourceBar("health"));
		}
	}

}
