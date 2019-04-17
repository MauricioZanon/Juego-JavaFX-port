package mountain;

import RNG.Noise;
import chunk.Chunk;
import factories.EntityFactory;

public class MountainLevel extends Chunk{
	
	public MountainLevel(int posX, int posY){
		coord = new int[] {posX, posY, 0};
		fillLevel(null);
		buildLevel();
	}

	@Override
	protected void buildLevel() {
		float[][] noise = Noise.generatePerlinNoise(SIZE, SIZE, 6);
		for(int i = 0; i < noise.length; i++) {
			for(int j = 0; j < noise[0].length; j++) {
				if(noise[i][j] > 0.45) {
					chunkMap[i][j].put(EntityFactory.create("dirt floor"));
				}else if(noise[i][j] > 0.25){
					chunkMap[i][j].put(EntityFactory.create("stone floor"));
				}else {
					chunkMap[i][j].put(EntityFactory.create("stone wall"));
				}
			}
		}
	}

}
