package player;

import javafx.beans.property.SimpleFloatProperty;

public class PlayerObserver {
	
	private PlayerObserver() {}
	
	public static final SimpleFloatProperty MAX_HP = new SimpleFloatProperty(0);
	public static final SimpleFloatProperty CUR_HP = new SimpleFloatProperty(0);
	

}
