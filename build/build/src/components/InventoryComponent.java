package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Component;
import main.Entity;

public class InventoryComponent extends Component{
	
	public HashMap<String, Entity> inv = new HashMap<>();
	
	public void add(Entity i) {
		String itemName = i.get(DescriptionComponent.class).name;
		if(!inv.keySet().contains(itemName)) {
			inv.put(itemName, i);
		}else {
			Entity item = inv.get(itemName);
			item.get(AttributeComponent.class).change("quantity", i.get(AttributeComponent.class).get("quantity"));
		}
	}
	
	public void add(List<Entity> items) {
		items.forEach(i -> add(i));
	}
	
	public Entity get(String itemName) {
		if(inv.keySet().contains(itemName)) {
			return inv.get(itemName);
		}
		return null;
	}
	
//	public List<Entity> getList(ItemType type){
//		ArrayList<Entity> itemList = new ArrayList<>();
//		for(Entity item : inv.values()) {
//			if(itemTypeMap.get(item).is(type)) {
//				itemList.add(item);
//			}
//		}
//		return itemList;
//	}
	
	public List<Entity> getAll(){
		return new ArrayList<Entity>(inv.values());
	}

	public Entity remove(String itemName) {
		if(inv.keySet().contains(itemName)) {
			inv.get(itemName).get(AttributeComponent.class).change("quantity", -1);
			Entity item = inv.get(itemName);
			if(inv.get(itemName).get(AttributeComponent.class).get("quantity") <= 0) {
				inv.remove(itemName);
			}
			return item;
		}
		return null;
	}
	
	public Entity removeAll(String itemName){
		if(inv.keySet().contains(itemName)) {
			return inv.remove(itemName);
		}
		return null;
	}
	
	public Entity remove(Entity i) {
		return remove(i.get(DescriptionComponent.class).name);
	}
	
	public Entity removeAll(Entity item) {
		return removeAll(item.get(DescriptionComponent.class).name);
	}

	@Override
	public InventoryComponent clone() {
		return null;
	}

}
