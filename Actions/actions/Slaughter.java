package actions;

import application.Main;
import components.ButcherC;
import components.ContainerC;
import components.FieldDressC;
import components.PositionC;
import components.SkinC;
import factories.ItemFactory;
import main.Entity;
import tile.Tile;

public abstract class Slaughter {
	
	public static void execute(Entity corpse, String type) {
		Tile tile = corpse.get(PositionC.class).getTile();
		
		if(corpse.has(ContainerC.class)) {
			corpse.get(ContainerC.class).removeAll().forEach(i -> tile.put(i));
		}
		
		switch(type) {
		case "Field dress":
			ItemFactory.getItems(corpse.get(FieldDressC.class).items).forEach(i -> tile.put(i));
			corpse.removeComponent(FieldDressC.class);
			break;
		case "Butcher":
			ItemFactory.getItems(corpse.get(ButcherC.class).items).forEach(i -> tile.put(i));
			corpse.removeComponent(ButcherC.class);
			break;
		case "Skin":
			ItemFactory.getItems(corpse.get(SkinC.class).items).forEach(i -> tile.put(i));
			corpse.removeComponent(SkinC.class);
			break;
		}
		
		if(!corpse.has(FieldDressC.class) && !corpse.has(ButcherC.class) && !corpse.has(SkinC.class)) {
			tile.remove(corpse);
		}

		EndTurn.execute(Main.player, ActionType.USE_ITEM);
		
	}
	

}
