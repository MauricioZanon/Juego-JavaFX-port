package menus;

import application.Main;
import components.ContainerC;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import system.RenderSystem;

/**
 * Este controller se usa para todos los menus que sirvan solo para elegir un solo item para realizar una acción (Quaff, Wear, etc)
 * @author Mauro
 */
public class ItemSelectionController {

	@FXML public BorderPane root;
	@FXML public TreeView<Text> itemList;
    @FXML public Label title;
    @FXML public TextFlow itemDesc;
	public StackPane searchBar;
    
    @FXML
    public void initialize() {
    	MenuUtils.resetObservables();
    	title.setText(MenuConfig.title);
    	
    	MenuUtils.filter = MenuConfig.filter;
    	MenuUtils.entities = Main.player.get(ContainerC.class).items;
    	
    	itemList.setRoot(new TreeItem<Text>());
    	Bindings.bindContentBidirectional(itemList.getRoot().getChildren(), MenuUtils.shownEntities);
    	Bindings.bindContentBidirectional(itemDesc.getChildren(), MenuUtils.itemDescText);
    	
    	MenuUtils.fillItemList();
    	
    	itemList.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<Text>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
				MenuUtils.refreshItemDesc(MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class)));
			}
		});
    	
    	searchBar = (StackPane) RenderSystem.getInstance().loadNode("SearchBar.fxml");
    	root.setBottom(searchBar);
    	
    }

    @FXML
    public void handlePressedKeyInItemList(KeyEvent event) {
    	switch(event.getCode()) {
		case F:
			searchBar.requestFocus();
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
		case ENTER:
			RenderSystem.getInstance().closeSecondaryStage();
			MenuConfig.action.accept(MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class)));
			break;
		default:
			break;
		}
		event.consume();
    }

}
