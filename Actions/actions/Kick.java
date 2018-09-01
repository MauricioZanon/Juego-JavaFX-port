package actions;

import java.util.Set;

import application.Main;
import components.PositionComponent;
import console.Console;
import effects.Effects;
import factories.FeatureFactory;
import gameScreen.GameScreen;
import input.GameScreenKeyboardListener;
import main.Entity;
import main.Type;
import map.Map;
import playerViews.EntitiesLayer;
import tile.Tile;
import world.Direction;

public abstract class Kick {
	
	public static void execute(Entity actor, Tile tile) {
		if(tile.get(Type.ACTOR) != null) {
			Entity kicked = tile.get(Type.ACTOR);
			int kickerSTR = (int) actor.get("STR");
			int kickedCON = (int) kicked.get("CON");
			
			if(kickerSTR > kickedCON) {
				Console.getInstance().addLiteralText("You pushed the " + kicked.name + ".");
				Effects.push(kicked, kickerSTR - kickedCON, Direction.get(actor.get(PositionComponent.class).getTile(), tile));
			}
			else {
				Console.getInstance().addLiteralText("The " + kicked.name + " resists your push attempt.");
			}
		}
		else if(tile.get(Type.FEATURE) != null && tile.get(Type.FEATURE).ID == 2003) {
			Console.getInstance().addLiteralText("You kick the door open");
			tile.put(FeatureFactory.createFeature("broken door"));
		}
		EndTurn.execute(Main.player, ActionType.ATTACK);
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
			if(selectedTile != null) execute(Main.player, selectedTile);
			GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
			EntitiesLayer.getInstance().refresh();
		});
	}

}
