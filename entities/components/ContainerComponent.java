package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.Component;
import main.Entity;
import main.Type;

/**
 * Guarda items, sirve para crear entidades que contengan items, tanto features como actores
 */
public class ContainerComponent extends Component{
	
	/** <Nombre del item, cantidad> */
	public Map<String, LinkedList<Entity>> items = new HashMap<>(); 
	
	public void add(Entity newItem) {
		String itemName = newItem.name;
		if(!items.keySet().contains(itemName)) {
			items.put(itemName, new LinkedList<>());
		}
		items.get(itemName).add(newItem);
	}
	
	public void addAll(List<Entity> items) {
		items.forEach(i -> add(i));
	}
	
	/** Devuelve todos los items del Tipo type */
	public List<Entity> get(Type type){
		List<Entity> returnedList = new ArrayList<>();
		for(LinkedList<Entity> itemList : items.values()) {
			if(itemList.getFirst().TYPE.is(type)) {
				returnedList.addAll(itemList);
			}
		}
		return returnedList;
	}
	
	/** Devuelve un item con el nombre itemName */
	public Entity get(String itemName) {
		if(items.keySet().contains(itemName.toLowerCase())) {
			return items.get(itemName.toLowerCase()).getFirst();
		}
		else {
			return null;
		}
	}
	
	/** Devuelve una lista con todos los items */
	public List<Entity> getAll(){
		List<Entity> returnedList = new LinkedList<>();
		for(LinkedList<Entity> itemList : items.values()) {
			returnedList.addAll(itemList);
		}
		return returnedList;
	}
	
	/** Remueve y devuelve la cantidad indicada del item pedido */
	public LinkedList<Entity> remove(String itemName, int quantity) {
		LinkedList<Entity> returnedList = new LinkedList<>();
		if(items.keySet().contains(itemName)) {
			LinkedList<Entity> itemList = items.get(itemName);
			while(!itemList.isEmpty() && returnedList.size() < quantity) {
				returnedList.add(items.get(itemName).removeFirst());
			}
		}
		removeDepletedItems();
		return returnedList;
	}

	/**
	 * Quita todos los items con este nombre del inventario
	 * @return el item removido
	 */
	public LinkedList<Entity> remove(String itemName) {
		if(items.keySet().contains(itemName)) {
			LinkedList<Entity> result = items.remove(itemName);
			return result;
		}
		else {
			return null;
		}
	}
	
	public List<Entity> getOcurrences(Type type){
		List<Entity> result = new LinkedList<>();
		items.values().forEach(list -> result.add(list.getFirst()));
		result.removeIf(i -> !i.TYPE.is(type));
		
		return result;
	}
	
	private void removeDepletedItems() {
		items.values().removeIf(list -> list.isEmpty());
	}
	
	
	public int getQuantity(String itemName) {
		return items.containsKey(itemName) ? items.get(itemName).size() : 0;
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public boolean contains(String itemName) {
		return items.keySet().contains(itemName);
	}
	
	public boolean contains(String itemName, int quantity) {
		return items.keySet().contains(itemName) && getQuantity(itemName) >= quantity;
	}

	@Override
	public ContainerComponent clone() {
		return null;
	}

	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder("CON ");
		for(Entity item : getAll()) {
			sb.append(item.ID + "-");
		}
		return sb.toString();
	}

}
