package field;

import Chunk.Chunk;
import factories.TerrainFactory;

public class FieldLevel extends Chunk{
	
	public FieldLevel(int posX, int posY){
		coord = new int[] {posX, posY, 0};
		fillLevel(TerrainFactory.get("grass floor"));
		buildLevel();
	}

	@Override
	protected void buildLevel() {
		
	}

}
