package tile;

import java.util.ArrayDeque;

import chunk.Chunk;
import components.PositionC;
import map.Map;

public class TilePool {
	
	private static ArrayDeque<Tile> pool = new ArrayDeque<>(Chunk.SIZE^2 * Map.MAX_NUMBER_OF_MAPS);
	
	private TilePool() {}
	
	public static Tile get(int x, int y, int z) {
		PositionC pos = new PositionC();
		pos.coord = new int[] {x, y, z};
		if(pool.isEmpty()) {
			return new Tile(pos);
		}else {
			Tile t = pool.pollFirst();
			t.pos = pos;
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
