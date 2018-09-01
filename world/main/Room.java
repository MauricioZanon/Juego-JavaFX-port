package main;

import java.util.HashSet;
import java.util.Set;

import map.Map;
import tile.Tile;

public class Room {
	
	private Set<Tile> floorTiles = new HashSet<>();
	
	public Room(Set<Tile> floorTiles){
		this.floorTiles = floorTiles;
	}

	public Set<Tile> getFloorTiles() {
		return floorTiles;
	}
	
	public Set<Tile> getDoorTiles(){
		Set<Tile> doorTiles = new HashSet<>();
		for(Tile tile : floorTiles) {
			for(Tile adjacentTile : Map.getAdjacentTiles(tile, t -> t.get(Type.FEATURE) != null)) {
				if(adjacentTile.get(Type.FEATURE).name.contains("door")) {
					doorTiles.add(adjacentTile);
				}
			}
		}
		return doorTiles;
	}
}
