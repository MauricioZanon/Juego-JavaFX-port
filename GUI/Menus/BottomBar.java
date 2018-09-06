package Menus;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Guarda los nodos auxiliares para los menus
 */
public class BottomBar extends StackPane{
	
	private static BottomBar instance = null;
	
	private BottomBar() {
		Label findLabel = new Label("[f]ind | [c]lear search");
		findLabel.setTextFill(Color.WHITE);
		getChildren().add(findLabel);

		setAlignment(Pos.CENTER_LEFT);
		setStyle("-fx-border-color: black black black black;");
	}
	
	public static BottomBar getInstance() {
		if(instance == null) {
			instance = new BottomBar();
		}
		return instance;
	}

}
