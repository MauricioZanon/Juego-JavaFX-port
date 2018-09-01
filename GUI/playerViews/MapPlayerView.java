package playerViews;

import application.Main;
import components.PositionComponent;
import javafx.scene.paint.Color;
import main.Type;
import map.Map;
import tile.Tile;

public class MapPlayerView extends PlayerView{
	
	private static MapPlayerView instance = new MapPlayerView();
	
	public static MapPlayerView getInstance() {
		return instance;
	}

	private MapPlayerView() {
		super(210);
	}

	@Override
	public void refresh() {
//		long t = System.nanoTime();
	    PositionComponent pos00 = Main.player.get(PositionComponent.class).clone();
	    
	    pos00.coord[0] -= tileQuantity/2;
	    pos00.coord[1] -= tileQuantity/2;
	    
	    Tile[][] map = Map.getSquareAreaAsArray(pos00, tileQuantity, tileQuantity);
	    
	    for(int i = 0; i < map.length; i++) {
	    	for(int j = 0; j < map.length; j++) {
	    		Tile tile = map[i][j];
	    		if(tile.has(Type.ACTOR)) {
	    			gc.setFill(Color.CRIMSON);
	    		}else {
	    			gc.setFill(tile.getBackColor());
	    		}
	    		gc.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
	    	}
	    }
//	    System.out.println("mini map player view refresh time " + (System.nanoTime() - t));
		
	}

}
