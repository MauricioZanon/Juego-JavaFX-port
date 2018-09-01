package dungeon;

import main.MultiLevelLocation;
import world.WorldBuilder;

public class Dungeon extends MultiLevelLocation{
	
	private DungeonLevel[] levels;
	
	public Dungeon(DungeonLevel[] levels){
		this.levels = levels;
		WorldBuilder.getLocations().add(this);
	}

	public DungeonLevel[] getLevels() {
		return levels;
	}
	
}
