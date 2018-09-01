package mountain;

import Chunk.Chunk;
import factories.TerrainFactory;

public class MountainLevel extends Chunk{
	
	public MountainLevel(int posX, int posY){
		coord = new int[] {posX, posY, 0};
		fillLevel(TerrainFactory.get("dirt floor"));
		buildLevel();
	}

	@Override
	protected void buildLevel() {
		
	}

}
