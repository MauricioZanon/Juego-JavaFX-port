package menus;

import actions.Quaff;
import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import main.Type;
import system.RenderSystem;

public class QuaffMenuController extends BaseMenuController{
	
	@FXML
	public void initialize() {
		condition = i -> i.TYPE.is(Type.POTION);
		fillItemList();
		
		itemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Text>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Text>> observable, TreeItem<Text> oldValue, TreeItem<Text> newValue) {
				refreshItemDesc();
			}
		});
	}
	
	@FXML
	public void handlePressedKeyInItemList(KeyEvent event) {
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
			Quaff.execute(Main.player, getSelectedItem());
			break;
		default:
			break;
		}
		event.consume();
	}
	

}
