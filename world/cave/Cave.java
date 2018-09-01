package cave;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import components.PositionComponent;
import factories.FeatureFactory;
import factories.TerrainFactory;
import main.Entity;
import main.MultiLevelLocation;
import main.Type;
import map.Map;
import tile.Tile;
import world.WorldBuilder;

public class Cave extends MultiLevelLocation{
	
	private Tile[][] caveArea;
	
	/**Hecho con random walks*/
	public Cave(PositionComponent entranceStairPos, CaveSize size) {
		PositionComponent exitStairPos = entranceStairPos.clone();
		exitStairPos.coord[2] += 1;
		
		caveArea = Map.getCircundatingAreaAsArray(size.floorTiles/10, exitStairPos.getTile(), false);
		
		dig(caveArea[caveArea.length/2][caveArea[0].length/2], size.floorTiles);
		
		Entity downStair = FeatureFactory.createFeature("down stair");
		downStair.addComponent(entranceStairPos);
		entranceStairPos.getTile().put(downStair);
		
		Entity upStair = FeatureFactory.createFeature("up stair");
		upStair.addComponent(exitStairPos);
		exitStairPos.getTile().put(upStair);
		
		putWalls();
		
		Miner.floorTiles.clear();
		
		WorldBuilder.getLocations().add(this);
	}
	
	private void dig(Tile startingTile, int floorTilesAmount) {
		Set<Miner> miners = new HashSet<>();
		miners.add(new Miner(startingTile, caveArea));
		miners.add(new Miner(startingTile, caveArea));
		miners.add(new Miner(startingTile, caveArea));
		
		while(Miner.getDiggedTiles() < floorTilesAmount) {
			Set<Miner> newMiners = new HashSet<>();
			Set<Miner> deactivatedMiners = new HashSet<>();
			
			for(Miner miner : miners) {
				if(!miner.activated) {
					deactivatedMiners.add(miner);
					continue;
				}
				miner.dig();
				if(RNG.nextInt(100) == 1) {
					Miner newMiner = miner.reproduce(caveArea);
					if(newMiner != null) {
						newMiners.add(newMiner);
					}
				}
			}
			miners.addAll(newMiners);
			miners.removeAll(deactivatedMiners);
			if(miners.isEmpty()) {
				miners.add(new Miner(RNG.getRandom(Miner.floorTiles, t -> Map.isOrthogonallyAdjacent(t, ti -> ti.get(Type.TERRAIN) == null)), caveArea));
			}
		}
	}
	
	private void putWalls() {
		Entity dirtWall = TerrainFactory.get("dirt wall");
		Entity dirtFloor = TerrainFactory.get("dirt floor");
		for(Tile tile : Miner.floorTiles) {
			for(Tile emptyTile : Map.getAdjacentTiles(tile, t -> t.get(Type.TERRAIN) == null)) {
				if(Map.countOrthogonalAdjacency(emptyTile, t -> !t.isTransitable()) != 0) {
					emptyTile.put(dirtWall);
				}else {
					emptyTile.put(dirtFloor);
				}
			}
		}
	}
	
	public enum CaveSize{
		TINY(100),
		SMALL(1000),
		MEDIUM(2000),
		BIG(4000),
		HUGE(8000);
		
		private int floorTiles;
		
		CaveSize(int tiles) {
			floorTiles = RNG.nextGaussian(tiles, tiles/33);
		}

		public int getFloorTiles() {
			return floorTiles;
		}
	}
	
}
