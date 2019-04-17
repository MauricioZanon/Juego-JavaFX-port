package dungeon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import RNG.RNG;
import components.PositionC;
import dungeon.DungeonBuilder.DungeonSize;
import factories.EntityFactory;
import main.Blueprint;
import main.Entity;
import main.Room;
import main.RoomFactory;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public class DungeonRegularLevel extends DungeonLevel{
	
	public DungeonRegularLevel(PositionC exitStairPos, DungeonSize size) {
		int requestedRooms = size.roomQuantity;
		
		while(rooms.isEmpty()) {
			createFirstRoom(exitStairPos);
		}
		
		while(rooms.size() < requestedRooms) {
			if(availableAnchors.isEmpty()) {
				validLevel = false;
				return;
			}
			availableAnchors.removeIf(a -> a == null);
			PositionC anchorPos = RNG.getRandom(availableAnchors).pos;
			createRoom(anchorPos);
		}
		putDoors();
		putStairs();
		putEnemies();
		putItems();
	}

	/**
	 * Crea la primer habitación del nivel
	 * @param exitStairPos: El lugar en el que debe ir la escalera al nivel superior
	 */
	private void createFirstRoom(PositionC exitStairPos) {
		
		Blueprint bp = RoomFactory.createRoom("Dungeon starting rooms");
		int[] startingPosCorrection = bp.getStairsAnchor();
		PositionC startingPos = exitStairPos.clone();
		startingPos.coord[0] -= startingPosCorrection[0];
		startingPos.coord[1] -= startingPosCorrection[1];
		
		buildRoom(startingPos, null, bp);
	}
	
	/**
	 * Crea una habitación genérica en el nivel
	 * @param anchorPos: Es la posición en el nivel a la que estará unida la nueva habitación
	 */
	private void createRoom(PositionC anchorPos) {
		Tile emptyTile = RNG.getRandom(Map.getOrthogonalTiles(anchorPos.getTile(), t -> t.get(Type.TERRAIN) == null));
		if(emptyTile == null) return;
		Direction bpDirection = Direction.get(anchorPos, emptyTile.pos);
		Blueprint bp = RoomFactory.createRoom("Dungeon rooms", bpDirection);
		List<Integer[]> posibleAnchors = bp.getAnchors(bpDirection);
		Integer[] bpAnchor = RNG.getRandom(posibleAnchors);
		
		PositionC startingPos = anchorPos.clone();
		startingPos.coord[0] -= bpAnchor[0];
		startingPos.coord[1] -= bpAnchor[1];
		
		buildRoom(startingPos, anchorPos.getTile(), bp);
	}


	private void buildRoom(PositionC startingPos, Tile entranceTile, Blueprint bp) {
		Set<Tile> roomTiles = new HashSet<>();
		Set<Tile> doorTiles = new HashSet<>();
		Tile upStairTile = null;
		Tile downStairTile = null;
		Set<Tile> newAnchorTiles = new HashSet<>();
		
		char[][] bpArray = bp.getArray();
		for(int i = 0; i < bpArray.length; i++) {
			for(int j = 0; j < bpArray[0].length; j++) {
				Tile tile = Map.getTile(startingPos.coord[0] + i, startingPos.coord[1] + j, startingPos.coord[2]);
				char symbol = bpArray[i][j];
				switch(symbol) {
				case '.':
					if(!isValidTile(tile)) return;
					roomTiles.add(tile);
					break;
				case 'u':
					newAnchorTiles.add(tile);
					break;
				case '+':
					roomTiles.add(tile);
					doorTiles.add(tile);
					break;
				case '>':
					if(!isValidTile(tile)) return;
					roomTiles.add(tile);
					downStairTile = tile;
					break;
				case '<':
					if(!isValidTile(tile)) return;
					roomTiles.add(tile);
					upStairTile = tile;
					break;
				}
			}
		}
		
		if(entranceTile != null) {
			roomTiles.add(entranceTile);
			doors.add(entranceTile);
		}
		buildRoom(roomTiles);
		rooms.add(new Room(roomTiles));
		doors.addAll(doorTiles);
		upStair = upStairTile == null ? upStair: upStairTile.pos;
		downStair = downStairTile == null ? downStair : downStairTile.pos;
		availableAnchors.addAll(newAnchorTiles);
		availableAnchors.remove(entranceTile);
	}

	private void buildRoom(Set<Tile> roomTiles) {
		roomTiles.forEach(t -> t.put(FLOOR));
		
		for(Tile floorTile : roomTiles) {
			Map.getAdjacentTiles(floorTile, t -> t.get(Type.TERRAIN) == null).forEach(t -> t.put(WALL));
		}
	}
	
	private void putEnemies() {
		int quantity = RNG.nextGaussian(rooms.size()/2, rooms.size()/3);
		Set<Tile> availableTiles = new HashSet<>();
		rooms.forEach(r -> availableTiles.addAll(r.getFloorTiles()));
		while(quantity > 0) {
			Entity npc = EntityFactory.createRandom(Type.NPC);
			Tile tile = RNG.getRandom(availableTiles, t -> t.get(Type.FEATURE) == null);
			npc.addComponent(tile.pos.clone());
			tile.put(npc);
			quantity--;
		}
	}
	
	private void putItems() {
		int quantity = RNG.nextGaussian(rooms.size(), rooms.size()/3);
		Set<Tile> availableTiles = new HashSet<>();
		rooms.forEach(r -> availableTiles.addAll(r.getFloorTiles()));
		while(quantity > 0) {
			//FIXME decidir la rareza de cada item del dungeon aca
			Entity item = EntityFactory.createRandom(Type.ITEM);
			Tile tile = RNG.getRandom(availableTiles, t -> t.get(Type.FEATURE) == null);
			tile.put(item);
			quantity--;
		}
	}
}
