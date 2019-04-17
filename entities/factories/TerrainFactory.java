package factories;

import main.Entity;

public class TerrainFactory extends EntityFactory {
	
	protected static Entity[] terrainsByID = null;
	
	private TerrainFactory() {}
	
	protected static Entity get(int ID) {
		int correctedID = ID - 1000;
		if(correctedID >= terrainsByID.length) {
			System.out.println("ID de terrano incorrecta, el máximo es " + (terrainsByID.length-1) + " y se pidió " + ID);
			return null;
		}else {
			return terrainsByID[correctedID];
		}
	}
	
	
	
}
