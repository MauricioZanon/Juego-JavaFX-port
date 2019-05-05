package world;

import java.util.HashSet;
import java.util.Set;

import RNG.Noise;
import cave.Cave;
import cave.Cave.CaveSize;
import chunk.Chunk;
import components.ContainerC;
import components.PositionC;
import dungeon.DungeonBuilder;
import dungeon.DungeonBuilder.DungeonSize;
import dungeon.DungeonBuilder.DungeonType;
import factories.EntityFactory;
import field.FieldLevel;
import forest.ForestLevel;
import main.Location;
import main.Type;
import map.Map;
import mountain.MountainLevel;

public class WorldBuilder {
	
	private static String name = "world";
	private static float[][] elevationMap;
	private static Set<Location> locations = new HashSet<>();
	
	public static boolean isBuilding;
	
	private WorldBuilder() {}
	
	public static void createWorld(String n){
		long time = System.currentTimeMillis();
		
		name = n;
		isBuilding = true;
		elevationMap = Noise.generatePerlinNoise(1000, 1000, 3);
		createLocations();
		isBuilding = false;
		
		Map.getTile(1,  1, 0).put(EntityFactory.create(2005));
		Map.getTile(1,  1, 0).get(Type.FEATURE).get(ContainerC.class).add(EntityFactory.createRandom(Type.ITEM));
//		Map.getTile(2,  1, 0).put(EntityFactory.create(2005));
//		Map.getTile(3,  1, 0).put(EntityFactory.create(2005));
		
//		Map.getTile(2,  2, 0).put(EntityFactory.create("wolf"));
//		Die.execute(Map.getTile(2,  2, 0).get(Type.ACTOR));
		
		System.out.println("Tiempo de creación del World Map: " + (System.currentTimeMillis() - time));
		
//		StateSaver.saveGameState();
		
	}
	
	private static void createLocations() {
		PositionC pos00 = new PositionC();
		pos00.coord = new int[] {0,0,0};
//		new Village(pos00);
		new Cave(pos00, CaveSize.MEDIUM);
		pos00.coord[2]++;
		pos00.coord = new int[] {100, 100, 0};
		DungeonBuilder.createDungeon(pos00, DungeonType.REGULAR, DungeonSize.MEDIUM);
	}

	public static String getName() {
		return name;
	}
	
	public static Chunk createOverworldChunk(int x, int y) {
		isBuilding = true;
		if(elevationMap == null) {
			elevationMap = Noise.generatePerlinNoise(1000, 1000, 3);
		}
		int center = elevationMap.length / 2;
		float elevation = elevationMap[center + x][center + y];
		Chunk result;
		if(elevation <= 0.5f) {
			result = new FieldLevel(x, y);
		}
		else if(elevation <= 0.65f) {
			result = new ForestLevel(x, y);
		}
		else{
			result = new MountainLevel(x, y);
		}
		isBuilding = true;
		return result;
	}

	public static Set<Location> getLocations() {
		return locations;
	}
	
}
