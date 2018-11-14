package player;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleFloatProperty;
import main.Entity;
import tile.Tile;

public class PlayerInfo {
	
	public static Entity PLAYER;// TODO: guardar el player aca y sacarlo de la clase Main
	
	public static final SimpleFloatProperty MAX_HP = new SimpleFloatProperty(0);
	public static final SimpleFloatProperty CUR_HP = new SimpleFloatProperty(0);
	
	public static final Set<Tile> viewedTiles = new HashSet<>();
	
	private PlayerInfo() {}
	
}
