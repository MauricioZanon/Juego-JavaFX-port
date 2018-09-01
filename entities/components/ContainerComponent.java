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
			item.changeAttribute("quantity", newItem.get("quantity"));
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

	public Entity remove(String itemName) {
		removeDepletedItems();
		if(items.keySet().contains(itemName)) {
			items.get(itemName).changeAttribute("quantity", -1);
			Entity item = items.get(itemName);
			if(items.get(itemName).get("quantity") <= 0) {
				items.remove(itemName);
			}
			return ItemFactory.createItem(item.name);
		}
		return null;
	}
	
	public Entity removeAll(String itemName){
		removeDepletedItems();
		if(items.keySet().contains(itemName)) {
			return items.remove(itemName);
		}
		return null;
	}
	
	private void removeDepletedItems() {
		items.values().removeIf(i -> i.get("quantity") < 1);
	}

	@Override
	public ContainerComponent clone() {
		return null;
	}

}
