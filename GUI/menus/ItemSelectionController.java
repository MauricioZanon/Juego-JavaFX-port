package menus;

import java.util.function.Predicate;

import application.Main;
import components.ContainerC;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.Entity;
import system.RenderSystem;

/**
 * Este controller se usa para todos los menus que sirvan solo para elegir un solo item para realizar una acción (Quaff, Wear, etc)
 * @author Mauro
 */
public class ItemSelectionController {

    @FXML private TreeView<Text> itemList;
    @FXML private Label title;
    @FXML private StackPane bottomBar;
    @FXML private TextField searchField;
    @FXML private TextFlow itemDesc;
    
    protected Predicate<Entity> filter = null;
    
    @FXML
    public void initialize() {
    	MenuUtils.resetObservables();
    	title.setText(MenuConfig.title);
    	
    	filter = MenuConfig.filter.and(item -> item.name.contains(searchField.getCharacters()));
    	
    	itemList.setRoot(new TreeItem<Text>());
    	Bindings.bindContentBidirectional(itemList.getRoot().getChildren(), MenuUtils.itemListItems);
    	Bindings.bindContentBidirectional(itemDesc.getChildren(), MenuUtils.itemDescText);
    	
    	MenuUtils.fillItemList(filter, Main.player.get(ContainerC.class));
    	
    	searchField.textProperty().addListener((value, oldValue, newValue) -> {
    		MenuUtils.fillItemList(filter, Main.player.get(ContainerC.class));
    	});
    	
    	itemList.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<Text>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
				MenuUtils.refreshItemDesc(MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class)));
			}
		});
    	
    }

    @FXML
    void handlePressedKeyInItemList(KeyEvent event) {
    	switch(event.getCode()) {
		case F:
			searchField.setVisible(true);
			searchField.requestFocus();
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
		case ENTER:
			RenderSystem.getInstance().changeScene("GameScreen.fxml");
			MenuConfig.action.accept(MenuUtils.getSelectedItem(itemList, Main.player.get(ContainerC.class)));
			break;
		default:
			break;
		}
		event.consume();
    }

}
