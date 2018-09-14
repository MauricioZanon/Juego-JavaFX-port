package components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import main.Component;
import main.Entity;

public class BodyComponent extends Component{
	
	private HashMap<BodyPart, Entity> body = new HashMap<>();
	private Entity itemInRightHand = null;
	
	/**
	 * @return el item equipado previamente en el slot
	 */
	public Entity equip(Entity item) {
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
		case WEAPON:
			if(body.containsKey(BodyPart.R_HAND)) {
				removedItem = body.put(BodyPart.R_HAND, null);
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
	
	@Override
	public BodyComponent clone() {
		return null;
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
