package cave;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import components.PositionC;
import factories.EntityFactory;
import main.Entity;
import main.MultiLevelLocation;
import main.Type;
import map.Map;
import tile.Tile;
import world.WorldBuilder;

public class Cave2 extends MultiLevelLocation{
	
	private Set<Tile> floorTiles = new HashSet<>();
	private Set<Tile> wallTiles = new HashSet<>();
	
	private final Entity DIRT_WALL = EntityFactory.create("dirt wall");
	private final Entity DIRT_FLOOR = EntityFactory.create("dirt floor");
	
	/**Hecho con cellular automaton, no sirve como cueva pero puede servir para otras cosas, como para agregar spots de pasto o arboles*/
	public Cave2(PositionC startingPos) {
		Entity stair = EntityFactory.create("down stair");
		stair.addComponent(startingPos);
		startingPos.getTile().put(stair);
		dig(startingPos);
		
		putWalls();
		
		WorldBuilder.getLocations().add(this);
	}
	
	private void dig(PositionC startingPos) {
		PositionC firstPos = startingPos.clone();
		firstPos.coord[2]++;
		
		Set<Tile> area = Map.getCircundatingAreaAsSet(40, firstPos.getTile(), true);
		for(Tile tile : area) {
			if(RNG.nextInt(100) < 55) {
				tile.put(DIRT_WALL);
				wallTiles.add(tile);
			}else {
				tile.put(DIRT_FLOOR);
				floorTiles.add(tile);
			}
		}
		for(int i = 0; i < 4; i++) {
			soften();
		}
		
	}
	
	private void soften() {
		Set<Tile> tiles = new HashSet<>();
		Set<Tile> newFloorTiles = new HashSet<>();
		Set<Tile> newWallTiles = new HashSet<>();
		tiles.addAll(floorTiles);
		tiles.addAll(wallTiles);
		
		for(Tile tile : tiles) {
			if(tile.get(Type.TERRAIN).name.equals("dirt wall") &&
					Map.countAdjacency(tile, t -> t.get(Type.TERRAIN).name.equals("dirt wall")) < 4) {
				newFloorTiles.add(tile);
			}
			else if(tile.get(Type.TERRAIN).name.equals("dirt floor") && 
					Map.countAdjacency(tile, t -> t.get(Type.TERRAIN).name.equals("dirt wall")) >= 6) {
				newWallTiles.add(tile);
			}
		}
		for(Tile tile : newFloorTiles) {
			tile.put(DIRT_FLOOR);
			floorTiles.add(tile);
			wallTiles.remove(tile);
		}
		for(Tile tile : newWallTiles) {
			tile.put(DIRT_WALL);
			wallTiles.add(tile);
			floorTiles.remove(tile);
		}
	}

	private void putWalls() {
		Entity wall = EntityFactory.create("dirt wall");
		for(Tile tile : floorTiles) {
			for(Tile emptyTile : Map.getAdjacentTiles(tile, t -> t.get(Type.TERRAIN) == null)) {
				emptyTile.put(wall);
			}
		}
	}
	
}
