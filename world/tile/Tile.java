package tile;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.function.Predicate;

import FOV.ShadowCasting;
import RNG.RNG;
import components.BackColorC;
import components.GraphicC;
import components.MovementC.MovementType;
import components.PositionC;
import components.TransitableC;
import javafx.scene.paint.Color;
import main.Entity;
import main.Flags;
import main.Type;
import observerPattern.Notification;
import observerPattern.Observable;
import time.Clock;
import world.WorldBuilder;

public class Tile implements Observable{
	
	private EnumMap<Type, Entity> entities = new EnumMap<>(Type.class);
	private EnumMap<Type, ArrayDeque<Entity>> stackableEntities = new EnumMap<>(Type.class);
	public PositionC pos;
	
	private Color backColor = null;
	private Color frontColor = null;
	private String ASCII = "";
	
	private float lightLevel = 0;
	
	protected Tile(PositionC pos) {
		this.pos = pos;
	}
	
	public void put(Entity e) {
		boolean wasTranslucent = isTranslucent();
		if(e != null) {
			e.addComponent(pos);
			Type superType = e.TYPE.getSuperType();
			if(e.TYPE.isTileStackable()) {
				if(!stackableEntities.containsKey(superType))
					stackableEntities.put(superType, new ArrayDeque<>());
				stackableEntities.get(superType).add(e);
			}else {
				entities.put(superType, e); 
			}
			refreshGraphics();
			
			if(e.is(Flags.LIGHT_SOURCE)) {
				ShadowCasting.calculateIllumination(e, true);
			}
			if(wasTranslucent != isTranslucent() && !WorldBuilder.isBuilding) {
				notifyObservers(Notification.RECALCULATE_LIGHT);
			}
		}
	}
	
	public void remove(Entity entity) {
		boolean wasTranslucent = isTranslucent();
		Type superType = entity.TYPE.getSuperType();
		if(!entity.TYPE.isTileStackable()) {
			entities.remove(superType);
		}
		else {
			Iterator<Entity> iter = stackableEntities.get(superType).iterator();
			while(iter.hasNext()) {
				if(iter.next().name.equals(entity.name)) {
					iter.remove();
					break;
				}
			}
		}
		if(wasTranslucent != isTranslucent() && !WorldBuilder.isBuilding) {
			notifyObservers(Notification.RECALCULATE_LIGHT);
		}
	}
	
	public Entity remove(Type type) {
		boolean wasTranslucent = isTranslucent();
		Entity removedEntity = null;
		Type superType = type.getSuperType();
		if(type.isTileStackable() && stackableEntities.containsKey(superType)) {
			removedEntity = stackableEntities.get(superType).pollFirst();
			if(stackableEntities.get(superType).isEmpty())
				stackableEntities.remove(superType);
		}
		else {
			removedEntity = entities.remove(superType);
		}
		if(removedEntity != null) {
			refreshGraphics();
			if(removedEntity.is(Flags.LIGHT_SOURCE)) {
				ShadowCasting.calculateIllumination(removedEntity, false);
			}
		}
		if(wasTranslucent != isTranslucent() && !WorldBuilder.isBuilding) {
			notifyObservers(Notification.RECALCULATE_LIGHT);
		}
		return removedEntity;
	}
	
	public Entity get(Type type) {
		if(type.isTileStackable() && stackableEntities.containsKey(type.getSuperType())) {
			return stackableEntities.get(type.getSuperType()).getFirst();
		}
		else {
			return entities.get(type.getSuperType());
		}
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
		if(stackableEntities.containsKey(Type.FEATURE)){
			for(Entity e : stackableEntities.get(Type.FEATURE)) {
				if(e.is(flag))
					return e;
			};
		}
		if(stackableEntities.containsKey(Type.ITEM)){
			for(Entity e : stackableEntities.get(Type.ITEM)) {
				if(e.is(flag))
					return e;
			};
		}
		if(entities.containsKey(Type.TERRAIN) && entities.get(Type.TERRAIN).is(flag)){
			return entities.get(Type.TERRAIN);
		}
		return null;
	}
	
	public ArrayDeque<Entity> getEntities(){
		ArrayDeque<Entity> result = new ArrayDeque<>();
		result.addAll(entities.values());
		stackableEntities.values().forEach(l -> result.addAll(l));
		return result;
	}
	
	public ArrayDeque<Entity> getEntities(Predicate<Entity> cond){
		ArrayDeque<Entity> result = new ArrayDeque<>();
		for(Entity e : getEntities()) {
			if(cond.test(e)) result.add(e);
		}
		return result;
	}
	
	public ArrayDeque<Entity> getEntities(Type type){
		Type superType = type.getSuperType();
		if(type.isTileStackable()) {
			if(stackableEntities.containsKey(superType)) {
				return stackableEntities.get(superType);
			}
		}
		else {
			if(entities.containsKey(superType)) {
				ArrayDeque<Entity> result = new ArrayDeque<>();
				result.add(entities.get(superType));
				return result;
			}
		}
		return new ArrayDeque<>();
	}
	
	public boolean has(Type type) {
		return entities.containsKey(type) || stackableEntities.containsKey(type);
	}
	
	public boolean has(Flags flag) {
		for(Entity e : getEntities()){
			if(e.is(flag)) {
				return true;
			}
		}
		return false;
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
				backColor = RNG.getRandom(get(Type.TERRAIN).get(BackColorC.class).colors);
			}
			GraphicC graph = visibleEntity.get(GraphicC.class);
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
		for(Entity e : getEntities()) {
			sb.append(e.ID + ",");
		}
		sb.append("/");
	}
	
	@Override
	public String toString() {
		String s = pos.coord[0] + " " + pos.coord[1] + " " + pos.coord[2] + "\n";
		for (Entity e : getEntities()) {
			s += e.name + "\n";
		}
		return s;
	}
	
	@Override
	public boolean equals(Object t) {
		return pos.equals(((Tile)t).pos);
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
		return !has(Flags.OPAQUE);
	}

	public boolean isTransitable(MovementType movType) {//TODO test
		return entities.get(Type.TERRAIN) != null && entities.get(Type.TERRAIN).get(TransitableC.class).isTransitable(movType)
				&& (entities.get(Type.FEATURE) == null || entities.get(Type.FEATURE).get(TransitableC.class).isTransitable(movType));
	}
	
	public float getMovementCost(MovementType movType) {
		float cost = 1;
		
		if(entities.get(Type.TERRAIN) != null) {
			cost *= entities.get(Type.TERRAIN).get(TransitableC.class).getMovCost(movType);
		}
		if(entities.get(Type.FEATURE) != null) {
			cost *= entities.get(Type.FEATURE).get(TransitableC.class).getMovCost(movType);
		}
		
		return cost;
	}
	
	public void changeLightLevel(float v) {
		lightLevel += v;
		if(lightLevel < 0)
			lightLevel = 0;
	}
	
	public float getLightLevel() {
		if(pos.coord[2] == 0) {
			return Clock.getSurfaceLightLevel() + lightLevel;
		}else {
			return lightLevel;
		}
	}

	/**
	 * Remueve todos los elementos del tile para que pueda reusarse
	 */
	protected void clear() {
		entities.clear();
		stackableEntities.clear();
		observers.clear();
		ASCII = null;
		backColor = null;
		frontColor = null;
		lightLevel = 0;
		pos = null;
	}

}
