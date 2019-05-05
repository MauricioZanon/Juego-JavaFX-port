package cave;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import components.PositionC;
import factories.EntityFactory;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Walker {
	
	private PositionC position;
	private static final Entity DIRT_FLOOR = EntityFactory.create("dirt floor");
	private static final Entity STONE_FLOOR = EntityFactory.create("stone floor");
	protected static Set<Tile> floorTiles = new HashSet<>();
	
	protected boolean activated = true;
	
	protected Walker(Tile startingTile) {
		position = startingTile.pos;
		digFloor(startingTile);
	}
	
	protected void dig() {
		Set<Tile> emptyTiles = Map.getOrthogonalTiles(position.getTile(), t -> t.get(Type.TERRAIN) == null);
		if(emptyTiles.isEmpty()) {
			activated = false;
		}
		else {
			Tile tile = RNG.getRandom(emptyTiles);
			position = tile.pos;
			if(tile.get(Type.TERRAIN) == null) {
				digFloor(tile);
				if(RNG.nextFloat() < 0.005) {
					digDown(tile);
				}
			}
		}
	}
	
	private void digFloor(Tile t) {
		t.put(floorTiles.size() < 200 ? DIRT_FLOOR : STONE_FLOOR);
		floorTiles.add(t);
	}
	
	private void digDown(Tile t) {
		t.put(EntityFactory.create("down stair"));
		
		position = position.clone();
		position.coord[2]++;
		
		t = position.getTile();
		digFloor(t);
		t.put(EntityFactory.create("up stair"));
	}
	
	protected Walker reproduce() {
		Tile tile = RNG.getRandom(floorTiles, t -> Map.isOrthogonallyAdjacent(t, ti -> ti.get(Type.TERRAIN) == null));
		return tile != null ? null : new Walker(tile);
	}

	protected PositionC getPosition() {
		return position;
	}

	protected static int getDiggedTiles() {
		return floorTiles.size();
	}

}
