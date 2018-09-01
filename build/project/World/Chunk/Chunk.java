package Chunk;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import factories.EntityFactory;
import main.Entity;
import map.Tile;

public class Chunk {
	
	public static final int SIZE = 100;
	protected Tile[][] chunkMap = new Tile[SIZE][SIZE];
	protected int[] coord;
	
	public Chunk(String chunkCoord, String chunkString) { //TODO test
		int[] coords = Arrays.stream(chunkCoord.split(":")).mapToInt(Integer::parseInt).toArray();
    	coord[0] = coords[0];
    	coord[1] = coords[1];
    	coord[2] = coords[2];
    	
    	fillLevel(null);
    	
    	String[] tileStrings = chunkString.split("/");
    	for(int i = 0; i < tileStrings.length; i++) {
    		String[] entitiesStrings = tileStrings[i].split(",");
    		
    		int tileX = i/SIZE;
    		int tileY = i%SIZE;
    		Tile t = new Tile(coord[0]*SIZE + tileX, coord[1]*SIZE + tileY, coord[2]);
    		for(int j = 1; j < entitiesStrings.length; j++) {
    			t.put(EntityFactory.create(Integer.parseInt(entitiesStrings[j])));
    		}
    		chunkMap[tileX][tileY] = t;
    	}
	}
	
	public Chunk() {}

	protected void fillLevel(Entity terrain) {
		int x = SIZE*coord[0];
		int y = SIZE*coord[1];
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				Tile t = new Tile(x+i, y+j, coord[2]);
				t.put(terrain);
				chunkMap[i][j] = t;
			}
		}
	}
	
	protected void buildLevel() {};
	
	public Tile[][] getMap() {
		return chunkMap;
	}

	public int[] getCoord() {
		return coord;
	}

	public Collection<Entity> getEntities(Predicate<Entity> cond) {
		return Arrays.stream(chunkMap)
				 .flatMap(sub -> Arrays.stream(sub))
				 .flatMap(tile -> tile.getEntities(cond).stream())
				 .collect(Collectors.toSet());
	}
	
	public String getPosAsString() {
		return coord[0] + ":" + coord[1] + ":" + coord[2];
	}
	
	public String serialize() {//TODO test
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < chunkMap.length; x++) {
			for(int y = 0; y < chunkMap[0].length; y++) {
				Tile tile = chunkMap[x][y];
				if(tile != null) {
					chunkMap[x][y].serialize(sb);
				}
			}
		}
		return sb.toString();
	}

}
