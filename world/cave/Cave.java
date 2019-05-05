package cave;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import components.MovementC.MovementType;
import components.PositionC;
import factories.EntityFactory;
import main.Entity;
import main.MultiLevelLocation;
import main.Type;
import map.Map;
import tile.Tile;
import world.WorldBuilder;

public class Cave extends MultiLevelLocation{
	
	/**Hecho con random walks*/
	public Cave(PositionC entranceStairPos, CaveSize size) {
		placeInitialStairs(entranceStairPos);
		
		PositionC initialPos = entranceStairPos.clone();
		initialPos.coord[2]++;
		dig(initialPos.getTile(), size.floorTiles);
		
		putWalls();
		
		Walker.floorTiles.clear();
		
		WorldBuilder.getLocations().add(this);
	}
	
	private void placeInitialStairs(PositionC entranceStairPos) {
		entranceStairPos.getTile().put(EntityFactory.create("down stair"));

		PositionC exitStairPos = entranceStairPos.clone();
		exitStairPos.coord[2] += 1;
		exitStairPos.getTile().put(EntityFactory.create("up stair"));
	}
	
	private void dig(Tile startingTile, int floorTilesAmount) {
		Set<Walker> miners = new HashSet<>();
		miners.add(new Walker(startingTile));
		
		while(Walker.getDiggedTiles() < floorTilesAmount) {
			Set<Walker> newMiners = new HashSet<>();
			Set<Walker> deactivatedMiners = new HashSet<>();
			
			for(Walker miner : miners) {
				if(miner.activated) {
					miner.dig();
					if(RNG.nextFloat() < 0.01) { // Ver como afecta a la eficiencia esta chance de reproducirse
						Walker newMiner = miner.reproduce();
						if(newMiner != null) {
							newMiners.add(newMiner);
						}
					}
				}
				else {
					deactivatedMiners.add(miner);
				}
			}
			miners.addAll(newMiners);
			miners.removeAll(deactivatedMiners);
			if(miners.isEmpty()) {
				miners.add(new Walker(RNG.getRandom(Walker.floorTiles, t -> Map.isOrthogonallyAdjacent(t, ti -> ti.get(Type.TERRAIN) == null))));
			}
		}
	}
	
	private void putWalls() {
		Entity dirtWall = EntityFactory.create("dirt wall");
		Entity dirtFloor = EntityFactory.create("dirt floor");
		Entity stoneWall = EntityFactory.create("stone wall");
		Entity stoneFloor = EntityFactory.create("stone floor");
		
		for(Tile tile : Walker.floorTiles) {
			for(Tile emptyTile : Map.getAdjacentTiles(tile, t -> t.get(Type.TERRAIN) == null)) {
				if(Map.countOrthogonalAdjacency(emptyTile, t -> !t.isTransitable(MovementType.WALK)) != 0) {
					emptyTile.put(tile.get(Type.TERRAIN).name.contains("dirt") ? dirtWall : stoneWall);
				}
				else {
					emptyTile.put(tile.get(Type.TERRAIN).name.contains("dirt") ? dirtFloor : stoneFloor);
				}
				if(emptyTile.get(Type.TERRAIN).name.contains("wall") && RNG.nextFloat() < 0.005) {
					emptyTile.put(EntityFactory.create("iron vein"));
				}
				else if(emptyTile.get(Type.TERRAIN).name.contains("floor")) {
					emptyTile.put(EntityFactory.create("luminescent mushroom"));
				}
			}
		}
	}
	
	public enum CaveSize{
		TINY(100),
		SMALL(200),
		MEDIUM(400),
		BIG(800),
		HUGE(1600);
		
		private int floorTiles;
		
		CaveSize(int tiles) {
			floorTiles = RNG.nextGaussian(tiles, tiles/33);
		}

		public int getFloorTiles() {
			return floorTiles;
		}
	}
	
}
