package menus;

import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import application.Main;
import components.BodyC;
import components.ContainerC;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.Att;
import main.Entity;
import main.Type;
import text.StringUtils;

public abstract class BaseMenuController {
	
	@FXML public TreeView<Text> itemList;
	@FXML public TextFlow itemDesc;
	@FXML public StackPane bottomBar;
	@FXML public TextField searchField;

	protected EnumMap<Type, TreeItem<Text>> categories = createCategories();
	protected Predicate<Entity> condition = null;
	
	/**
	 * Agrega todos los items al itemList, se usa cuando se instancia el Scene y cuando se escribe algo en el searchField
	 */
	@FXML
	protected void fillItemList() {
		itemList.setRoot(new TreeItem<Text>());
		
		ContainerC inv = Main.player.get(ContainerC.class);
		Set<Entity> equipedItems = Main.player.get(BodyC.class).getEquipment();
		for(Type type : categories.keySet()) {
			TreeItem<Text> categoryBranch = categories.get(type);
			categoryBranch.getChildren().clear();
			
			List<Entity> items = inv.getOcurrences(type);
			if(items.isEmpty()) continue;
			
			for(Entity item : items) {
				if(condition.test(item) && item.name.contains(searchField.getCharacters())) {
					Text nameText = new Text(StringUtils.createItemName(item, inv));
					if(equipedItems.contains(item)) {
						nameText.setFill(Color.GRAY);
					}else {
						nameText.setFill(Color.WHITE);
					}
					categoryBranch.getChildren().add(new TreeItem<Text>(nameText));
				}
			}
			if(!categoryBranch.getChildren().isEmpty()) {
				itemList.getRoot().getChildren().add(categoryBranch);
			}
		}
	}

	@FXML
	protected void handlePressedKeyInSearchField(KeyEvent event) {
		switch(event.getCode()) {
		case ESCAPE:
			searchField.clear();
			itemList.requestFocus();
			break;
		case ENTER:
			itemList.requestFocus();
			break;
		default:
			break;
		}
	}
	
	protected void refreshItemDesc(Entity item) {
		itemDesc.getChildren().clear();
		
		if(item != null) {
			Text name = new Text(StringUtils.toTitle(item.name) + "\n\n");
			name.setFill(Color.WHITE);
			name.setFont(Font.font("courier new", FontWeight.BLACK, 20));
			name.setUnderline(true);
			
			Text desc = new Text(item.description + "\n\n");
			desc.setFont(Font.font("courier new", FontWeight.BLACK, 16));
			desc.setFill(Color.WHITE);
			
			itemDesc.getChildren().addAll(name, desc);

			for(Entry<Att, Float> att : item.getAttributes().entrySet()) {
				Text attName = new Text(att.toString().toUpperCase() + ": ");
				attName.setFont(Font.font("courier new", FontWeight.BLACK, 16));
				attName.setFill(Color.LIGHTSTEELBLUE);
				
				Text attValue = new Text(att.getValue() + "\n");
				attValue.setFont(Font.font("courier new", FontWeight.BLACK, 16));
				attValue.setFill(Color.WHITE);
				
				itemDesc.getChildren().addAll(attName, attValue);
			}
		}
	}
	
	protected Entity getSelectedItem() {
		TreeItem<Text> selectedItem = itemList.getSelectionModel().getSelectedItem();
		if(selectedItem == null) {
			return null;
		}else {
			String selectedItemName = selectedItem.getValue().getText().replaceAll("\\sx\\d", "");
			return Main.player.get(ContainerC.class).get(selectedItemName);
		}
	}
	
	protected EnumMap<Type, TreeItem<Text>> createCategories() {
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
}
