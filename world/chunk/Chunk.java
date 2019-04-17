package chunk;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import main.Entity;
import tile.Tile;
import tile.TilePool;

public class Chunk {
	
	public static final int SIZE = 32;
	protected Tile[][] chunkMap = new Tile[SIZE][SIZE];
	protected int[] coord;
	
	public Chunk(String chunkCoord) {
		coord = Arrays.stream(chunkCoord.split(":")).mapToInt(Integer::parseInt).toArray();
	}
	
	public Chunk() {}

	protected void fillLevel(Entity terrain) {
		int x = SIZE*coord[0];
		int y = SIZE*coord[1];
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				Tile t = TilePool.get(x+i, y+j, coord[2]);
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
	
	public void dump() {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				TilePool.returnTile(chunkMap[i][j]);
			}
		}
	}

}
