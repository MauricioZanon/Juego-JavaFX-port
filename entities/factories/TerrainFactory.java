package factories;

import java.util.ArrayList;
import java.util.HashMap;

import main.Entity;

public class TerrainFactory extends EntityFactory {
	
	protected static HashMap<String, Entity> terrainPool = new HashMap<>();
	protected static ArrayList<Entity> terrainsByID = new ArrayList<>();
	
	private TerrainFactory() {}
	
	public static Entity get(String name){
		if(!terrainPool.containsKey(name)) {
			return null;
		}
		else {
			return terrainPool.get(name);
		}
	}
	
	public static Entity get(int ID) {
		int correctedID = ID - 1000;
		if(correctedID >= terrainsByID.size()) {
			System.out.println("ID de terrano incorrecta, el máximo es " + (terrainsByID.size()-1) + " y se pidió " + ID);
			return null;
		}else {
			return terrainsByID.get(correctedID);
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
