package actions;

import java.util.ArrayDeque;

import application.Main;
import components.UsesC;
import components.UsesC.UseType;
import gameScreen.Console;
import main.Entity;
import system.RenderSystem;
import tile.Tile;

/** Examina un tile adyacente y, de ser posible, usa la entidad encontrada */
public class Examine {
	
	public static Tile examinedTile = null;
	
	private Examine() {}
	
	public static void execute(Tile tile) {
		ArrayDeque<Entity> entities = tile.getEntities(e -> e.has(UsesC.class));
		if(entities.isEmpty()) {
			Console.addMessage("There is nothing there that you can use.\n");
			EndTurn.execute(Main.player, ActionType.USE_ITEM);
		}
		else if(entities.size() == 1 && entities.getFirst().get(UsesC.class).uses.size() == 1) {
			useEntity(entities.getFirst(), entities.getFirst().get(UsesC.class).uses.get(0));
		}
		else {
			examinedTile = tile;
			RenderSystem.getInstance().changeScene("ExamineMenu.fxml", true);
		}
	}

	public static void useEntity(Entity usedEntity, UseType use) {
		switch(use) {
		case ACTIVATE:
			break;
		case CHOP:
			break;
		case CLOSE:
			Close.execute(Main.player, usedEntity);
			break;
		case CUT_BRANCH:
			break;
		case FISH:
			break;
		case GET_BARK:
			break;
		case HARVEST:
			Harvest.execute(Main.player, usedEntity);
			break;
		case LOCK:
			break;
		case MINE:
			break;
		case OPEN:
			Open.execute(Main.player, usedEntity);
			break;
		case PEEK:
			break;
		case REFILL_CONTAINER:
			break;
		case SEE_CONTENT:
			break;
		case TRUNK:
			break;
		case UNLOCK:
			break;
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
