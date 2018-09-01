package chunk;

public class EmptyChunk extends Chunk{
	
	public EmptyChunk(int posX, int posY, int posZ){
		coord = new int[] {posX, posY, posZ};
		fillLevel(null);
	}

	@Override
	protected void buildLevel() {
		
	}
	
}
