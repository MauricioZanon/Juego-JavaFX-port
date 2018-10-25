package components;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import main.Component;
import main.Entity;

public class BodyComponent extends Component{
	
	private EnumMap<BodyPart, Entity> body = new EnumMap<>(BodyPart.class);
	private Entity itemInRightHand = null;
	
	/**
	 * @return el item equipado previamente en el slot
	 */
	public Entity equip(Entity item) {
		if(item == null) return null;
		Entity removedItem = null;
		switch(item.TYPE) {
		case SWORD:
		case DAGGER:
		case MACE:
		case BOW:
			if(body.containsKey(BodyPart.R_HAND)) {
				removedItem = itemInRightHand;
				itemInRightHand = item;
			}
			break;
		case HELMET:
			if(body.containsKey(BodyPart.HEAD)) {
				removedItem = body.put(BodyPart.HEAD, item);
			}
			break;
		case BREASTPLATE:
			if(body.containsKey(BodyPart.TORSO)) {
				removedItem = body.put(BodyPart.TORSO, item);
			}
			break;
		case GREAVES:
			if(body.containsKey(BodyPart.L_LEG) && body.containsKey(BodyPart.R_LEG)) {
				removedItem = body.put(BodyPart.L_LEG, item);
				body.put(BodyPart.R_LEG, item);
			}
			break;
		case GLOVES:
			if(body.containsKey(BodyPart.L_HAND)) {
				removedItem = body.put(BodyPart.L_HAND, item);
			}
			if(body.containsKey(BodyPart.R_HAND)) {
				removedItem = body.put(BodyPart.R_HAND, item);
			}
			break;
		case BOOTS:
			if(body.containsKey(BodyPart.L_FOOT)) {
				removedItem = body.put(BodyPart.L_FOOT, item);
			}
			if(body.containsKey(BodyPart.R_FOOT)) {
				removedItem = body.put(BodyPart.R_FOOT, item);
			}
			break;
		default:
			break;
		}
		return removedItem;
	}
	
	/**
	 * @return el item equipado previamente en el slot
	 */
	public Entity remove(Entity item) {
		Entity removedItem = null;
		switch(item.TYPE) {
		case SWORD:
		case DAGGER:
		case MACE:
		case BOW:
			if(body.containsKey(BodyPart.R_HAND)) {
				removedItem = itemInRightHand;
				itemInRightHand = null;
			}
			break;
		case HELMET:
			if(body.containsKey(BodyPart.HEAD)) {
				removedItem = body.put(BodyPart.HEAD, null);
			}
			break;
		case BREASTPLATE:
			if(body.containsKey(BodyPart.TORSO)) {
				removedItem = body.put(BodyPart.TORSO, null);
			}
			break;
		case GREAVES:
			if(body.containsKey(BodyPart.L_LEG) && body.containsKey(BodyPart.R_LEG)) {
				removedItem = body.put(BodyPart.L_LEG, null);
			}
			break;
		case GLOVES:
			if(body.containsKey(BodyPart.L_HAND)) {
				removedItem = body.put(BodyPart.L_HAND, null);
			}
			if(body.containsKey(BodyPart.R_HAND)) {
				removedItem = body.put(BodyPart.R_HAND, null);
			}
			break;
		case BOOTS:
			if(body.containsKey(BodyPart.L_FOOT)) {
				removedItem = body.put(BodyPart.L_FOOT, null);
			}
			if(body.containsKey(BodyPart.R_FOOT)) {
				removedItem = body.put(BodyPart.R_FOOT, null);
			}
			break;
		default:
			break;
		}
		return removedItem;
	}
	
	public Set<Entity> getEquipment(){
		Set<Entity> equipment = new HashSet<>();
		body.values().forEach(e ->{
			if(e != null) equipment.add(e);
		});
		if(itemInRightHand != null) {
			equipment.add(itemInRightHand);
		}
		return equipment;
	}
	
	public void add(BodyPart part) {
		body.put(part, null);
	}
	
	public void remove(BodyPart part) {
		body.remove(part);
	}
	
	public Entity getWeapon() {
		return itemInRightHand;
	}
	
	@Override
	public BodyComponent clone() {
		BodyComponent comp = new BodyComponent();
		for(Entry<BodyPart, Entity> entry : body.entrySet()) {
			comp.add(entry.getKey());
			comp.equip(entry.getValue().clone());
		}
		if(itemInRightHand != null) {
			comp.equip(itemInRightHand.clone());
		}
		return comp;
	}

	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder("BODY ");
		
		for(BodyPart part : body.keySet()) {
			sb.append(part.toString() + "-");
		}
		sb.append(" ");
		
		for(Entity equipment : body.values()) {
			sb.append(equipment.ID + "-");
		}
		if(itemInRightHand != null) {
			sb.append(itemInRightHand.ID);
		}
		
		return sb.toString();
	}
	
	public enum BodyPart{
		HEAD,
		TORSO,
		R_ARM,
		L_ARM,
		R_LEG,
		L_LEG,
		R_HAND,
		L_HAND,
		R_FOOT,
		L_FOOT,
	}
	
}
