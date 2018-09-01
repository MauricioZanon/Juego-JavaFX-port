package actions;

import java.util.Set;

import application.Main;
import components.PositionComponent;
import gameScreen.GameScreen;
import input.GameScreenKeyboardListener;
import map.Map;
import playerViews.EntitiesLayer;
import tile.Tile;
import world.Direction;

public class Examine {
	
	public static void execute(Tile tile) {
		System.out.println("examino el tile \n" + tile);
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
			case NUMPAD5:
				selectedTile = playerPos.getTile();
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
			if(selectedTile != null) execute(selectedTile);
			GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
			EntitiesLayer.getInstance().refresh();
		});
	}

}
