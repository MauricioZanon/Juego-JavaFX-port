package dungeon;

import RNG.RNG;
import components.PositionC;
import factories.EntityFactory;
import main.Entity;
import main.Type;

public class DungeonBuilder {
	
	public static void createDungeon(PositionC pos) {
		DungeonType type = RNG.getRandom(DungeonType.values());
		DungeonSize size = RNG.getRandom(DungeonSize.values());
		createDungeon(pos, type, size);
	}
	
	public static void createDungeon(PositionC entrancePos, DungeonType type, DungeonSize size) {
		int depth = RNG.nextGaussian(5, 3);
		DungeonLevel[] levels = new DungeonLevel[depth];
		
		Entity stair = EntityFactory.create("down stair");
		stair.addComponent(entrancePos);
		entrancePos.getTile().put(stair);
		
		PositionC startingPos = entrancePos.clone();
		startingPos.coord[2]++;
		
		for (int i = 0; i < depth;) {
			DungeonLevel level = null;
			switch(type) {
			case REGULAR:
				level = new DungeonRegularLevel(startingPos, size);
				break;
			case WATER:
				level = new DungeonWaterLevel(startingPos, size);
				break;
			default:
				level = new DungeonRegularLevel(startingPos, size);
				break;
			}
			if(level.isValidLevel()) {
				levels[i] = level;
				startingPos = level.getDownStair().clone();
				startingPos.coord[2]++;
				i++;
			}
		}
		levels[depth-1].getDownStair().getTile().remove(Type.FEATURE);
		new Dungeon(levels);
	}

	public enum DungeonType{
		REGULAR,
		LAVA,
		WATER,
		ABANDONED;
	}
	
	public enum DungeonSize{
		TINY(3),
		SMALL(9),
		MEDIUM(15),
		BIG(25),
		HUGE(40);
		
		public int roomQuantity;
		
		DungeonSize(int rooms) {
			roomQuantity = RNG.nextGaussian(rooms, rooms/33);
		}
	}
}

