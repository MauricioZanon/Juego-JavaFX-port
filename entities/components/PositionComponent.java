package components;

import chunk.Chunk;
import main.Component;
import map.Map;
import tile.Tile;

/**Guarda las coordenadas globales de la entidad*/
public class PositionComponent extends Component{
	
	public int[] coord = new int[3];
	
	public int getGx() {
		if(coord[0] < 0) return coord[0] / Chunk.SIZE - 1;
		else return coord[0] / Chunk.SIZE;
	}
	public int getLx() {
		return coord[0] % Chunk.SIZE;
	}
	public int getGy() {
		if(coord[1] < 0) return coord[1] / Chunk.SIZE - 1;
		else return coord[1] / Chunk.SIZE;
	}
	public int getLy() {
		return coord[1] % Chunk.SIZE;
	}
	public int getGz() {
		return coord[2];
	}
	
	public Tile getTile(){
		return Map.getTile(coord[0], coord[1], coord[2]);
	}
	
	@Override
	public String toString(){
		return coord[0] + ":" + coord[1] + ":" + coord[2];
	}
	
	@Override
	public boolean equals(Object o) {
		int[] otherCoords = ((PositionComponent)o).coord;
		return coord[0] == otherCoords[0] && coord[1] == otherCoords[1] && coord[2] == otherCoords[2];
	}
	
	@Override
	public PositionComponent clone() {
		PositionComponent c = new PositionComponent();
		c.coord = new int[]{coord[0], coord[1], coord[2]};
		return c;
	}
	@Override
	public String serialize() {
		return "POS " + toString();
	}
}
