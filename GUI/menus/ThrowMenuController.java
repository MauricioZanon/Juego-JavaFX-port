package menus;

import actions.Throw;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import main.Type;
import system.RenderSystem;

public class ThrowMenuController extends BaseMenuController{
	
	@FXML
	public void initialize() {
		condition = i -> i.TYPE.is(Type.ITEM);
		fillItemList();
		
		itemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Text>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
				refreshItemDesc();
			}
		});
	}
	
	@FXML
	public void handlePressedKeyInItemList(KeyEvent key) {
		switch(key.getCode()) {
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
			Throw.setListener(getSelectedItem());
			break;
		default:
			break;
		}
		key.consume();
	}

}
