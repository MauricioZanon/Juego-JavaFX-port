package Menus;

import gameScreen.GameScreen;
import main.Type;

public class InventoryMenu extends Menu{
	
	private static InventoryMenu instance = null;
	
	private InventoryMenu(String title) {
		super(title);
	}
	
	public void refresh() {
		ItemList list = ItemList.getInstance();
		list.shownType = Type.ITEM;
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
				ItemDesc.getInstance().refresh();
				ActionList.getInstance().refresh();
				break;
			case NUMPAD8:
				list.getSelectionModel().selectPrevious();
				ItemDesc.getInstance().refresh();
				ActionList.getInstance().refresh();
				break;
			case ESCAPE:
				GameScreen.getInstance().hideMenu();
				break;
			case NUMPAD6:
			case ENTER:
				if(list.getSelectedItem() != null) {
					ActionList.getInstance().getSelectionModel().select(0);
					ActionList.getInstance().requestFocus();
				}
				break;
			default:
				break;
			}
			e.consume();
		});
		list.refresh();
		setLeft(list);
		setCenter(ActionList.getInstance());
		setRight(ItemDesc.getInstance());
		setBottom(BottomBar.getInstance());
	}
	
	public static InventoryMenu getInstance() {
		if(instance == null) {
			instance = new InventoryMenu("Inventory");
		}
		return instance;
	}
	
	
}
