package gameScreen;

import application.Main;
import components.PositionComponent;
import javafx.scene.layout.GridPane;
import map.Map;
import map.Tile;
import system.RenderSystem;

public class PlayerView extends GridPane{
	
	public static final int TILE_SIZE = 18;
	private static final int TILES_IN_CANVAS = 59;
	
	private static PlayerView instance = new PlayerView();
	
	public static PlayerView getInstance() {
		return instance;
	}

	private PlayerView() {
		double canvasHeight = RenderSystem.getStageHeight()-22;
		canvasHeight = canvasHeight - (canvasHeight%TILE_SIZE);
		setHeight(canvasHeight);
		
		double canvasWidth = RenderSystem.getStageHeight() - (RenderSystem.getStageHeight()%TILE_SIZE);
		setWidth(canvasWidth);
	}
	
//	public void refresh() {
//		long t = System.currentTimeMillis();
//		GraphicsContext gc = getGraphicsContext2D();
//		
//	    gc.setTextAlign(TextAlignment.CENTER);
//	    gc.setTextBaseline(VPos.CENTER);
//	    gc.setFont(Font.font("Courier New", FontWeight.BLACK, TILE_SIZE));
//	    
//	    PositionComponent pos00 = Main.PLAYER.get(PositionComponent.class).clone();
//	    
//	    pos00.coord[0] -= TILES_IN_CANVAS/2;
//	    pos00.coord[1] -= TILES_IN_CANVAS/2;
//	    
//	    Tile[][] map = Map.getSquareAreaAsArray(pos00, TILES_IN_CANVAS, TILES_IN_CANVAS);
//	    
//	    
//	    for(int i = 0; i < map.length; i++) {
//	    	for(int j = 0; j < map.length; j++) {
//	    		Tile tile = map[i][j];
//	    		
//	    		Color backColor = tile.getBackColor();
//	    		gc.setFill(backColor);
//	    		gc.setStroke(Color.ORANGE.darker());
//	    		gc.fillRect(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE);
//	    		gc.strokeRect(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE);
//	    		
//	    		double textPosX = i * TILE_SIZE + TILE_SIZE/2;
//	            double textPosY = j * TILE_SIZE + TILE_SIZE/2;
//	            
//	    		gc.setFill(tile.getFrontColor());
//	    		gc.fillText(tile.getASCII(), textPosX, textPosY);
//	    	}
//	    }
//	    System.out.println(System.currentTimeMillis() - t);
//	}
	
    public void refresh() {
		long t = System.currentTimeMillis();
		getChildren().clear();
		
	    PositionComponent pos00 = Main.PLAYER.get(PositionComponent.class).clone();
	    
	    pos00.coord[0] -= TILES_IN_CANVAS/2;
	    pos00.coord[1] -= TILES_IN_CANVAS/2;
	    
	    Tile[][] map = Map.getSquareAreaAsArray(pos00, TILES_IN_CANVAS, TILES_IN_CANVAS);
	    
	    
	    for(int i = 0; i < map.length; i++) {
	    	for(int j = 0; j < map.length; j++) {
	    		Tile tile = map[i][j];
	    		add(tile.CANVAS, i, j);
	    	}
	    }
	    System.out.println(System.currentTimeMillis() - t);
	}
}
