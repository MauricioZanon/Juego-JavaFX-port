package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import factories.ItemFactory;
import main.Component;
import main.Entity;
import main.Type;

/**
 * Guarda items, sirve para crear entidades que contengan items, tanto features como actores
 * @author Mauro
 *
 */
public class ContainerComponent extends Component{
	
	public Map<String, Entity> items = new HashMap<>(); 
	
	public void add(Entity newItem) {
		String itemName = newItem.name;
		if(!items.keySet().contains(itemName)) {
			items.put(itemName, newItem);
		}else {
			Entity item = items.get(itemName);
			item.changeAttribute("quantity", newItem.getBase("quantity"));
		}
	}
	
	public void add(List<Entity> items) {
		items.forEach(i -> add(i));
	}
	
	public Set<Entity> get(Type type){
		removeDepletedItems();
		Set<Entity> set = new HashSet<>();
		for(Entity item : items.values()) {
			if(item.TYPE.is(type)) {
				set.add(item);
			}
		}
		return set;
	}
	
	public Entity get(String itemName) {
		removeDepletedItems();
		if(items.keySet().contains(itemName.toLowerCase())) {
			return items.get(itemName.toLowerCase());
		}
		return null;
	}
	
	public List<Entity> getAll(){
		removeDepletedItems();
		return new ArrayList<Entity>(items.values());
	}
	
	public Entity remove(String itemName, int quantity) {
		removeDepletedItems();
		if(items.keySet().contains(itemName)) {
			Entity item = items.get(itemName);
			if(item.get("quantity") >= quantity) {
				item.changeAttribute("quantity", -quantity);
				Entity removedItems = ItemFactory.createItem(itemName);
				removedItems.setAttribute("quantity", quantity);
				return removedItems;
			}else {
				items.remove(itemName);
				return item;
			}
		}
		return null;
	}

	/**
	 * Quita todos los items con este nombre del inventario
	 * @return el item removido
	 */
	public Entity remove(String itemName) {
		removeDepletedItems();
		if(items.keySet().contains(itemName)) {
			return items.remove(itemName);
		}
		return null;
	}
	
	private void removeDepletedItems() {
		items.values().removeIf(i -> i.getBase("quantity") < 1);
	}

	@Override
	public ContainerComponent clone() {
		return null;
	}

}
