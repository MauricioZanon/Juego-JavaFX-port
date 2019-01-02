package menus;

import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

import components.ContainerC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Att;
import main.Entity;
import main.Type;
import text.StringUtils;

public class MenuUtils {
	
	protected static ObservableList<TreeItem<Text>> itemListItems = FXCollections.<TreeItem<Text>>observableArrayList();
	protected static ObservableList<Node> itemDescText = FXCollections.<Node>observableArrayList();
	
	private MenuUtils() {}
	
	/**
	 * Agrega todos los items al itemList, se usa cuando se instancia el Scene y cuando se escribe algo en el searchField
	 */
	protected static void fillItemList(Predicate<Entity> filter, ContainerC inv) {
		itemListItems.clear();
		EnumMap<Type, TreeItem<Text>> categories = createCategories();
		
		for(Type type : categories.keySet()) {
			TreeItem<Text> categoryBranch = categories.get(type);
			categoryBranch.getChildren().clear();
			
			List<Entity> items = inv.getOcurrences(type);
			if(items.isEmpty()) continue;
			
			for(Entity item : items) {
				if(filter.test(item)) {
					Text nameText = new Text(StringUtils.createItemName(item, inv));
					nameText.setFill(Color.WHITE);
					categoryBranch.getChildren().add(new TreeItem<Text>(nameText));
				}
			}
			if(!categoryBranch.getChildren().isEmpty()) {
				itemListItems.add(categoryBranch);
			}
		}
	}
	
	private static EnumMap<Type, TreeItem<Text>> createCategories() {
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
		tl.setFill(Color.BROWN);
		map.put(Type.TOOL, new TreeItem<>(tl));
		
		Text ml = new Text("Materials");
		ml.setFill(Color.BEIGE);
		map.put(Type.MATERIAL, new TreeItem<>(ml));
		
		Text mul = new Text("Munition");
		mul.setFill(Color.AQUA);
		map.put(Type.MUNITION, new TreeItem<>(mul));
		
		Text wal = new Text("Wands");
		map.put(Type.WAND, new TreeItem<>(wal));
		
		Text bl = new Text("Books");
		map.put(Type.BOOK, new TreeItem<>(bl));
		
		map.values().forEach(ti -> ti.expandedProperty().set(true));
		
		return map;
	}
	
	protected static void refreshItemDesc(Entity item) {
		itemDescText.clear();
		
		if(item != null) {
			Text name = new Text(StringUtils.toTitle(item.name) + "\n\n");
			name.setFill(Color.WHITE);
			name.setFont(Font.font("courier new", FontWeight.BLACK, 20));
			name.setUnderline(true);
			
			Text desc = new Text(item.description + "\n\n");
			desc.setFont(Font.font("courier new", FontWeight.BLACK, 16));
			desc.setFill(Color.WHITE);
			
			itemDescText.addAll(name, desc);

			for(Entry<Att, Float> att : item.getAttributes().entrySet()) {
				Text attName = new Text(att.getKey().toString().toUpperCase() + ": ");
				attName.setFont(Font.font("courier new", FontWeight.BLACK, 16));
				attName.setFill(Color.LIGHTSTEELBLUE);
				
				Text attValue = new Text(att.getValue() + "\n");
				attValue.setFont(Font.font("courier new", FontWeight.BLACK, 16));
				attValue.setFill(Color.WHITE);
				
				itemDescText.addAll(attName, attValue);
			}
		}
	}
	
	protected static Entity getSelectedItem(TreeView<Text> itemList, ContainerC inv) {
		TreeItem<Text> selectedItem = itemList.getSelectionModel().getSelectedItem();
		if(selectedItem == null) {
			return null;
		}else {
			String selectedItemName = selectedItem.getValue().getText().replaceAll("\\sx\\d", "");
			return inv.get(selectedItemName);
		}
	}
	
	protected static void resetObservables() {
		itemDescText = FXCollections.<Node>observableArrayList();
		itemListItems = FXCollections.<TreeItem<Text>>observableArrayList();
	}

}
