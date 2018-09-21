package Menus;

import java.util.EnumMap;
import java.util.Set;

import application.Main;
import components.BodyComponent;
import components.ContainerComponent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.Entity;
import main.Type;

/**
 * Muestra los items del @Type que esté guardado en la variable shownType
 */
public class ItemList extends TreeView<Text> {
	
	private static ItemList instance = null;
	private EnumMap<Type, TreeItem<Text>> categories = createCategories();
	protected Type shownType = Type.ITEM;
	
	private ItemList() {
		setShowRoot(false);
		setRoot(new TreeItem<Text>());
		setEditable(false);
		setMouseTransparent(true);
		setStyle("-fx-border-color: black brown black black;");
	}
	
	public void refresh() {
		getRoot().getChildren().clear();
		getSelectionModel().clearSelection();
		
		ContainerComponent inv = Main.player.get(ContainerComponent.class);
		Set<Entity> equipedItems = Main.player.get(BodyComponent.class).getEquipment();
		for(Type type : categories.keySet()) {
			if(type.is(shownType)) {
				TreeItem<Text> categoryBranch = categories.get(type);
				categoryBranch.getChildren().clear();
				
				Set<Entity> items = inv.get(type);
				if(items.isEmpty()) continue;
				
				for(Entity item : items) {
					if(item.name.contains(SearchBar.getInstance().getCharacters())) {
						Text nameText = new Text(createName(item));
						if(equipedItems.contains(item)) {
							nameText.setFill(Color.GRAY);
						}else {
							nameText.setFill(Color.WHITE);
						}
						categoryBranch.getChildren().add(new TreeItem<Text>(nameText));
					}
				}
				getRoot().getChildren().add(categoryBranch);
			}
		}
	}
	
	private String createName(Entity item) {
		String result = StringUtils.toTitle(item.name);
		int quantity = (int) item.getBase("quantity");
		if(quantity > 1) {
			result += (" x" + (quantity));
		}
		return result;
	}
	
	public Entity getSelectedItem() {
		TreeItem<Text> selectedItem = getSelectionModel().getSelectedItem();
		if(selectedItem == null) {
			return null;
		}else {
			String selectedItemName = selectedItem.getValue().getText().replaceAll("\\sx\\d", "");
			return Main.player.get(ContainerComponent.class).get(selectedItemName);
		}
	}
	
	private EnumMap<Type, TreeItem<Text>> createCategories() {
		EnumMap<Type, TreeItem<Text>> map = new EnumMap<>(Type.class);
		
		Text wl = new Text("Weapons");
		wl.setFill(Color.CRIMSON);
		map.put(Type.WEAPON, new TreeItem<>(wl));
		
		Text al = new Text("Armors");
		al.setFill(Color.CORNFLOWERBLUE);
		map.put(Type.ARMOR, new TreeItem<>(al));
		
		Text cl = new Text("Clothes");
		cl.setFill(Color.CORNFLOWERBLUE);
		map.put(Type.CLOTHES, new TreeItem<>(cl));
		
		Text jl = new Text("Jewelry");
		jl.setFill(Color.HOTPINK);
		map.put(Type.JEWELRY, new TreeItem<>(jl));
		
		Text pl = new Text("Potions");
		pl.setFill(Color.DARKSEAGREEN);
		map.put(Type.POTION, new TreeItem<>(pl));
		
		Text sl = new Text("Scrolls");
		sl.setFill(Color.ROSYBROWN);
		map.put(Type.SCROLL, new TreeItem<>(sl));
		
		Text fl = new Text("Food");
		fl.setFill(Color.CORAL);
		map.put(Type.FOOD, new TreeItem<>(fl));
		
		Text tl = new Text("Tools");
		map.put(Type.TOOL, new TreeItem<>(tl));
		
		Text ml = new Text("Materials");
		map.put(Type.MATERIAL, new TreeItem<>(ml));
		
		Text mul = new Text("Munition");
		map.put(Type.MUNITION, new TreeItem<>(mul));
		
		Text wal = new Text("Wands");
		map.put(Type.WAND, new TreeItem<>(wal));
		
		Text bl = new Text("Books");
		map.put(Type.BOOK, new TreeItem<>(bl));
		
		map.values().forEach(ti -> ti.expandedProperty().set(true));
		
		return map;
	}
	
	public static ItemList getInstance() {
		if(instance == null) {
			instance = new ItemList();
		}
		return instance;
	}
	
}
