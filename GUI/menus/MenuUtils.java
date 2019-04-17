package menus;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Map;
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
	
	protected static ObservableList<TreeItem<Text>> shownEntities = FXCollections.<TreeItem<Text>>observableArrayList();
	protected static ObservableList<Node> itemDescText = FXCollections.<Node>observableArrayList();
	
	protected static Predicate<Entity> filter = null;
	protected static Map<String, ArrayDeque<Entity>> entities = null;
	
	
	private MenuUtils() {}
	
	/**
	 * Agrega todos los items al itemList, se usa cuando se instancia el Scene y cuando se escribe algo en el searchField
	 */
	protected static void fillItemList() {
		shownEntities.clear();
		EnumMap<Type, TreeItem<Text>> categories = createCategories();
		
		for(Entry<String, ArrayDeque<Entity>> e : entities.entrySet()) {
			if(filter.test(e.getValue().getFirst())) {
				Type type = e.getValue().getFirst().type;
				TreeItem<Text> branch = categories.get(type);
				Text nameText = new Text(StringUtils.createItemName(e.getKey(), e.getValue().size()));
				nameText.setFill(Color.WHITE);
				branch.getChildren().add(new TreeItem<>(nameText));
			}
		}
		
		for(TreeItem<Text> branch : categories.values()) {
			if(!branch.getChildren().isEmpty()) {
				shownEntities.add(branch);
			}
		}
		
	}
	
	private static EnumMap<Type, TreeItem<Text>> createCategories() {
		EnumMap<Type, TreeItem<Text>> map = new EnumMap<>(Type.class);
		
		for(Type t : Type.values()) {
			if(t.isLeaf()) {
				Text typeText = new Text(t.toString().replaceAll("_", " "));
				typeText.setFill(Color.CHOCOLATE);
				map.put(t, new TreeItem<>(typeText));
			}
		}
		
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
		shownEntities = FXCollections.<TreeItem<Text>>observableArrayList();
	}

}
