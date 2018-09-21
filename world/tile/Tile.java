package tile;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.function.Predicate;

import RNG.RNG;
import components.BackColorComponent;
import components.GraphicComponent;
import components.PositionComponent;
import components.TransitableComponent;
import javafx.scene.paint.Color;
import main.Entity;
import main.Flags;
import main.Type;
import time.Clock;

public class Tile{
	
	private EnumMap<Type, Entity> entities = new EnumMap<Type, Entity>(Type.class);
	public final int[] COORD;
	
	private Color backColor = Color.BLACK;
	private Color frontColor = Color.BLACK;
	private String ASCII = "";
	
	private float lightLevel = 0;
	
	protected Tile(int x, int y, int z) {
		COORD = new int[]{x, y, z};
	}
	
	protected Tile(int[] coord) {
		COORD = coord;
	}
	
	public void put(Entity e) {
		if(e != null) {
			entities.put(e.TYPE.getSuperType(), e); 
			refreshGraphics();
		}
	}
	
	public Entity remove(Type type) {
		Entity removedEntity = entities.remove(type.getSuperType());
		if(removedEntity != null) {
			refreshGraphics();
		}
		return removedEntity;
	}
	
	public Entity get(Type type) {
		return entities.get(type.getSuperType());
	}
	
	/**
	 * @param flag
	 * @return una entidad que tenga el flag con esta prioridad:
	 * 		ACTOR > FEATURE > ITEM > TERRAIN
	 */
	public Entity get(Flags flag) {
		if(entities.containsKey(Type.ACTOR) && entities.get(Type.ACTOR).is(flag)){
			return entities.get(Type.ACTOR);
		}
		if(entities.containsKey(Type.FEATURE) && entities.get(Type.FEATURE).is(flag)){
			return entities.get(Type.FEATURE);
		}
		if(entities.containsKey(Type.ITEM) && entities.get(Type.ITEM).is(flag)){
			return entities.get(Type.ITEM);
		}
		if(entities.containsKey(Type.TERRAIN) && entities.get(Type.TERRAIN).is(flag)){
			return entities.get(Type.TERRAIN);
		}
		return null;
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
	
	public boolean has(Type type) {
		return entities.keySet().contains(type.getSuperType());
	}

	public PositionComponent getPos() {
		PositionComponent pos = new PositionComponent();
		pos.coord = new int[] {COORD[0], COORD[1], COORD[2]};
		return pos;
	}
	
	private void refreshGraphics() {
		Entity visibleEntity;
		if(has(Type.ACTOR)) {
			visibleEntity = get(Type.ACTOR);
		}else if(has(Type.FEATURE)) {
			visibleEntity = get(Type.FEATURE);
		}else if(has(Type.ITEM)) {
			visibleEntity = get(Type.ITEM);
		}else {
			visibleEntity = get(Type.TERRAIN);
		}
		if(visibleEntity != null) {
			if(has(Type.TERRAIN)) {
				backColor = RNG.getAproximateColor(get(Type.TERRAIN).get(BackColorComponent.class).color);
			}
			GraphicComponent graph = visibleEntity.get(GraphicComponent.class);
			frontColor = graph.color;
			ASCII = RNG.getRandom(graph.ASCII.split(" "));
		}
		else {
			ASCII = "";
			frontColor = Color.BLACK;
			backColor = Color.BLACK;
		}
	}

	public void serialize(StringBuilder sb) {
		for(Entity e : entities.values()) {
			sb.append(e.ID + ",");
		}
		sb.append("/");
	}
	
	public String toString() {
		String s = COORD[0] + " " + COORD[1] + " " + COORD[2] + "\n";
		for (Entity e : entities.values()) {
			s += e.name + "\n";
		}
		return s;
	}

	public Color getBackColor() {
		return backColor == null ? Color.BLACK : backColor;
	}

	public Color getFrontColor() {
		return frontColor;
	}

	public String getASCII() {
		return ASCII;
	}
	
	public boolean isTranslucent() {
		for(Entity e : entities.values()) {
			if(e.is(Flags.OPAQUE)) return false;
		}
		return true;
	}

	public boolean isTransitable() {//TODO test cuando se agregue el parametro
		return entities.get(Type.TERRAIN) != null && entities.get(Type.TERRAIN).get(TransitableComponent.class).transitable
				&& (entities.get(Type.FEATURE) == null || entities.get(Type.FEATURE).get(TransitableComponent.class).transitable);
	}
	
	public void changeLightLevel(float v) {
		lightLevel += v;
	}
	
	public float getLightLevel() {
		if(COORD[2] == 0) {
			return Clock.getSurfaceLightLevel();
		}else {
			return lightLevel;
		}
	}

	/**
	 * Remueve todos los elementos del tile para que pueda reusarse
	 */
	protected void clear() {
		entities.clear();
		ASCII = "";
		backColor = Color.BLACK;
		lightLevel = 0;
	}


}
