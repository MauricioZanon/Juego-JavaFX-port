package map;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import RNG.RNG;
import components.BackColorComponent;
import components.DescriptionComponent;
import components.GraphicComponent;
import components.PositionComponent;
import components.TransitableComponent;
import gameScreen.PlayerView;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import main.Entity;
import main.Type;

public class Tile {
	
	private HashMap<Type, Entity> entities = new HashMap<Type, Entity>();
	/**Las coordenadas globales del tile*/
	public final int[] COORD;
	
	public final Canvas CANVAS;
	
//	private Color backColor;
//	private Color frontColor;
//	private String ASCII;
	
	public Tile(int x, int y, int z) {
		COORD = new int[]{x, y, z};
		CANVAS = new Canvas(PlayerView.TILE_SIZE, PlayerView.TILE_SIZE);
		GraphicsContext gc = CANVAS.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.CENTER);
	    gc.setFont(Font.font("Courier New", FontWeight.BLACK, PlayerView.TILE_SIZE));
	}
	
	public Tile(int[] coord) {
		COORD = coord;
		CANVAS = new Canvas(PlayerView.TILE_SIZE, PlayerView.TILE_SIZE);
		GraphicsContext gc = CANVAS.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.CENTER);
	    gc.setFont(Font.font("Courier New", FontWeight.BLACK, PlayerView.TILE_SIZE));
	}
	
	public void put(Entity e) {
		if(e != null) {
			entities.put(e.getType(), e);
		}
		refreshGraphics();
	}
	
	public Entity remove(Type type) {//TODO test
		Entity removedEntity = entities.remove(type);
		refreshGraphics();
		return removedEntity;
	}
	
	public Entity get(Type type) {
		return entities.get(type);
	}
	
	public Collection<Entity> getEntities(){
		return entities.values();
	}
	
	public Collection<Entity> getEntities(Predicate<Entity> cond){
		Collection<Entity> col = new HashSet<>();
		for(Entity e : entities.values()) {
			if(cond.test(e)) col.add(e);
		}
		return col;
	}
	
	public boolean has(Type type) {//TODO test
		return entities.keySet().contains(type);
	}

	public boolean isTransitable() {//TODO test
		return entities.get(Type.TERRAIN) != null && entities.get(Type.TERRAIN).has(TransitableComponent.class);
	}

	public PositionComponent getPos() {
		PositionComponent pos = new PositionComponent();
		pos.coord = new int[] {COORD[0], COORD[1], COORD[2]};
		return pos;
	}

	public void serialize(StringBuilder sb) {
		for(Entity e : entities.values()) {
			sb.append(e.id + ",");
		}
		sb.append("/");
	}
	
	public String toString() {
		String s = COORD[0] + " " + COORD[1] + " " + COORD[2] + "\n";
		for (Entity e : entities.values()) {
			s += e.get(DescriptionComponent.class).name + "\n";
		}
		return s;
	}
	
//	private void refreshGraphics() {
//		Entity visibleEntity;
//		if(entities.keySet().contains(Type.ACTOR)) {
//			visibleEntity = entities.get(Type.ACTOR);
//		}else if(entities.keySet().contains(Type.FEATURE)) {
//			visibleEntity = entities.get(Type.FEATURE);
//		}else if(entities.keySet().contains(Type.ITEM)) {
//			visibleEntity = entities.get(Type.ITEM);
//		}else {
//			visibleEntity = entities.get(Type.TERRAIN);
//		}
//		if(visibleEntity != null) {
//			GraphicComponent gc = visibleEntity.get(GraphicComponent.class);
//			frontColor = gc.color;
//			ASCII = gc.ASCII;
//			if(entities.keySet().contains(Type.TERRAIN)) {
//				backColor = entities.get(Type.TERRAIN).get(BackColorComponent.class).color;
//			}
//		}
//	}
	
	private void refreshGraphics() { //TODO test
		Entity visibleEntity;
		if(entities.keySet().contains(Type.ACTOR)) {
			visibleEntity = entities.get(Type.ACTOR);
		}else if(entities.keySet().contains(Type.FEATURE)) {
			visibleEntity = entities.get(Type.FEATURE);
		}else if(entities.keySet().contains(Type.ITEM)) {
			visibleEntity = entities.get(Type.ITEM);
		}else {
			visibleEntity = entities.get(Type.TERRAIN);
		}
		if(visibleEntity != null) {
			GraphicsContext gc = CANVAS.getGraphicsContext2D();

			if(entities.keySet().contains(Type.TERRAIN)) {
				gc.setFill(RNG.getAproximateColor(entities.get(Type.TERRAIN).get(BackColorComponent.class).color));
				gc.fillRect(0, 0, PlayerView.TILE_SIZE, PlayerView.TILE_SIZE);
			}
			GraphicComponent graph = visibleEntity.get(GraphicComponent.class);
			gc.setFill(graph.color);
			gc.fillText(graph.ASCII, CANVAS.getWidth()/2, CANVAS.getHeight()/2);
		}
	}

//	public Color getBackColor() {
//		return backColor;
//	}
//
//	public Color getFrontColor() {
//		return frontColor;
//	}
//
//	public String getASCII() {
//		return ASCII;
//	}
	
	
	
}
