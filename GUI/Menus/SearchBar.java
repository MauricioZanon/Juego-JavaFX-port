package Menus;

import javafx.scene.control.TextField;

public class SearchBar extends TextField{
	
	public static SearchBar instance = null;
	
	private SearchBar() {
		setStyle("-fx-control-inner-background: BLACK; "
				+ "-fx-font-family: Consolas; "
				+ "-fx-font-fill: WHITE;"
				+ "-fx-font-weight: BOLD;"
				+ "-fx-border-width: 0; "
				+ "-fx-border-color: black black black black;");
		
		setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case BACK_SPACE:
				deletePreviousChar();
				break;
			case ENTER:
				ItemList.getInstance().requestFocus();
				BottomBar.getInstance().getChildren().remove(this);
				break;
			case ESCAPE:
				this.clear();
				BottomBar.getInstance().getChildren().remove(this);
				ItemList.getInstance().requestFocus();
				break;
			default:
				break;
			}
			e.consume();
			ItemList.getInstance().refresh();
		});
	}
	
	public static SearchBar getInstance() {
		if(instance == null) {
			instance = new SearchBar();
		}
		return instance;
	}
}
