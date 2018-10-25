package tile;

import java.util.LinkedList;
import java.util.List;

public abstract class TilePool {
	
	private static List<Tile> pool = new LinkedList<>();
	
	public static Tile get(int x, int y, int z) {
		if(pool.isEmpty()) {
			return new Tile(new int[] {x, y, z});
		}else {
			Tile t = pool.remove(0);
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
