package gameScreen;

import java.util.Set;

import application.Main;
import components.PositionC;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import map.Map;
import tile.Tile;

public class SelectionDrawer {
	
	private static GraphicsContext gc;
	private static int tileSize;
	
	protected static void initialize(GraphicsContext context) {
		gc = context;
		tileSize = DrawUtils.tileSize;
	}
	
	/**
	 * Marca el tile que este seleccionado o bajo el mouse, si se esta disparando, lanzando un item o algún 
	 * otro tipo de proyectil o habilidad va a marcar también el area que afecta y el camino que recorre
	 */
	protected static void draw() {
		reset();
		Tile selectedTile = DrawUtils.selectedTile;
		
		if(InputConfig.getMouseAction() != MouseAction.GO_TO)
			drawSelectableArea();
		
		if(InputConfig.isProjectile()) {
			gc.setStroke(Color.YELLOW);
			gc.setGlobalAlpha(1);
			Map.getStraigthLine(Main.player.get(PositionC.class), selectedTile.pos).forEach(t -> drawMarkOnSelectedTile(t));
		}
		
		int radius = InputConfig.getAffectedRadius();
		if(radius > 1) {
			gc.setStroke(Color.WHITE);
			gc.setGlobalAlpha(0.5f);
			Map.getCircundatingAreaAsSet(radius, selectedTile, true).forEach(t -> drawMarkOnSelectedTile(t));
		}
		
		gc.setStroke(Color.YELLOW);
		gc.setGlobalAlpha(1);
		drawMarkOnSelectedTile(selectedTile);
		
//		String text = "";
//		if(Main.player.get(VisionC.class).visionMap.contains(selectedTile)) {
//			if(selectedTile.has(Type.ACTOR))
//				text = StringUtils.toTitle(selectedTile.get(Type.ACTOR).name);
//			else if(selectedTile.has(Type.FEATURE))
//				text = StringUtils.toTitle(selectedTile.get(Type.FEATURE).name);
//			else if(selectedTile.has(Type.ITEM))
//				text = StringUtils.toTitle(selectedTile.get(Type.ITEM).name);
//		}
//		viewedEntityName.setText(text);
	}
	
	private static void drawSelectableArea() {
		int maxDistance = InputConfig.getMaxDistance();
		Set<Tile> drawedTiles;
		if(maxDistance > 0) {
			drawedTiles = Map.getCircundatingAreaAsSet(maxDistance, Main.player.get(PositionC.class).getTile(), true);
		}
		else {
			drawedTiles = Map.getAdjacentTiles(Main.player.get(PositionC.class).getTile());
		}
		
		gc.setStroke(Color.BLACK);
		for(Tile t : drawedTiles) {
			double[] coordInCanvas = DrawUtils.getDiscretePosInCanvas(t.pos);
			gc.strokeRect(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
		}
	}
	
	private static void drawMarkOnSelectedTile(Tile tile) {
		double[] coordInCanvas = DrawUtils.getDiscretePosInCanvas(tile.pos);
		gc.strokeRect(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
		gc.strokeOval(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
	}
	
	protected static void reset() {
		gc.clearRect(0, 0, DrawUtils.screenSize, DrawUtils.screenSize);
	}

}
