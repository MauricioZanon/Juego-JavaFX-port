package menus;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class MainScreenController {
	
	@FXML
	public void startNewGame() {
		Main.startNewGame();
	}
	
	@FXML
	public void loadGame() {
		System.out.println("load game");
	}
	
	@FXML
	public void exitGame() {
		System.exit(0);
	}
	
	@FXML
	public void handlePressedKey(KeyEvent key) {
		switch(key.getCode()) {
		case N:
			startNewGame();
        	break;
		case ESCAPE:
			System.exit(0);
		default:
			break;
		}
		key.consume();
	}

}
