package components;

import java.util.HashSet;

import main.Component;
import tile.Tile;

public class VisionComponent extends Component{
	
	public int sightRange = 49;
	
	public HashSet<Tile> visionMap = new HashSet<>();
	public HashSet<Tile> enemyTiles = new HashSet<>();
	
	@Override
	public Component clone() {
		VisionComponent vc = new VisionComponent();
		vc.sightRange = sightRange;
		vc.visionMap.addAll(visionMap);
		vc.enemyTiles.addAll(enemyTiles);
		return vc;
	}
	
}
