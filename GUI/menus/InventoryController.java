package menus;

import actions.Drop;
import actions.Quaff;
import actions.Throw;
import actions.Wear;
import actions.Wield;
import application.Main;
import components.BodyComponent;
import gameScreen.Console;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import main.Entity;
import main.Flags;
import main.Type;
import system.RenderSystem;

public class InventoryController extends BaseMenuController{
	
	@FXML public ListView<String> actionList;
	
	public void initialize() {
		condition = i -> i.TYPE.is(Type.ITEM);
		fillItemList();
		
		itemList.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<Text>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
				refreshItemDesc();
				refreshActionList();
			}
		});
	}
	
	@FXML
	public void handlePressedKeyInItemList(KeyEvent event) {
		Entity item = getSelectedItem();
		switch(event.getCode()) {
		case F:
			searchField.setVisible(true);
			searchField.requestFocus();
			break;
		case C:
			searchField.clear();
			fillItemList();
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
			if(event.isShiftDown()) {
				if(item != null && item.is(Flags.WEARABLE) && !Main.player.get(BodyComponent.class).getEquipment().contains(item)) {
					executeAction("Wear");
				}
			}else {
				if(item != null && item.TYPE.is(Type.WEAPON) && !Main.player.get(BodyComponent.class).getEquipment().contains(item)) {
					executeAction("Wield");
				}
			}
			break;
		case DOWN:
		case NUMPAD2:
			itemList.getSelectionModel().selectNext();
			break;
		case UP:
		case NUMPAD8:
			itemList.getSelectionModel().selectPrevious();
			break;
		case ESCAPE:
			RenderSystem.getInstance().changeScene("GameScreen.fxml");
			break;
		case RIGHT:
		case NUMPAD6:
		case ENTER:
			if(item != null) {
				actionList.getSelectionModel().select(0);
				actionList.requestFocus();
			}
			break;
		default:
			break;
		}
		event.consume();
	}
	
	@FXML
	public void handleKeyPressedInActionList(KeyEvent event) {
		Entity item = getSelectedItem();
		switch(event.getCode()) {
		case NUMPAD2:
			actionList.getSelectionModel().selectNext();
			break;
		case NUMPAD8:
			actionList.getSelectionModel().selectPrevious();
			break;
		case ENTER:
			executeAction(actionList.getSelectionModel().getSelectedItem().split(" - ")[1]);
			break;
		case LEFT:
		case NUMPAD4:
		case ESCAPE:
			actionList.getSelectionModel().clearSelection();
			itemList.requestFocus();
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
			if(event.isShiftDown()) {
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
		event.consume();
	}

	
	private void refreshActionList() {
		Entity item = getSelectedItem();
		actionList.getSelectionModel().clearSelection();
		actionList.getItems().clear();
		if(item == null) return;
		
		if(item.TYPE.is(Type.WEAPON)) {
			if(Main.player.get(BodyComponent.class).getEquipment().contains(item)){
				actionList.getItems().add("p - Put away");
			}else {
				actionList.getItems().add("w - Wield");
			}
		}
		if(item.is(Flags.WEARABLE)) {
			if(Main.player.get(BodyComponent.class).getEquipment().contains(item)){
				actionList.getItems().add("T - Take off");
			}else {
				actionList.getItems().add("W - Wear");
			}
		}
		if(item.is(Flags.EDIBLE)) {
			actionList.getItems().add("e - Eat");
		}
		if(item.is(Flags.DRINKABLE)) {
			actionList.getItems().add("q - Quaff");
		}
		actionList.getItems().add("t - Throw");
		actionList.getItems().add("d - Drop");
	}
	
	private void executeAction(String action) {
		RenderSystem.getInstance().changeScene("GameScreen.fxml");
		Entity item = getSelectedItem();
		switch(action) {
		case "Drop":
			Drop.execute(Main.player, item);
			break;
		case "Eat":
			break;
		case "Put away":
			Main.player.get(BodyComponent.class).remove(item);
			Console.addMessage("You put your " + item.name + " away.\n");
			break;
		case "Quaff":
			Quaff.execute(Main.player, item);
			break;
		case "Take off":
			Main.player.get(BodyComponent.class).remove(item);
			Console.addMessage("You take off your " + item.name + ".\n");
			break;
		case "Throw":
			Throw.setListener(getSelectedItem());
			break;
		case "Wear":
			Wear.execute(Main.player, item);
			break;
		case "Wield":
			Wield.execute(Main.player, item);
			break;
		}
	}

}
