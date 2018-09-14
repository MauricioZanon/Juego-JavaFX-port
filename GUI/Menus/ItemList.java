package Menus;

import java.util.EnumMap;
import java.util.Set;

import application.Main;
import components.ContainerComponent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;

/**
 * Muestra los items del @Type que esté guardado en la variable shownType
 */
public class ItemList extends TreeView<Label> {
	
	private static ItemList instance = null;
	private EnumMap<Type, TreeItem<Label>> categories = createCategories();
	protected Type shownType = Type.ITEM;
	
	private ItemList() {
		setShowRoot(false);
		setRoot(new TreeItem<Label>());
		setEditable(false);
		setMouseTransparent(true);
		setStyle("-fx-border-color: black brown black black;");
	}
	
	public void refresh() {
		int selectionIndex = getSelectionModel().getSelectedIndex();
		getRoot().getChildren().clear();
		
		ContainerComponent inv = Main.player.get(ContainerComponent.class);
		for(Type type : categories.keySet()) {
			if(type.is(shownType)) {
				TreeItem<Label> categoryBranch = categories.get(type);
				categoryBranch.getChildren().clear();
				
				Set<Entity> items = inv.get(type);
				if(items.isEmpty()) continue;
				
				for(Entity item : items) {
					if(!item.name.contains(SearchBar.getInstance().getCharacters())) {
						continue;
					}
					String itemName = createName(item);
					categoryBranch.getChildren().add(new TreeItem<Label>(new Label(itemName)));
				}
				getRoot().getChildren().add(categoryBranch);
			}
		}
		
		getSelectionModel().selectIndices(selectionIndex);
	}
	
	private String createName(Entity item) {
		String result = StringUtils.toTitle(item.name);
		int quantity = (int) item.get("quantity");
		if(quantity > 1) {
			result += (" x" + (quantity));
		}
		return result;
	}
	
	//FIXME la segunda linea tira nullPointer cuando la lista esta vacia
	public Entity getSelectedItem() {
		ContainerComponent cc = Main.player.get(ContainerComponent.class);
		String selectedItemName = getSelectionModel().getSelectedItem().getValue().getText().replaceAll("\\sx\\d", "");
		return cc.get(selectedItemName);
	}
	
	private EnumMap<Type, TreeItem<Label>> createCategories() {
		EnumMap<Type, TreeItem<Label>> map = new EnumMap<>(Type.class);
		
		Label wl = new Label("Weapons");
		wl.setTextFill(Color.CRIMSON);
		map.put(Type.WEAPON, new TreeItem<>(wl));
		Label al = new Label("Armors");
		al.setTextFill(Color.CORNFLOWERBLUE);
		map.put(Type.ARMOR, new TreeItem<>(al));
		Label cl = new Label("Clothes");
		cl.setTextFill(Color.CORNFLOWERBLUE);
		map.put(Type.CLOTHES, new TreeItem<>(cl));
		Label jl = new Label("Jewelry");
		jl.setTextFill(Color.HOTPINK);
		map.put(Type.JEWELRY, new TreeItem<>(jl));
		Label pl = new Label("Potions");
		pl.setTextFill(Color.DARKSEAGREEN);
		map.put(Type.POTION, new TreeItem<>(pl));
		Label sl = new Label("Scrolls");
		sl.setTextFill(Color.ROSYBROWN);
		map.put(Type.SCROLL, new TreeItem<>(sl));
		Label fl = new Label("Food");
		fl.setTextFill(Color.CORAL);
		map.put(Type.FOOD, new TreeItem<>(fl));
		Label tl = new Label("Tools");
		map.put(Type.TOOL, new TreeItem<>(tl));
		Label ml = new Label("Materials");
		map.put(Type.MATERIAL, new TreeItem<>(ml));
		Label mul = new Label("Munition");
		map.put(Type.MUNITION, new TreeItem<>(mul));
		Label wal = new Label("Wands");
		map.put(Type.WAND, new TreeItem<>(wal));
		Label bl = new Label("Books");
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
