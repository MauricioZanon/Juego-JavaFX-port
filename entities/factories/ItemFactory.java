package factories;

import java.util.ArrayList;
import java.util.HashMap;

import RNG.RNG;
import main.Entity;

public class ItemFactory extends EntityFactory{
	
	//TODO: buscar una mejor forma de separar los tipos de items
	
	protected static HashMap<String, Entity> weapons = new HashMap<>();
	protected static ArrayList<Entity> weaponsByID = new ArrayList<>();
	protected static HashMap<String, Entity> armors = new HashMap<>();
	protected static ArrayList<Entity> armorsByID = new ArrayList<>();
	protected static HashMap<String, Entity> potions = new HashMap<>();
	protected static ArrayList<Entity> potionsByID = new ArrayList<>();
	protected static HashMap<String, Entity> tools = new HashMap<>();
	protected static ArrayList<Entity> toolsByID = new ArrayList<>();
	protected static HashMap<String, Entity> materials = new HashMap<>();
	protected static ArrayList<Entity> materialsByID = new ArrayList<>();
	
	private ItemFactory() {}
	
	public static Entity createRandomItem(){
		int rarity = RNG.nextInt(100) + 1;
		if(rarity < 80){
			return createPotion();
		}
		else if(rarity < 95){
			return createArmor();
		}
		else {
			return createWeapon();
		}
	}
	
	public static Entity createItem(String name) {
		if(weapons.containsKey(name)) {
			return weapons.get(name).clone();
		}else if(armors.containsKey(name)) {
			return armors.get(name).clone();
		}else if(potions.containsKey(name)) {
			return potions.get(name).clone();
		}else if(tools.containsKey(name)) {
			return tools.get(name).clone();
		}else {
			return null;
		}
	}
	
	public static Entity createItem(int ID) {
		try {
			if(ID >= 7000) {
				return materialsByID.get(ID-7000).clone();
			}else if(ID >= 6000) {
				return toolsByID.get(ID-6000).clone();
			}else if(ID >= 5000) {
				return potionsByID.get(ID-5000).clone();
			}else if(ID >= 4000) {
				return weaponsByID.get(ID-4000).clone();
			}else if(ID >= 3000) {
				return armorsByID.get(ID-3000).clone();
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Se dio una ID inválida " + ID);
		}
		return null;
	}
	
	public static Entity createWeapon(){
		return RNG.getRandom(weapons.values()).clone();
	}
	
	public static Entity createArmor() {
		return RNG.getRandom(armors.values()).clone();
	}

	public static Entity createPotion(){
		return RNG.getRandom(potions.values()).clone();
	}
	
//	private static String generateFakeName(String[] colorString){
//		return findText(potionsDoc, "//descriptions/desc", 1).get(0) + " " + colorString[1].trim() + " potion";
//	}
//	
//	private static Color generateColor(String[] colorString){
//		float r = Float.parseFloat(colorString[2]);
//		float g = Float.parseFloat(colorString[3]);
//		float b = Float.parseFloat(colorString[4]);
//		return new Color(r, g, b, 1f);
//	}
	
	
}
