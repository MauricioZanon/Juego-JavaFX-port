package components;

import java.util.HashSet;
import java.util.Set;

import tile.Tile;

public class LightSourceC extends Component{
	
	public Set<Tile> illuminatedTiles = new HashSet<>();
	public float lightIntensity;

	@Override
	public Component clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
