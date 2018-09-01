package Menus;

import javafx.scene.layout.BorderPane;

public class Menu extends BorderPane{
	
	protected Menu() {
		setMouseTransparent(true);
		setFocusTraversable(false);
		
		setStyle("-fx-control-inner-background: BLACK; "
				+ "-fx-font-family: Consolas; "
				+ "-fx-font-fill: BLACK;"
				+ "-fx-font-weight: BOLD;"
				+ "-fx-border-width: 0 ");
		
		bottomProperty().set(BottomBar.getInstance());
	}
}
