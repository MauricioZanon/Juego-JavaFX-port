package Menus;

import actions.Drop;
import actions.Quaff;
import actions.Throw;
import actions.Wear;
import actions.Wield;
import application.Main;
import components.BodyComponent;
import console.Console;
import gameScreen.GameScreen;
import javafx.scene.control.ListView;
import main.Entity;
import main.Flags;
import main.Type;

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
		getSelectionModel().clearSelection();
		getItems().clear();
		if(item == null) return;
		
		if(item.TYPE.is(Type.WEAPON)) {
			if(Main.player.get(BodyComponent.class).getEquipment().contains(item)){
				getItems().add("Put away");
			}else {
				getItems().add("w - Wield");
			}
		}
		if(item.is(Flags.WEARABLE)) {
			if(Main.player.get(BodyComponent.class).getEquipment().contains(item)){
				getItems().add("T - Take off");
			}else {
				getItems().add("W - Wear");
			}
		}
		if(item.is(Flags.EDIBLE)) {
			getItems().add("e - Eat");
		}
		if(item.is(Flags.DRINKABLE)) {
			getItems().add("q - Quaff");
		}
		getItems().add("t - Throw");
		getItems().add("d - Drop");
	}
	
	private void createListener() {
		setOnKeyPressed(e -> {
			Entity item = ItemList.getInstance().getSelectedItem();
			switch(e.getCode()) {
			case NUMPAD2:
				getSelectionModel().selectNext();
				break;
			case NUMPAD8:
				getSelectionModel().selectPrevious();
				break;
			case ENTER:
				executeAction(getSelectionModel().getSelectedItem().split(" - ")[1]);
				break;
			case NUMPAD4:
			case ESCAPE:
				getSelectionModel().clearSelection();
				ItemList.getInstance().requestFocus();
				break;
			case D:
				executeAction("Drop");
				break;
			case E:
				if(item != null && item.is(Flags.EDIBLE)) {
					executeAction("Eat");
				}
				break;
			case Q:
				if(item != null && item.is(Flags.DRINKABLE)) {
					executeAction("Quaff)");
				}
				break;
			case T:
				executeAction("Throw");
				break;
			case W:
				if(e.isShiftDown()) {
					if(item != null && item.is(Flags.WEARABLE) && !Main.player.get(BodyComponent.class).getEquipment().contains(item)) {
						executeAction("Wear");
					}
				}else {
					if(item != null && item.TYPE.is(Type.WEAPON) && !Main.player.get(BodyComponent.class).getEquipment().contains(item)) {
						executeAction("Wield");
					}
				}
				break;
			default:
				break;
			}
			e.consume();
		});
	}
	
	protected void executeAction(String action) {
		Entity item = ItemList.getInstance().getSelectedItem();
		switch(action) {
		case "Drop":
			Drop.execute(Main.player, item);
			break;
		case "Eat":
			break;
		case "Put away":
			Main.player.get(BodyComponent.class).remove(item);
			Console.getInstance().addMessage("You put your " + item.name + " away.");
			break;
		case "Quaff":
			Quaff.execute(Main.player, item);
			break;
		case "Take off":
			Main.player.get(BodyComponent.class).remove(item);
			Console.getInstance().addMessage("You take off your " + item.name + ".");
			break;
		case "Throw":
			Throw.setListener();
			break;
		case "Wear":
			Wear.execute(Main.player, item);
			break;
		case "Wield":
			Wield.execute(Main.player, item);
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
