package components;

import java.util.HashSet;
import java.util.Set;

import tile.Tile;

public class LightSourceC extends Component{
	
	public Set<Tile> illuminatedTiles = new HashSet<>();
	public float lightIntensity;
	
	public LightSourceC() {
		isShared = false;
	}

	@Override
	public Component clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(StringBuilder sb) {}
	
	@Override
	public void deserialize(String info) {}

	@Override
	public boolean equals(Component comp) {
		LightSourceC c = (LightSourceC) comp;
		if(lightIntensity != c.lightIntensity) return false;
		return illuminatedTiles.equals(c.illuminatedTiles);
	}

}
