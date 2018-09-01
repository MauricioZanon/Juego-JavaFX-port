package actions;

import java.util.Set;

import application.Main;
import components.PositionComponent;
import gameScreen.GameScreen;
import input.GameScreenKeyboardListener;
import main.Entity;
import main.Flags;
import map.Map;
import playerViews.EntitiesLayer;
import tile.Tile;
import world.Direction;

public abstract class Grab {
	
	public static void execute(Entity actor, Entity grabbedEntity) {
		// TODO implementar el grab como un status: cada vez que el actor se mueva la entidad agarrada la sigue (STR vs STRyCON)
		//		Si la entidad agarrada intenta moverse puede cancelarse el grab (STR vs STR)
		//		Si el actor se teletransporta la entidad agarrada se teletransporta también, lo mismo si se teletransporta la entidad agarrada
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
			Entity grabbed = selectedTile.get(Flags.DRAGGABLE);
			if(grabbed != null) execute(Main.player, grabbed);
			GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
			EntitiesLayer.getInstance().refresh();
		});
	}

}
