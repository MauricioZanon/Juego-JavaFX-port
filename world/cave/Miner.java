package cave;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import components.PositionC;
import factories.TerrainFactory;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Miner {
	
	private PositionC position;
	private static final Entity DIRT_FLOOR = TerrainFactory.get("dirt floor");
	protected static Set<Tile> floorTiles = new HashSet<>();
	
	protected boolean activated = true;
	
	public Miner(Tile startingTile, Tile[][] caveArea) {
		position = startingTile.pos;
		startingTile.put(DIRT_FLOOR);
		floorTiles.add(startingTile);
	}
	
	public void dig() {
		Set<Tile> validTiles = Map.getOrthogonalTiles(position.getTile(), t -> t.get(Type.TERRAIN) == null);
		Tile tile = RNG.getRandom(validTiles);
		if(tile != null) {
			tile.put(DIRT_FLOOR);
			position = tile.pos;
			floorTiles.add(tile);
		}else {
			activated = false;
		}
	}
	
	public Miner reproduce(Tile[][] caveArea) {
		Tile tile = RNG.getRandom(floorTiles, t -> Map.isOrthogonallyAdjacent(t, ti -> ti.get(Type.TERRAIN) == null));
		if(tile != null) {
			return new Miner(tile, caveArea);
		}else {
			return null;
		}
	}

	public PositionC getPosition() {
		return position;
	}

	public static int getDiggedTiles() {
		return floorTiles.size();
	}

}
