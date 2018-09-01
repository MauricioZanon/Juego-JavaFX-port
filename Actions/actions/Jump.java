package actions;

import java.util.Set;

import application.Main;
import components.PositionComponent;
import components.VisionComponent;
import effects.Effects;
import gameScreen.GameScreen;
import input.GameScreenKeyboardListener;
import main.Entity;
import main.Type;
import map.Map;
import playerViews.EntitiesLayer;
import tile.Tile;
import world.Direction;

public abstract class Jump {
	
	private static Tile selectedTile;
	
	public static void execute(Entity actor, Tile target) {
		Effects.move(actor, target.getPos());
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	public static void setListener() {
		Tile playerTile = Main.player.get(PositionComponent.class).getTile();
		selectedTile = playerTile;
		
		Set<Tile> elegibleTiles = Map.getCircundatingAreaAsSet(3, playerTile, true);
		elegibleTiles.removeIf(t -> !canBeJumpedTo(t));
		elegibleTiles.add(playerTile);
		elegibleTiles.forEach(t -> EntitiesLayer.getInstance().markTile(t));

		GameScreen.getInstance().setOnKeyPressed(e -> {
			EntitiesLayer.getInstance().refresh();
			elegibleTiles.forEach(t -> EntitiesLayer.getInstance().markTile(t));
			switch(e.getCode()) {
			case NUMPAD1:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.SW).getTile();
				break;
			case NUMPAD2:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.S).getTile();
				break;
			case NUMPAD3:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.SE).getTile();
				break;
			case NUMPAD4:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.W).getTile();
				break;
			case NUMPAD6:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.E).getTile();
				break;
			case NUMPAD7:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.NW).getTile();
				break;
			case NUMPAD8:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.N).getTile();
				break;
			case NUMPAD9:
				selectedTile = Map.getPosition(selectedTile.getPos(), Direction.NE).getTile();
				break;
				
			case ENTER:
				if(elegibleTiles.contains(selectedTile) && Main.player.get(VisionComponent.class).visionMap.contains(selectedTile)){
					execute(Main.player, selectedTile);
					GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
				}
				break;
			case ESCAPE:
				GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
				EntitiesLayer.getInstance().refresh();
				break;
			default:
				break;
			}
			e.consume();
			if (selectedTile != null) {
				EntitiesLayer.getInstance().selectTile(selectedTile);
			}
		});
	}
	
	private static boolean canBeJumpedTo(Tile t) {
		return t.isTransitable() && !t.has(Type.ACTOR) && Main.player.get(VisionComponent.class).visionMap.contains(t);
	}

}
