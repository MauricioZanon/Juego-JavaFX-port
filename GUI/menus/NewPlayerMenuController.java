package menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import system.RenderSystem;

public class NewPlayerMenuController {

    @FXML private TabPane tabs;
    @FXML private ListView<String> raceList;
    @FXML private TextFlow raceDesc;
    @FXML private ListView<String> profList;
    @FXML private TextFlow profDesc;
    @FXML private ListView<String> sceneList;
    @FXML private TextFlow sceneDesc;
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> sexChoice;

    public void initialize() {
    	sexChoice.getItems().add("Male");
    	sexChoice.getItems().add("Female");
    }
    
    @FXML
	public void handlePressedKey(KeyEvent key) {
		switch(key.getCode()) {
		case TAB:
			if(key.isShiftDown()) {
				tabs.getSelectionModel().selectPrevious();
			}else {
				tabs.getSelectionModel().selectNext();
			}
			break;
		case ESCAPE:
			RenderSystem.getInstance().changeScene("MainMenuScreen.fxml");
			break;
		default:
			break;
		}
		key.consume();
	}

    @FXML
    public void createPlayer(ActionEvent event) {
    	System.out.println("create player");
    }

    @FXML
    public void randomizeName(ActionEvent event) {
    	System.out.println("random name");
    }

}
