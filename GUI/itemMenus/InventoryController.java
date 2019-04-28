package itemMenus;

import actions.Drop;
import actions.Quaff;
import actions.Throw;
import actions.Wear;
import actions.Wield;
import application.Main;
import components.BodyC;
import components.ContainerC;
import gameScreen.Console;
import gameScreen.InputConfig;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.Entity;
import main.Flag;
import main.Type;
import system.RenderSystem;

public class InventoryController{
	
	@FXML public BorderPane root;
	@FXML public TreeView<Text> itemList;
	@FXML public ListView<String> actionList;
	@FXML public TextFlow itemDesc;
//	public StackPane searchBar;
	
	
	public void initialize() {
		itemList.setRoot(new TreeItem<Text>());
		
    	MenuUtils.fillItemList(itemList, MenuDataHolder.containers[0].items);
    	
//    	searchField.textProperty().addListener((value, oldValue, newValue) -> {
//    		MenuUtils.fillItemList(filter, Main.player.get(ContainerC.class));
//    	});
    	
    	itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		Entity selectedEntity = MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class));
			MenuUtils.refreshItemDesc(selectedEntity, itemDesc.getChildren());
			refreshActionList();
		});
//    	searchBar = (StackPane) RenderSystem.getInstance().loadNode("SearchBar.fxml");
//    	root.setBottom(searchBar);
	}
	
	@FXML
	public void handlePressedKeyInItemList(KeyEvent event) {
		Entity item = MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class));
		switch(event.getCode()) {
		case F:
//			searchBar.requestFocus();
			break;
		case D:
			executeAction("Drop");
			break;
		case E:
			if(item != null && item.is(Flag.EDIBLE)) {
				executeAction("Eat");
			}
			break;
		case Q:
			if(item != null && item.is(Flag.DRINKABLE)) {
				executeAction("Quaff)");
			}
			break;
		case T:
			executeAction("Throw");
			break;
		case W:
			if(event.isShiftDown()) {
				if(item != null && item.is(Flag.WEARABLE) && !Main.player.get(BodyC.class).getEquipment().contains(item)) {
					executeAction("Wear");
				}
			}else {
				if(item != null && item.type.is(Type.WEAPON) && !Main.player.get(BodyC.class).getEquipment().contains(item)) {
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
			RenderSystem.getInstance().closeSecondaryStage();
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
		Entity item = MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class));
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
			if(item != null && item.is(Flag.EDIBLE)) {
				executeAction("Eat");
			}
			break;
		case Q:
			if(item != null && item.is(Flag.DRINKABLE)) {
				executeAction("Quaff)");
			}
			break;
		case T:
			executeAction("Throw");
			break;
		case W:
			if(event.isShiftDown()) {
				if(item != null && item.is(Flag.WEARABLE) && !Main.player.get(BodyC.class).getEquipment().contains(item)) {
					executeAction("Wear");
				}
			}else {
				if(item != null && item.type.is(Type.WEAPON) && !Main.player.get(BodyC.class).getEquipment().contains(item)) {
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
		Entity item = MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class));
		actionList.getSelectionModel().clearSelection();
		actionList.getItems().clear();
		if(item == null) return;
		
		if(item.type.is(Type.WEAPON)) {
			if(Main.player.get(BodyC.class).getEquipment().contains(item)){
				actionList.getItems().add("p - Put away");
			}else {
				actionList.getItems().add("w - Wield");
			}
		}
		if(item.is(Flag.WEARABLE)) {
			if(Main.player.get(BodyC.class).getEquipment().contains(item)){
				actionList.getItems().add("T - Take off");
			}else {
				actionList.getItems().add("W - Wear");
			}
		}
		if(item.is(Flag.EDIBLE)) {
			actionList.getItems().add("e - Eat");
		}
		if(item.is(Flag.DRINKABLE)) {
			actionList.getItems().add("q - Quaff");
		}
		actionList.getItems().add("t - Throw");
		actionList.getItems().add("d - Drop");
	}
	
	private void executeAction(String action) {
		RenderSystem.getInstance().closeSecondaryStage();
		Entity item = MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class));
		switch(action) {
		case "Drop":
			Drop.execute(Main.player, item);
			break;
		case "Eat":
			break;
		case "Put away":
			Main.player.get(BodyC.class).remove(item);
			Console.addMessage("You put your " + item.name + " away.\n");
			break;
		case "Quaff":
			Quaff.execute(Main.player, item);
			break;
		case "Take off":
			Main.player.get(BodyC.class).remove(item);
			Console.addMessage("You take off your " + item.name + ".\n");
			break;
		case "Throw":
			Throw.thrownItem = MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class));
			InputConfig.setThrowInput();
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
