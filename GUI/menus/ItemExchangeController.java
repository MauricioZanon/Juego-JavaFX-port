//package menus;
//
//import java.util.EnumMap;
//import java.util.List;
//
//import actions.Examine;
//import application.Main;
//import components.ContainerC;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.fxml.FXML;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TreeItem;
//import javafx.scene.control.TreeView;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
//import main.Entity;
//import main.Type;
//import system.RenderSystem;
//import text.StringUtils;
//
//public class ItemExchangeController{
//
//    @FXML public TreeView<Text> leftItemList;
//    @FXML public TreeView<Text> rightItemList;
//    @FXML public TextFlow itemDesc;
//    @FXML public StackPane bottomBar;
//    @FXML public TextField searchField;
//    
//    protected EnumMap<Type, TreeItem<Text>> categories2 = createCategories();
//    
//    @FXML
//    public void initialize() {
//    	condition = t -> true;
//    	fillItemList();
//    	fillItemList2();
//    	
//    	itemList.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<Text>>() {
//			@Override
//			public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
//				refreshItemDesc(getSelectedItem());
//			}
//		});
//    	leftItemList.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<Text>>() {
//    		@Override
//    		public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
//    			refreshItemDesc(getSelectedItem());
//    		}
//    	});
//    	
//    }
//
//    @FXML
//    public void fillItemList2() {
//    	leftItemList.setRoot(new TreeItem<Text>());
//    	
//		ContainerC inv = Examine.examinedTile.get(Type.CONTAINER).get(ContainerC.class);
//		for(Type type : categories2.keySet()) {
//			TreeItem<Text> categoryBranch = categories2.get(type);
//			categoryBranch.getChildren().clear();
//			
//			List<Entity> items = inv.getOcurrences(type);
//			if(items.isEmpty()) continue;
//			
//			for(Entity item : items) {
//				if(condition.test(item) && item.name.contains(searchField.getCharacters())) {
//					Text nameText = new Text(StringUtils.createItemName(item, inv));
//					nameText.setFill(Color.WHITE);
//					categoryBranch.getChildren().add(new TreeItem<Text>(nameText));
//				}
//			}
//			if(!categoryBranch.getChildren().isEmpty()) {
//				leftItemList.getRoot().getChildren().add(categoryBranch);
//			}
//		}
//    }
//
//    @FXML
//    public void handlePressedKeyInItemList(KeyEvent event) {
//    	Entity item = getSelectedItem();
//		switch(event.getCode()) {
//		case F:
//			searchField.setVisible(true);
//			searchField.requestFocus();
//			break;
//		case C:
//			searchField.clear();
//			fillItemList();
//			break;
//		case DOWN:
//		case NUMPAD2:
//			itemList.getSelectionModel().selectNext();
//			break;
//		case UP:
//		case NUMPAD8:
//			itemList.getSelectionModel().selectPrevious();
//			break;
//		case ESCAPE:
//			RenderSystem.getInstance().closeSecondaryStage();
//			break;
//		case RIGHT:
//		case NUMPAD6:
//		case TAB:
//			leftItemList.requestFocus();
//			break;
//		case SPACE:
//			transferAll();
//			fillItemList();
//			fillItemList2();
//			break;
//		case ENTER:
//			if(item != null) {
//				Main.player.get(ContainerC.class).remove(item.name, 1);
//				Examine.examinedTile.get(Type.CONTAINER).get(ContainerC.class).add(item);
//				fillItemList();
//				fillItemList2();
//			}
//			break;
//		default:
//			break;
//		}
//		event.consume();
//    }
//
//	@FXML
//    public void handlePressedKeyInItemList2(KeyEvent event) {
//    	Entity item = getSelectedItem();
//		switch(event.getCode()) {
//		case F:
//			searchField.setVisible(true);
//			searchField.requestFocus();
//			break;
//		case C:
//			searchField.clear();
//			fillItemList();
//			break;
//		case DOWN:
//		case NUMPAD2:
//			leftItemList.getSelectionModel().selectNext();
//			break;
//		case UP:
//		case NUMPAD8:
//			leftItemList.getSelectionModel().selectPrevious();
//			break;
//		case ESCAPE:
//			RenderSystem.getInstance().closeSecondaryStage();
//			break;
//		case LEFT:
//		case NUMPAD4:
//		case TAB:
//			itemList.requestFocus();
//			break;
//		case SPACE:
//			transferAll();
//			fillItemList();
//			fillItemList2();
//			break;
//		case ENTER:
//			if(item != null) {
//				int selectIndex = leftItemList.getSelectionModel().getSelectedIndex();
//				Examine.examinedTile.get(Type.CONTAINER).get(ContainerC.class).remove(item.name, 1);
//				Main.player.get(ContainerC.class).add(item);
//				fillItemList();
//				fillItemList2();
//				leftItemList.getSelectionModel().select(selectIndex);
//			}
//			break;
//		default:
//			break;
//		}
//		event.consume();
//    }
//    
//    private void transferAll() {
//    	ContainerC playerInv = Main.player.get(ContainerC.class);
//    	ContainerC examinedContainer = Examine.examinedTile.get(Type.CONTAINER).get(ContainerC.class);
//		if(leftItemList.isFocused()) {
//			examinedContainer.addAll(playerInv.removeAll());
//		}
//		else {
//			playerInv.addAll(examinedContainer.removeAll());
//		}
//	}
//    
//    @Override
//	protected Entity getSelectedItem() {
//    	if(leftItemList.isFocused()) {
//    		TreeItem<Text> selectedItem = leftItemList.getSelectionModel().getSelectedItem();
//    		if(selectedItem == null) {
//    			return null;
//    		}else {
//    			String selectedItemName = selectedItem.getValue().getText().replaceAll("\\sx\\d", "");
//    			return Examine.examinedTile.get(Type.CONTAINER).get(ContainerC.class).get(selectedItemName);
//    		}
//    	}
//    	else {
//    		
//    		TreeItem<Text> selectedItem = itemList.getSelectionModel().getSelectedItem();
//    		if(selectedItem == null) {
//    			return null;
//    		}else {
//    			String selectedItemName = selectedItem.getValue().getText().replaceAll("\\sx\\d", "");
//    			return Main.player.get(ContainerC.class).get(selectedItemName);
//    		}
//    	}
//	}
//
//}