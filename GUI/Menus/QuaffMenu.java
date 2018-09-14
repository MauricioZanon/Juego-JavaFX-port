package Menus;

import actions.Quaff;
import application.Main;
import gameScreen.GameScreen;
import main.Type;

public class QuaffMenu extends Menu {
	
	private static QuaffMenu instance = null;
	
	private QuaffMenu(String title) {
		super(title);
	}
	
	public void refresh() {
		ItemDesc desc = ItemDesc.getInstance();
		ItemList list = ItemList.getInstance();
		list.setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case F:
				BottomBar.getInstance().getChildren().add(SearchBar.getInstance());
				SearchBar.getInstance().requestFocus();
				break;
			case C:
				SearchBar.getInstance().clear();
				list.refresh();
				break;
			case NUMPAD2:
				list.getSelectionModel().selectNext();
				desc.refresh();
				break;
			case NUMPAD8:
				list.getSelectionModel().selectPrevious();
				desc.refresh();
				break;
			case ESCAPE:
				GameScreen.getInstance().hideMenu();
				break;
			case ENTER:
				Quaff.execute(Main.player, list.getSelectedItem());
				GameScreen.getInstance().hideMenu();
				break;
			default:
				break;
			}
			e.consume();
		});
		list.shownType = Type.POTION;
		list.refresh();
		setLeft(list);
		setCenter(desc);
		setBottom(BottomBar.getInstance());
	}
	
	public static QuaffMenu getInstance() {
		if(instance == null) {
			instance = new QuaffMenu("Quaff");
		}
		return instance;
	}

}
