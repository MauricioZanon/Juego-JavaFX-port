package world;

import java.util.HashSet;
import java.util.Set;

import RNG.Noise;
import RNG.RNG;
import chunk.Chunk;
import components.ContainerComponent;
import components.PositionComponent;
import dungeon.DungeonBuilder;
import dungeon.DungeonBuilder.DungeonSize;
import dungeon.DungeonBuilder.DungeonType;
import factories.FeatureFactory;
import factories.ItemFactory;
import field.FieldLevel;
import forest.ForestLevel;
import main.Entity;
import main.Location;
import map.Map;
import mountain.MountainLevel;
import village.Village;

public class WorldBuilder {
	
	private static String name = "world";
	private static float[][] elevationMap;
	private static Set<Location> locations = new HashSet<>();
	
	public static boolean isBuilding;
	
	private WorldBuilder() {}
	
	public static void createWorld(){
		long time = System.currentTimeMillis();
		
		isBuilding = true;
		elevationMap = Noise.generatePerlinNoise(1000, 1000, 3);
		createLocations();
		isBuilding = false;
		
		Entity chest = FeatureFactory.createFeature(2005);
		chest.get(ContainerComponent.class).addAll(ItemFactory.getTwoOfEach());
		Map.getTile(1,  1, 0).put(chest);
		
		System.out.println("Tiempo de creación del World Map: " + (System.currentTimeMillis() - time));
		
//		StateSaver.saveGameState();
		
	}
	
	private static void createLocations() {
		PositionComponent pos00 = new PositionComponent();
		pos00.coord = new int[] {0,0,0};
		new Village(pos00);
		DungeonBuilder.createDungeon(pos00, RNG.getRandom(DungeonType.values()), RNG.getRandom(DungeonSize.values()));
		pos00.coord[2]++;
		pos00.coord = new int[] {100, 100, 0};
//		new Cave(pos00, RNG.getRandom(CaveSize.values()));
	}

	public static String getName() {
		return name;
	}
	
	public static Chunk createOverworldChunk(int x, int y) {
		int center = elevationMap.length / 2;
		float elevation = elevationMap[center + x][center + y];
		if(elevation <= 0.5f) {
			return new FieldLevel(x, y);
		}
		else if(elevation <= 0.65f) {
			return new ForestLevel(x, y);
		}
		else{
			return new MountainLevel(x, y);
		}
	}

	public static Set<Location> getLocations() {
		return locations;
	}
	
}
