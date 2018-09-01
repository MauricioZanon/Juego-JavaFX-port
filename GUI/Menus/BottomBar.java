package Menus;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Guarda los nodos auxiliares para los menus
 */
public class BottomBar extends StackPane{
	
	private static BottomBar instance = null;
	
	private BottomBar() {
		setStyle("-fx-control-inner-background: BLACK; "
				+ "-fx-font-family: Consolas; "
				+ "-fx-font-fill: WHITE;"
				+ "-fx-font-weight: BOLD;"
				+ "-fx-border-width: 0; "
				+ "-fx-border-color: black black black black;");
		
		Label findLabel = new Label("[f]ind | [c]lear search");
		findLabel.setTextFill(Color.WHITE);
		
		getChildren().add(findLabel);
	}
	
	public static BottomBar getInstance() {
		if(instance == null) {
			instance = new BottomBar();
		}
		return instance;
	}

}
