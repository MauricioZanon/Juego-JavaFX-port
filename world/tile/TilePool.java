package tile;

import java.util.ArrayDeque;

import chunk.Chunk;
import map.Map;

public abstract class TilePool {
	
	private static ArrayDeque<Tile> pool = new ArrayDeque<>(Chunk.SIZE^2 * Map.MAX_NUMBER_OF_MAPS);
	
	public static Tile get(int x, int y, int z) {
		if(pool.isEmpty()) {
			return new Tile(new int[] {x, y, z});
		}else {
			Tile t = pool.pollFirst();
			t.COORD[0] = x;
			t.COORD[1] = y;
			t.COORD[2] = z;
			return t;
		}
	}
	
	public static void returnTile(Tile t) {
		if(t != null) {
			t.clear();
			pool.add(t);
		}
	}

}
