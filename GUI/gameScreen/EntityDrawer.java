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
	
	protected static void initialize(GraphicsContext context) {
		gc = context;
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(Font.font("Courier New", FontWeight.BLACK, DrawUtils.tileSize));
		
		tileQuantity = DrawUtils.tileQuantity;
		tileSize = DrawUtils.tileSize;
	}

	protected static void draw() {
		PositionC pos00 = DrawUtils.getPos00();
		Tile[][] map = Map.getSquareAreaAsArray(pos00, tileQuantity, tileQuantity);
		Set<Tile> visibleTiles = Main.player.get(VisionC.class).visionMap;
		
		float halfTile = tileSize>>1;
		
		gc.clearRect(0, 0, DrawUtils.screenSize, DrawUtils.screenSize);
		LightningDrawer.reset();
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				int x = i*tileSize;
				int y = j*tileSize;
				
				Tile tile = map[i][j];
				if(visibleTiles.contains(tile)) {
					gc.setFill(tile.getBackColor());
					gc.fillRect(x, y, tileSize, tileSize); 
					gc.setFill(tile.getFrontColor());
					gc.fillText(tile.getASCII(), x + halfTile, y + halfTile);
					
					// Dibujar borde amarillo si hay mas de un item
					if(tile.getEntities(Type.ITEM).size() > 1) {
						gc.setStroke(Color.YELLOW);
						gc.strokeRect(x, y, tileSize, tileSize);
					}
					
					// Dibujar círculo rojo en al dirección en la que está mirando el NPC
					if(tile.has(Type.NPC)) {
						Direction faceDir = tile.get(Type.NPC).get(VisionC.class).faceDir;
						gc.setFill(Color.DARKRED);
						gc.fillOval(x + halfTile + 7*faceDir.movX, y + halfTile + 7*faceDir.movY, 4, 4);
					}
					LightningDrawer.draw(1-tile.getLightLevel(), x, y);
				}
				else if(tile.isViewed()) {
					gc.setFill(tile.getBackColor().darker());
					gc.fillRect(x, y, tileSize, tileSize);
				}
			}
		}
	}
	
}
