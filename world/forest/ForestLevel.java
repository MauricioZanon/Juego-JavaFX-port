package forest;

import RNG.RNG;
import chunk.Chunk;
import components.MovementC.MovementType;
import factories.EntityFactory;
import main.Entity;
import main.Type;
import tile.Tile;

public class ForestLevel extends Chunk{
	
	public ForestLevel(int posX, int posY){
		coord = new int[] {posX, posY, 0};
		fillLevel(EntityFactory.create("grass floor"));
		buildLevel();
	}
	
	@Override
	protected void buildLevel() {
		for(int i = 0; i < RNG.nextGaussian(Chunk.SIZE / 2, Chunk.SIZE / 10); i++){
			Tile tile = RNG.getRandom(chunkMap, t -> t.isTransitable(MovementType.WALK) && t.get(Type.FEATURE) == null);
			Entity feature = EntityFactory.create("apple tree");
			tile.put(feature);
		}
	}
	

}
