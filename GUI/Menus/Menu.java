package Menus;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public abstract class Menu extends BorderPane{
	
	protected Menu(String title) {
		setFocusTraversable(false);
		
		setStyle("-fx-control-inner-background: BLACK; "
				+ "-fx-font-family: Consolas; "
				+ "-fx-font-weight: BOLD;"
				+ "-fx-border-width: 0 ");
		
		Label titleLabel = new Label(title);
		titleLabel.setTextFill(Color.WHITE);
		setTop(titleLabel);
	}
}
