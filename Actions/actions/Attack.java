package actions;

import java.util.Set;

import application.Main;
import components.PositionComponent;
import console.Console;
import effects.Effects;
import gameScreen.GameScreen;
import input.GameScreenKeyboardListener;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import map.Map;
import playerViews.EntitiesLayer;
import tile.Tile;
import world.Direction;

//TODO TEST
public abstract class Attack {
	
	public static void execute(Entity attacker, Entity receiver) {
		float damage = calculateDamage(attacker);
		Effects.receiveDamage(receiver, damage);
		
		if(attacker.TYPE == Type.PLAYER) {
			Console.getInstance().addMessage("You attack the -" + receiver.name + "-.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
		}
		else if(receiver.TYPE == Type.PLAYER) {
			Console.getInstance().addMessage("The -" + attacker.name + "- attacks you.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
		}
		EndTurn.execute(attacker, ActionType.ATTACK);
	}
	
	private static float calculateDamage(Entity attacker) {
		float damage = attacker.get("damage");
		damage *= (attacker.get("STR") / 10);
		return damage;
	}

	public static void setListener() {
		PositionComponent playerPos = Main.player.get(PositionComponent.class);
		Set<Tile> elegibleTiles = Map.getAdjacentTiles(playerPos.getTile());
		elegibleTiles.add(playerPos.getTile());

		elegibleTiles.forEach(t -> EntitiesLayer.getInstance().markTile(t));
		GameScreen.getInstance().setOnKeyPressed(e -> {
			Tile selectedTile = null;
			switch(e.getCode()) {
			case NUMPAD1:
				selectedTile = Map.getPosition(playerPos, Direction.SW).getTile();
				break;
			case NUMPAD2:
				selectedTile = Map.getPosition(playerPos, Direction.S).getTile();
				break;
			case NUMPAD3:
				selectedTile = Map.getPosition(playerPos, Direction.SE).getTile();
				break;
			case NUMPAD4:
				selectedTile = Map.getPosition(playerPos, Direction.W).getTile();
				break;
			case NUMPAD6:
				selectedTile = Map.getPosition(playerPos, Direction.E).getTile();
				break;
			case NUMPAD7:
				selectedTile = Map.getPosition(playerPos, Direction.NW).getTile();
				break;
			case NUMPAD8:
				selectedTile = Map.getPosition(playerPos, Direction.N).getTile();
				break;
			case NUMPAD9:
				selectedTile = Map.getPosition(playerPos, Direction.NE).getTile();
				break;
			default:
				break;
			}
			e.consume();
			//TODO agregar la opcion de atacar terrenos, features, etc
			Entity attacked = selectedTile.get(Type.ACTOR);
			if(attacked != null) execute(Main.player, attacked);
			GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
			EntitiesLayer.getInstance().refresh();
		});
	}
	
}
