package map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.Blueprint;
import main.RoomFactory;

public class BlueprintTests {
	
	@Test
	public void getStairTileTest() {
		Blueprint bp = RoomFactory.createRoom("Dungeon starting rooms");
		int[] stairsCoord = bp.getStairsAnchor();
		assertEquals('<', bp.getArray()[stairsCoord[0]][stairsCoord[1]]);
	}

}
