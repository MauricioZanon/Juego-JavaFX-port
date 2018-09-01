package factories;

import java.util.HashMap;

import main.Entity;

public abstract class TerrainFactory extends EntityFactory {
	
	protected static HashMap<String, Entity> terrainPool = new HashMap<>();
	
	public static Entity get(String name){
		if(!terrainPool.containsKey(name)) {
			return null;
		}
		else {
			return terrainPool.get(name);
		}
	}
	
	public static Entity getNewInstance(String name) {
		if(!terrainPool.containsKey(name)) {
			return null;
		}
		else {
			return terrainPool.get(name).clone();
		}
	}
	
	
}
