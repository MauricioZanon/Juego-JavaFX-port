package Menus;

import actions.Wield;
import application.Main;
import gameScreen.GameScreen;
import main.Type;

public class WieldMenu extends Menu {
	
	private static WieldMenu instance = null;
	
	private WieldMenu(String title) {
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
				Wield.execute(Main.player, list.getSelectedItem());
				GameScreen.getInstance().hideMenu();
				break;
			default:
				break;
			}
			e.consume();
		});
		list.shownType = Type.WEAPON;
		list.refresh();
		setLeft(list);
		setCenter(desc);
		setBottom(BottomBar.getInstance());
	}
	
	public static WieldMenu getInstance() {
		if(instance == null) {
			instance = new WieldMenu("Wield");
		}
		return instance;
	}

}
