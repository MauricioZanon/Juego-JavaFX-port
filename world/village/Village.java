package village;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import RNG.RNG;
import components.PositionC;
import factories.EntityFactory;
import main.Blueprint;
import main.Entity;
import main.Location;
import main.Room;
import main.RoomFactory;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;
import world.WorldBuilder;

public class Village extends Location{
	
	private Set<Tile> road;
	private Set<Room> houses = new HashSet<>();
	
	public Village(PositionC pos) {
		build(pos);
		WorldBuilder.getLocations().add(this);
	}

	public void build(PositionC pos) {
		Set<Tile> villageArea = Map.getCircundatingAreaAsSet(100, pos.getTile(), true);
		clearArea(villageArea);
		createRoads(pos);
		refillArea(villageArea);
		
		Predicate<Tile> isGrassFloor = t -> t.get(Type.TERRAIN) != null && t.get(Type.TERRAIN).name.equals("grass floor");
		Predicate<Tile> adjacentToGrassFloor = t -> Map.isOrthogonallyAdjacent(t, isGrassFloor);
		while(houses.size() < 10) {
			Tile roadAnchor = RNG.getRandom(road, adjacentToGrassFloor);
			createHouse(roadAnchor);
		}
		
	}

	private void clearArea(Set<Tile> villageArea) {
		villageArea.forEach(t -> t.remove(Type.TERRAIN));
		
	}
	
	private void refillArea(Set<Tile> villageArea) {
		for(Tile tile : villageArea) {
			if(tile.get(Type.TERRAIN) == null) {
				tile.put(EntityFactory.create("grass floor"));
			}
		}
	}

	private void createRoads(PositionC pos) {
		
		int roadsWidth = RNG.nextInt(2, 4);
		int roadLength = RNG.nextGaussian(40, 20);
		Set<Tile> roadTiles = new HashSet<>();
		
		PositionC auxPos = pos.clone();
		
		//Camino al este
		roadTiles.addAll(Map.getSquareAreaAsSet(auxPos, roadsWidth, roadLength));
		
		//Camino al norte
		roadLength = RNG.nextGaussian(40, 20);
		roadTiles.addAll(Map.getSquareAreaAsSet(auxPos, roadLength, roadsWidth));
		
		//Camino al oeste
		auxPos = pos.clone();
		roadLength = RNG.nextGaussian(40, 20);
		auxPos.coord[0] -= roadLength;
		roadTiles.addAll(Map.getSquareAreaAsSet(auxPos, roadsWidth, roadLength));
		
		//Camino al sur
		auxPos = pos.clone();
		roadLength = RNG.nextGaussian(40, 20);
		auxPos.coord[1] -= roadLength;
		roadTiles.addAll(Map.getSquareAreaAsSet(auxPos, roadLength, roadsWidth));
		
		if(validRoad(roadTiles)) {
			roadTiles.forEach(t -> {
				t.put(EntityFactory.create("concrete floor"));
				t.remove(Type.FEATURE);
			});
		}
		road = roadTiles;
	}

	private boolean validRoad(Set<Tile> road) {
		for(Tile tile : road) {
			if(tile.get(Type.TERRAIN) != null) {
				return false;
			}
		}
		return true;
	}
	
	private void createHouse(Tile roadAnchor) {
		Set<Tile> tiles = Map.getOrthogonalTiles(roadAnchor, t -> t.get(Type.TERRAIN).name.equals("grass floor"));

		Tile initialHouseTile = RNG.getRandom(tiles);
		
		Direction dir = Direction.get(roadAnchor, initialHouseTile);
		Blueprint bp = RoomFactory.createRoom("Houses", dir);
		List<Integer[]> posibleAnchors = bp.getAnchors(dir);
		Integer[] bpAnchor = RNG.getRandom(posibleAnchors);
		PositionC startingPos = initialHouseTile.pos.clone();
		
		startingPos.coord[0] -= bpAnchor[0];
		startingPos.coord[1] -= bpAnchor[1];
		
		Set<Tile> floors = new HashSet<>();
		Set<Tile> walls = new HashSet<>();
		Set<Tile> doors = new HashSet<>();
		
		char[][] bpArray = bp.getArray();
		for(int i = 0; i < bpArray.length; i++) {
			for(int j = 0; j < bpArray[0].length; j++) {
				Tile tile = Map.getTile(startingPos.coord[0] + i, startingPos.coord[1] + j, startingPos.coord[2]);
				if(tile == null || !validHouseTile(tile)) return;

				char symbol = bpArray[i][j];
				switch(symbol) {
				case '.':
					floors.add(tile);
					break;
				case 'u':
				case '+':
					doors.add(tile);
					floors.add(tile);
					break;
				case '#':
					walls.add(tile);
					break;
				}
			}
		}
		buildHouse(walls, floors, doors);
	}
	
	private boolean validHouseTile(Tile tile) {
		return tile.get(Type.TERRAIN) != null && tile.get(Type.TERRAIN).name.equals("grass floor");
	}

	private void buildHouse(Set<Tile> wallTiles, Set<Tile> floorTiles, Set<Tile> doorTiles){
		Entity houseWall = EntityFactory.create("wooden wall");
		Entity houseFloor = EntityFactory.create("wooden floor");
		
		wallTiles.forEach(t -> {
			t.remove(Type.FEATURE);
			t.put(houseWall);
		});
		floorTiles.forEach(t -> {
			t.remove(Type.FEATURE);
			t.put(houseFloor);
		});
		doorTiles.forEach(t -> {
			t.put(EntityFactory.create("closed door"));
			t.put(houseFloor);
		});
		houses.add(new Room(floorTiles));
	}

}
