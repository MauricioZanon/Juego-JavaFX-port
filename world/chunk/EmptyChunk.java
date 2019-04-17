package chunk;

public class EmptyChunk extends Chunk{
	
	public EmptyChunk(String coord) {
		String[] coordArray = coord.split(":");
		this.coord = new int[] {
				Integer.parseInt(coordArray[0]),
				Integer.parseInt(coordArray[1]),
				Integer.parseInt(coordArray[2])
		};
		fillLevel(null);
	}

	@Override
	protected void buildLevel() {
		
	}
	
}
