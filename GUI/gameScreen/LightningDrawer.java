package gameScreen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LightningDrawer {
	
	private static GraphicsContext gc;
	private static int tileSize;
	
	protected static void initialize(GraphicsContext context) {
		gc = context;
		gc.setFill(new Color(0.1, 0.1, 0.1, 1));
		tileSize = DrawUtils.tileSize;
	}
	
	protected static void draw(float alpha, int x, int y) {
		gc.setGlobalAlpha(alpha);
		gc.fillRect(x, y, tileSize, tileSize);
	}
	
	protected static void reset() {
		gc.clearRect(0, 0, DrawUtils.screenSize, DrawUtils.screenSize);
	}
}
