package input;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import application.Main;
import components.PositionComponent;
import gameScreen.GameScreen;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import main.Entity;
import map.Map;
import playerViews.EntitiesLayer;
import tile.Tile;
import world.Direction;

public class SelectTargetListener implements EventHandler<KeyEvent>{
	
	private Tile selectedTile = null;
	private boolean isProjectile = false;
	private int area = 1;
	private int maxDistance = 1;
	
	private BiConsumer<Entity, Tile> action = null;

	public SelectTargetListener(boolean isProjectile, int area, int maxDistance, BiConsumer<Entity, Tile> action) {
		selectedTile = Main.player.get(PositionComponent.class).getTile();
		this.isProjectile = isProjectile;
		this.area = area;
		this.maxDistance = maxDistance;
		this.action = action;
	}
	
	@Override
	public void handle(KeyEvent e) {
		Tile newTile = selectedTile;
		PositionComponent playerPos = Main.player.get(PositionComponent.class);
		switch(e.getCode()) {
		case NUMPAD1:
			newTile = Map.getTile(selectedTile, Direction.SW);
			break;
		case NUMPAD2:
			newTile = Map.getTile(selectedTile, Direction.S);
			break;
		case NUMPAD3:
			newTile = Map.getTile(selectedTile, Direction.SE);
			break;
		case NUMPAD4:
			newTile = Map.getTile(selectedTile, Direction.W);
			break;
		case NUMPAD6:
			newTile = Map.getTile(selectedTile, Direction.E);
			break;
		case NUMPAD7:
			newTile = Map.getTile(selectedTile, Direction.NW);
			break;
		case NUMPAD8:
			newTile = Map.getTile(selectedTile, Direction.N);
			break;
		case NUMPAD9:
			newTile = Map.getTile(selectedTile, Direction.NE);
			break;
		case ESCAPE:
			GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
			break;
		case ENTER:
			action.accept(Main.player, selectedTile);
			GameScreen.getInstance().setOnKeyPressed(new GameScreenKeyboardListener());
			break;
		default:
			break;
		}
		e.consume();
		
		if(Map.getDistance(playerPos, newTile.getPos()) <= maxDistance) {
			selectedTile = newTile;
		}
		
		Set<Tile> markedArea = new HashSet<>();
		if(isProjectile) {
			markedArea.addAll(Map.getStraigthLine(Main.player.get(PositionComponent.class), selectedTile.getPos()));
		}
		if(area > 1) {
			markedArea.addAll(Map.getCircundatingAreaAsSet(area, selectedTile, true));
		}
		
		EntitiesLayer canvas = EntitiesLayer.getInstance();
		canvas.refresh();
		markedArea.forEach(t -> canvas.markTile(t));
		canvas.selectTile(selectedTile);
		
	}
	
}
