package components;

import java.util.HashSet;

import tile.Tile;

public class VisionC extends Component{
	
	public VisionC() {
		isBase = false;
	}

	public int sightRange = 26; //TODO mover a atributos?
	
	public HashSet<Tile> visionMap = new HashSet<>();
	public HashSet<Tile> enemyTiles = new HashSet<>();
	
	@Override
	public Component clone() {
		VisionC vc = new VisionC();
		vc.sightRange = sightRange;
		vc.visionMap.addAll(visionMap);
		vc.enemyTiles.addAll(enemyTiles);
		return vc;
	}

	@Override
	public String serialize() {
		return "VIS " + sightRange;
	}
	
}
