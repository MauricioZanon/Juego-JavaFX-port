package Menus;

import java.util.Set;

import actions.Drop;
import actions.Quaff;
import actions.Wield;
import application.Main;
import gameScreen.GameScreen;
import javafx.scene.control.ListView;
import main.Entity;
import main.Flags;

/**
 * Muestra las acciones posibles con el item seleccionado en @ItemList
 */
public class ActionList extends ListView<String>{
	
	public static ActionList instance = null;
	
	private ActionList() {
		setMouseTransparent(true);
		setStyle("-fx-border-color: black brown black brown;");
		setMaxWidth(150);
		createListener();
	}
	
	public void refresh() {
		Entity item = ItemList.getInstance().getSelectedItem();
		
		getItems().clear();
		if(item == null) return;
		
		getItems().add("[w]ield");
		
		Set<Flags> flags = item.getFlags();
		
		if(flags.contains(Flags.WEARABLE)) {
			getItems().add("[W]ear");
		}
		if(flags.contains(Flags.EDIBLE)) {
			getItems().add("[e]at");
		}
		if(flags.contains(Flags.DRINKABLE)) {
			getItems().add("[q]uaff");
		}
		getItems().add("[t]hrow");
		getItems().add("[d]rop");
	}
	
	private void createListener() {
		setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case NUMPAD2:
				getSelectionModel().selectNext();
				break;
			case NUMPAD8:
				getSelectionModel().selectPrevious();
				break;
			case ENTER:
				executeAction(getSelectionModel().getSelectedItem());
				break;
			case D:
				executeAction("[d]rop");
				break;
			case E:
				executeAction("[e]at");
				break;
			case Q:
				executeAction("[q]uaff");
				break;
			case T:
				executeAction("[t]hrow");
				break;
			case W:
				if(e.isShiftDown()) {
					executeAction("[W]ear");
				}
				else {
					executeAction("[w]ield");
				}
				break;
			case NUMPAD4:
			case ESCAPE:
				getSelectionModel().clearSelection();
				ItemList.getInstance().requestFocus();
				break;
			default:
				break;
			}
			e.consume();
		});
	}
	
	private void executeAction(String action) {
		Entity item = ItemList.getInstance().getSelectedItem();
		switch(action) {
		case "[d]rop":
			Drop.execute(Main.player, item);
			GameScreen.getInstance().hideMenu();
			break;
		case "[e]at":
			break;
		case "[q]uaff":
			Quaff.execute(Main.player, item);
			break;
		case "[t]hrow":
			break;
		case "[W]ear":
			break;
		case "[w]ield":
			Wield.execute(Main.player, item);
			GameScreen.getInstance().hideMenu();
			break;
		}
		GameScreen.getInstance().hideMenu();
	}
	
	public static ActionList getInstance() {
		if(instance == null) {
			instance = new ActionList();
		}
		return instance;
	}

}
