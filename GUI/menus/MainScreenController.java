package menus;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import system.RenderSystem;

public class MainScreenController {
	
	@FXML
	public void startNewGame() {
		Main.startNewGame();
	}
	
	@FXML
	void openNewCharacterMenu() {
		RenderSystem.getInstance().changeScene("NewPlayerMenu.fxml");
	}
	
	@FXML
	public void loadGame() {
		Main.loadGame();
	}
	
	@FXML
	public void exitGame() {
		System.exit(0);
	}
    
	@FXML
	public void handlePressedKey(KeyEvent key) {
		switch(key.getCode()) {
		case C:
			openNewCharacterMenu();
			break;
		case L:
			Main.loadGame();
			break;
		case N:
			Main.startNewGame();
        	break;
		case ESCAPE:
			System.exit(0);
			break;
		default:
			break;
		}
		key.consume();
	}

}
