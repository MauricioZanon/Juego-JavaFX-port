package dungeon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import RNG.RNG;
import components.PositionComponent;
import dungeon.DungeonBuilder.DungeonSize;
import factories.ItemFactory;
import factories.NPCFactory;
import main.Blueprint;
import main.Entity;
import main.Room;
import main.RoomFactory;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public class DungeonRegularLevel extends DungeonLevel{
	
	public DungeonRegularLevel(PositionComponent exitStairPos, DungeonSize size) {
		int requestedRooms = size.roomQuantity;
		
		while(rooms.isEmpty()) {
			createFirstRoom(exitStairPos);
		}
		
		while(rooms.size() < requestedRooms) {
			if(availableAnchors.isEmpty()) {
				validLevel = false;
				return;
			}
			//FIXME tira null pointer porque en la lista de anchors se guardan nulls en algun momento
			PositionComponent anchorPos = RNG.getRandom(availableAnchors).getPos();
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
	private void createFirstRoom(PositionComponent exitStairPos) {
		
		Blueprint bp = RoomFactory.createRoom("Dungeon starting rooms");
		int[] startingPosCorrection = bp.getStairsAnchor();
		PositionComponent startingPos = exitStairPos.clone();
		startingPos.coord[0] -= startingPosCorrection[0];
		startingPos.coord[1] -= startingPosCorrection[1];
		
		buildRoom(startingPos, null, bp);
	}
	
	/**
	 * Crea una habitación genérica en el nivel
	 * @param anchorPos: Es la posición en el nivel a la que estará unida la nueva habitación
	 */
	private void createRoom(PositionComponent anchorPos) {
		Tile emptyTile = RNG.getRandom(Map.getOrthogonalTiles(anchorPos.getTile(), t -> t.get(Type.TERRAIN) == null));
		if(emptyTile == null) return;
		Direction bpDirection = Direction.get(anchorPos, emptyTile.getPos());
		Blueprint bp = RoomFactory.createRoom("Dungeon rooms", bpDirection);
		List<Integer[]> posibleAnchors = bp.getAnchors(bpDirection);
		Integer[] bpAnchor = RNG.getRandom(posibleAnchors);
		
		PositionComponent startingPos = anchorPos.clone();
		startingPos.coord[0] -= bpAnchor[0];
		startingPos.coord[1] -= bpAnchor[1];
		
		buildRoom(startingPos, anchorPos.getTile(), bp);
	}


	private void buildRoom(PositionComponent startingPos, Tile entranceTile, Blueprint bp) {
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
		upStair = upStairTile == null ? upStair: upStairTile.getPos();
		downStair = downStairTile == null ? downStair : downStairTile.getPos();
		availableAnchors.addAll(newAnchorTiles);
		availableAnchors.remove(entranceTile);
	}

	private void buildRoom(Set<Tile> roomTiles) {
		for(Tile floorTile : roomTiles) {
			floorTile.put(FLOOR);
			Map.getAdjacentTiles(floorTile, t -> t.get(Type.TERRAIN) == null).forEach(t -> t.put(WALL));
		}
	}
	
	private void putEnemies() {
		int quantity = RNG.nextGaussian(rooms.size()/2, rooms.size()/3);
		Set<Tile> availableTiles = new HashSet<>();
		rooms.forEach(r -> availableTiles.addAll(r.getFloorTiles()));
		while(quantity > 0) {
			Entity npc = NPCFactory.createNPC();
			Tile tile = RNG.getRandom(availableTiles, t -> t.get(Type.FEATURE) == null);
			npc.addComponent(tile.getPos().clone());
			tile.put(npc);
			quantity--;
		}
	}
	
	private void putItems() {
		int quantity = RNG.nextGaussian(rooms.size(), rooms.size()/3);
		Set<Tile> availableTiles = new HashSet<>();
		rooms.forEach(r -> availableTiles.addAll(r.getFloorTiles()));
		while(quantity > 0) {
			Entity item = ItemFactory.createRandomItem();
			Tile tile = RNG.getRandom(availableTiles, t -> t.get(Type.FEATURE) == null);
			tile.put(item);
			quantity--;
		}
	}
}
