package playerViews;

import java.util.Set;

import application.Main;
import components.PositionComponent;
import components.VisionComponent;
import eventSystem.EventSystem;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import map.Map;
import tile.Tile;

public class EntitiesLayer extends PlayerView{
	
	private static EntitiesLayer instance = new EntitiesLayer();
	
	public static EntitiesLayer getInstance() {
		return instance;
	}

	private EntitiesLayer() {
		super(45);
		
		gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.CENTER);
	    gc.setFont(Font.font("Courier New", FontWeight.BLACK, tileSize));
	    
//	    Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/unifont-11.0.02.ttf"), tileSize);
//	    gc.setFont(font);
	    
	    setOnMouseMoved(e -> {
	    	if(EventSystem.waitingOnPlayerInput) {
	    		Platform.runLater(() -> {
	    			refresh();
	    			markTile(getTileUnderTheMouse(e.getX(), e.getY()));
	    		});
	    	}
	    	e.consume();
	    });
	}
	
	@Override
    public void refresh() {
		PositionComponent pos00 = getPos00();
		Tile[][] map = Map.getSquareAreaAsArray(pos00, tileQuantity, tileQuantity);
		Set<Tile> visibleTiles = Main.player.get(VisionComponent.class).visionMap;
		
		float halfTile = tileSize>>1;
		
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				Tile tile = map[i][j];
				if(visibleTiles.contains(tile)) {
					gc.setFill(tile.getBackColor());
					gc.fillRect(i*tileSize, j*tileSize, tileSize, tileSize); 
					
					gc.setFill(tile.getFrontColor());
					gc.fillText(tile.getASCII(), i*tileSize + halfTile, j*tileSize + halfTile);
				}
			}
		}
	}
	
}
