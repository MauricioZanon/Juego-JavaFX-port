package actions;

import java.util.ArrayDeque;

import application.Main;
import components.UsesC;
import components.UsesC.UseType;
import gameScreen.Console;
import itemMenus.MenuFactory;
import main.Entity;
import tile.Tile;

public abstract class Use {
	
	public static Entity usedEntity;
	
	public static void execute(Entity used, Entity user, UseType use) {
		usedEntity = used;
		switch(use) {
		case ACTIVATE -> {}
		case CHOP -> {}
		case CLOSE -> Close.execute(user, usedEntity);
		case CUT_BRANCH -> {}
		case FISH -> {}
		case GET_BARK -> {}
		case HARVEST -> Harvest.execute(user, usedEntity);
		case LOCK -> {}
		case MINE -> {}
		case OPEN -> Open.execute(user, usedEntity);
		case PEEK -> {}
		case REFILL_CONTAINER -> {}
		case SEE_CONTENT -> MenuFactory.openGetItemsMenu();
		case TRUNK -> {}
		case UNLOCK -> {}
		};
		
	}

	public static void execute(Tile tile) {
		ArrayDeque<Entity> usables = tile.getEntities(e -> e.has(UsesC.class));
		if(usables.isEmpty()) {
			Console.addMessage("There is nothing you can use here.\n");
		}
		else if(usables.size() == 1) {
			execute(usables.getFirst(), Main.player, usables.getFirst().get(UsesC.class).quickUse);
		}
		else {
			Examine.execute(tile);
		}
	}

}

/**
 * si es una planta y tiene containerC >>>>>>>> HARVEST
 * si es una puerta y esta abierta >>>>>>>>>>>> CLOSE
 * 					si esta cerrada >>>>>>>>>>> OPEN, PEEK, TRUNK, LOCK, UNLOCK
 * si es un boton o una palanca >>>>>>>>>>>>>>> ACTIVATE
 * si es un tile con agua >>>>>>>>>>>>>>>>>>>>> REFILL_CONTAINER, FISH
 */
