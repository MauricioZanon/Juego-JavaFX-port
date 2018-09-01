package map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import main.Blueprint;
import main.RoomFactory;
import world.Direction;

public class RoomFactoryTests {

	@Test
	@RepeatedTest(100)
	public void roomSouthDirectionTest() {
		Blueprint bp = RoomFactory.createRoom("Dungeon rooms", Direction.S);
		char[][] map = bp.getArray();
		boolean found = false;
		for(int i = 0; i < map.length; i++) {
			if(map[i][0] == 'u') {
				found = true;
				break;
			}
		}
		assertTrue(bp.toString(), found);
	}

	@Test
	@RepeatedTest(100)
	public void roomNorthDirectionTest() {
		Blueprint bp = RoomFactory.createRoom("Dungeon rooms", Direction.N);
		char[][] map = bp.getArray();
		boolean found = false;
		for(int i = 0; i < map.length; i++) {
			if(map[i][map[0].length-1] == 'u') {
				found = true;
				break;
			}
		}
		assertTrue(bp.toString(), found);
	}

	@Test
	@RepeatedTest(100)
	public void roomWestDirectionTest() {
		Blueprint bp = RoomFactory.createRoom("Dungeon rooms", Direction.W);
		char[][] map = bp.getArray();
		boolean found = false;
		for(int i = 0; i < map[0].length; i++) {
			if(map[map.length-1][i] == 'u') {
				found = true;
				break;
			}
		}
		assertTrue(bp.toString(), found);
	}
	
	@Test
	@RepeatedTest(100)
	public void roomEastDirectionTest() {
		Blueprint bp = RoomFactory.createRoom("Dungeon rooms", Direction.E);
		char[][] map = bp.getArray();
		boolean found = false;
		for(int i = 0; i < map[0].length; i++) {
			if(map[0][i] == 'u') {
				found = true;
				break;
			}
		}
		assertTrue(bp.toString(), found);
	}
	
	@Test
	public void requestNonExistenRoomType() {
		Blueprint bp = RoomFactory.createRoom("type");
		assertEquals(null, bp);
	}
}
