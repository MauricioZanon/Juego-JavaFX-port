package components;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Entity;
import main.Type;

/**
 * Guarda items, sirve para crear entidades que contengan items, tanto features como actores
 */
public class ContainerC extends Component{
	
	/** <Nombre del item, cantidad> */
	public Map<String, ArrayDeque<Entity>> items = new HashMap<>();
	
	public ContainerC() {
		isBase = false;
	}

	public void add(Entity newItem) {
		String itemName = newItem.name;
		if(!items.keySet().contains(itemName)) {
			items.put(itemName, new ArrayDeque<>());
		}
		items.get(itemName).add(newItem);
	}
	
	public void addAll(ArrayDeque<Entity> items) {
		items.forEach(i -> add(i));
	}
	
	/** Devuelve todos los items del Tipo type */
	public ArrayDeque<Entity> get(Type type){
		ArrayDeque<Entity> returnedList = new ArrayDeque<>();
		for(ArrayDeque<Entity> itemList : items.values()) {
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
		List<Entity> returnedList = new ArrayList<>();
		for(ArrayDeque<Entity> itemList : items.values()) {
			returnedList.addAll(itemList);
		}
		return returnedList;
	}
	
	/** Remueve y devuelve la cantidad indicada del item pedido */
	public ArrayDeque<Entity> remove(String itemName, int quantity) {
		ArrayDeque<Entity> returnedList = new ArrayDeque<>();
		if(items.keySet().contains(itemName)) {
			ArrayDeque<Entity> itemList = items.get(itemName);
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
	public ArrayDeque<Entity> remove(String itemName) {
		if(items.keySet().contains(itemName)) {
			ArrayDeque<Entity> result = items.remove(itemName);
			return result;
		}
		else {
			return null;
		}
	}
	
	public ArrayDeque<Entity> removeAll(){
		ArrayDeque<Entity> result = new ArrayDeque<>();
		for(ArrayDeque<Entity> itemList : items.values()) {
			result.addAll(itemList);
		}
		items.clear();
		return result;
	}
	
	/** Devuelve uno de cada item en el container */
	public List<Entity> getOcurrences(Type type){
		List<Entity> result = new ArrayList<>();
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
	public ContainerC clone() {
		ContainerC comp = new ContainerC();
		for(Entity item : getAll()) {
			comp.add(item.clone());
		}
		return comp;
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
