package Menus;

import actions.Throw;
import gameScreen.GameScreen;
import main.Type;

public class ThrowMenu extends Menu{

	private static ThrowMenu instance = null;
	
	private ThrowMenu(String title) {
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
				if(list.getSelectedItem() != null) {
					Throw.setListener();
					GameScreen.getInstance().hideMenu();
				}
				break;
			default:
				break;
			}
			e.consume();
		});
		list.shownType = Type.ITEM;
		list.refresh();
		setLeft(list);
		setCenter(desc);
		setBottom(BottomBar.getInstance());
	}
	
	public static ThrowMenu getInstance() {
		if(instance == null) {
			instance = new ThrowMenu("Throw");
		}
		return instance;
	}
	
}
