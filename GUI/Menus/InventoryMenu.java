package menus;
//package Menus;
//
//import application.Main;
//import components.BodyComponent;
//import main.Entity;
//import main.Flags;
//import main.Type;
//
//public class InventoryMenu extends Menu{
//	
//	private static InventoryMenu instance = null;
//	
//	private InventoryMenu(String title) {
//		super(title);
//	}
//	
//	public void refresh() {
//		ItemList list = ItemList.getInstance();
//		ActionList actionList = ActionList.getInstance();
//		list.shownType = Type.ITEM;
//		
//		list.setOnKeyPressed(e -> {
//			Entity item = list.getSelectedItem();
//			switch(e.getCode()) {
//			case F:
//				BottomBar.getInstance().getChildren().add(SearchBar.getInstance());
//				SearchBar.getInstance().requestFocus();
//				break;
//			case C:
//				SearchBar.getInstance().clear();
//				break;
//			case D:
//				actionList.executeAction("Drop");
//				break;
//			case E:
//				if(item != null && item.is(Flags.EDIBLE)) {
//					actionList.executeAction("Eat");
//				}
//				break;
//			case Q:
//				if(item != null && item.is(Flags.DRINKABLE)) {
//					actionList.executeAction("Quaff)");
//				}
//				break;
//			case T:
//				actionList.executeAction("Throw");
//				break;
//			case W:
//				if(e.isShiftDown()) {
//					if(item != null && item.is(Flags.WEARABLE) && !Main.player.get(BodyComponent.class).getEquipment().contains(item)) {
//						actionList.executeAction("Wear");
//					}
//				}else {
//					if(item != null && item.TYPE.is(Type.WEAPON) && !Main.player.get(BodyComponent.class).getEquipment().contains(item)) {
//						actionList.executeAction("Wield");
//					}
//				}
//				break;
//			case DOWN:
//			case NUMPAD2:
//				list.getSelectionModel().selectNext();
//				ItemDesc.getInstance().refresh();
//				ActionList.getInstance().refresh();
//				break;
//			case UP:
//			case NUMPAD8:
//				list.getSelectionModel().selectPrevious();
//				ItemDesc.getInstance().refresh();
//				ActionList.getInstance().refresh();
//				break;
//			case ESCAPE:
////				GameScreen.getInstance().hideMenu();
//				break;
//			case RIGHT:
//			case NUMPAD6:
//			case ENTER:
//				if(list.getSelectedItem() != null) {
//					actionList.getSelectionModel().select(0);
//					actionList.requestFocus();
//				}
//				break;
//			default:
//				break;
//			}
//			e.consume();
//		});
//		
//		list.refresh();
//		setLeft(list);
//		actionList.refresh();
//		setCenter(actionList);
//		ItemDesc.getInstance().refresh();
//		setRight(ItemDesc.getInstance());
//		setBottom(BottomBar.getInstance());
//	}
//	
//	public static InventoryMenu getInstance() {
//		if(instance == null) {
//			instance = new InventoryMenu("Inventory");
//		}
//		return instance;
//	}
//	
//	
//}
