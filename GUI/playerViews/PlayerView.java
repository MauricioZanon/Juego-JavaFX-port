package playerViews;

import actions.FollowPath;
import application.Main;
import components.MovementComponent;
import components.PositionComponent;
import eventSystem.EventSystem;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import pathFind.AStar;
import pathFind.Path;
import system.RenderSystem;
import tile.Tile;

public abstract class PlayerView extends Canvas{
	
	/**El tamaño con el que se dibujan los tiles en el canvas*/
	public int tileSize;
	public int tileQuantity;
	protected GraphicsContext gc;
	
	public abstract void refresh();
	
	/**
	 * @param tileQuantity La cantidad de tiles de alto y ancho que se van a dibujar
	 */
	public PlayerView(int tileQuantity) {
		this.tileQuantity = tileQuantity;
		
		double sceneHeight = RenderSystem.getSceneHeight();
		tileSize = (int) (sceneHeight/tileQuantity);
		setHeight(sceneHeight);
		setWidth(sceneHeight);

	    setOnMouseClicked(e -> {
	    	if(EventSystem.waitingOnPlayerInput) {
	    		switch(e.getButton()) {
	    		case MIDDLE:
	    			break;
	    		case PRIMARY:
	    			PositionComponent pos = Main.player.get(PositionComponent.class);
	    			PositionComponent clickedPos = getTileUnderTheMouse(e.getX(), e.getY()).getPos();
	    			Path path = AStar.findPath(pos, clickedPos, Main.player);
	    			if(path.getDistance() > 0) {
	    				Main.player.get(MovementComponent.class).path = path;
	    				FollowPath.execute(Main.player);
	    			}
	    			e.consume();
	    			break;
	    		case SECONDARY:
	    			break;
	    		default:
	    			break;
	    			
	    		}
	    	}
	    });
	    
	    gc = getGraphicsContext2D();
	}
	
	/**
	 * @return Devuelve la posición del @Tile que se dibuja en la esquina superior izquierda del canvas
	 */
	protected PositionComponent getPos00() {
		PositionComponent pos00 = Main.player.get(PositionComponent.class).clone();
	    pos00.coord[0] -= tileQuantity/2;
	    pos00.coord[1] -= tileQuantity/2;
	    
	    return pos00;
	}
	
	protected Tile getTileUnderTheMouse(double x, double y) {
    	PositionComponent pos00 = getPos00();
    	pos00.coord[0] += (int) (x / tileSize);
    	pos00.coord[1] += (int) (y / tileSize);
    	
    	return pos00.getTile();
	}
	
	/**
	 * @return Devuelve un array con dos doubles x e y, ambos son múltiplo de tileSize y representan la posición
	 * 		   del canvas en el que se debe dibujar un @Tile
	 */
	protected double[] getDiscretePosInCanvas(PositionComponent pos) {
		PositionComponent pos00 = getPos00();
		double x = (pos.coord[0] - pos00.coord[0])*tileSize;
		double y = (pos.coord[1] - pos00.coord[1])*tileSize;
		
		return new double[] {x, y};
	}
	
	public void markTile(Tile tile) {
		double[] coordInCanvas = getDiscretePosInCanvas(tile.getPos());
		gc.setStroke(tile.getBackColor().invert());
		gc.strokeRect(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
		gc.strokeOval(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
	}
	
	public void selectTile(Tile tile) {
		double[] coordInCanvas = getDiscretePosInCanvas(tile.getPos());
		gc.setStroke(tile.getBackColor().brighter().brighter());
		gc.strokeRect(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
		gc.strokeOval(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
	}
	
}
