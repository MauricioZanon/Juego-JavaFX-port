package forest;

import RNG.Noise;
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
		fillLevel();
		buildLevel();
	}
	
	@Override
	protected void buildLevel() {
		float[][] noise = Noise.generatePerlinNoise(SIZE, SIZE, 5);
		for(int i = 0; i < noise.length; i++) {
			for(int j = 0; j < noise[0].length; j++) {
				if(noise[i][j] > 0.25) {
					chunkMap[i][j].put(EntityFactory.create("grass floor"));
				}else {
					chunkMap[i][j].put(EntityFactory.create("dirt floor"));
				}
			}
		}
		
		placeTrees();
		
		for(int i = 0; i < RNG.nextGaussian(SIZE / 2, SIZE / 10); i++){
			Tile tile = RNG.getRandom(chunkMap, t -> t.isTransitable(MovementType.WALK) && t.get(Type.FEATURE) == null);
			Entity feature = EntityFactory.create("rock");
			tile.put(feature);
		}
	}
	
	private void placeTrees() {
		for(int i = 0; i < RNG.nextGaussian(SIZE / 2, SIZE / 10); i++){
			Tile tile = RNG.getRandom(chunkMap, t -> t.isTransitable(MovementType.WALK) && t.get(Type.FEATURE) == null);
			Entity feature = EntityFactory.create("apple tree");
			tile.put(feature);
		}
	}

}
