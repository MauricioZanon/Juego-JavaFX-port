package gameScreen;

import java.util.Set;

import application.Main;
import components.PositionC;
import components.VisionC;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public class EntityDrawer {
	
	private static GraphicsContext gc;
	private static int tileQuantity;
	private static int tileSize;
	private static float halfTile;
	
	protected static void initialize(GraphicsContext context) {
		gc = context;
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(Font.font("Courier New", FontWeight.BLACK, DrawUtils.tileSize));
		
		tileQuantity = DrawUtils.tileQuantity;
		tileSize = DrawUtils.tileSize;
		halfTile = tileSize>>1;
	}

	protected static void draw() {
		PositionC pos00 = DrawUtils.getPos00();
		Set<Tile> visibleTiles = Main.player.get(VisionC.class).visionMap;
		if(DrawUtils.fullRedraw) {
			fullRedraw(pos00, visibleTiles);
		}
		else {
			partialRedraw(pos00, visibleTiles);
		}
		
		DrawUtils.fullRedraw = false;
		DrawUtils.tilesToDraw.clear();
	}
	
	private static void fullRedraw(PositionC pos00, Set<Tile> visibleTiles) {
		Tile[][] map = Map.getSquareAreaAsArray(pos00, tileQuantity, tileQuantity);
		
		gc.clearRect(0, 0, DrawUtils.screenSize, DrawUtils.screenSize);
		LightningDrawer.reset();
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				int x = i*tileSize;
				int y = j*tileSize;
				
				Tile tile = map[i][j];
				if(visibleTiles.contains(tile)) {
					drawVisibleTile(tile, x, y);
				}
				else if(tile.isViewed()) {
					drawViewedTile(tile, x, y);
				}
			}
		}
	}
	
	private static void partialRedraw(PositionC pos00, Set<Tile> visibleTiles) {
		Set<Tile> tiles = DrawUtils.tilesToDraw;
		
		tiles.forEach(t -> {
			int x = (t.pos.coord[0] - pos00.coord[0]) * tileSize;  
			int y = (t.pos.coord[1] - pos00.coord[1]) * tileSize;  
			if(visibleTiles.contains(t)) {
				drawVisibleTile(t, x, y);
			}
			else if(t.isViewed()) {
				drawViewedTile(t, x, y);
			}
		});
	}
	
	private static void drawVisibleTile(Tile tile, int x, int y) {
		gc.setFill(tile.getBackColor());
		gc.fillRect(x, y, tileSize, tileSize); 
		gc.setFill(tile.getFrontColor());
		gc.fillText(tile.getASCII(), x + halfTile, y + halfTile);
		
		// Dibujar borde amarillo si hay mas de un item
		if(tile.getEntities(Type.ITEM).size() > 1) {
			gc.setStroke(Color.YELLOW);
			gc.strokeRect(x, y, tileSize, tileSize);
		}
		
		// Dibujar c�rculo rojo en al direcci�n en la que est� mirando el NPC
		if(tile.has(Type.NPC)) {
			Direction faceDir = tile.get(Type.NPC).get(VisionC.class).faceDir;
			gc.setFill(Color.DARKRED);
			gc.fillOval(x + halfTile + 7*faceDir.movX, y + halfTile + 7*faceDir.movY, 4, 4);
		}
		LightningDrawer.draw(1-tile.getLightLevel(), x, y);
	}
	
	private static void drawViewedTile(Tile tile, int x, int y) {
		gc.setFill(tile.getBackColor().darker().desaturate());
		gc.fillRect(x, y, tileSize, tileSize);
		gc.setFill(tile.getFrontColor().darker().desaturate());
		gc.fillText(tile.getASCII(), x + halfTile, y + halfTile);
		
		float darkness = 1-tile.getLightLevel();
		if(darkness > 0.85f) darkness = 0.85f;
		LightningDrawer.draw(darkness, x, y);
	}
	
}
