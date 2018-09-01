package factories;

import java.util.HashMap;

import RNG.RNG;
import main.Entity;
import main.Type;

public abstract class NPCFactory extends EntityFactory{
	
	private static HashMap<Integer, String> NPCsId;
	private static HashMap<String, String> NPCsName;
	
	public static void initialize() {
		NPCsId = loadEntities("../RogueWorld/assets/Data/Entities/Enemies.xml");
		NPCsName = makeMapWithNames(NPCsId);
	}
	
	public static Entity createNPC(){
		return createNPC(RNG.getRandom(NPCsId.keySet()));
	}
	
	public static Entity createNPC(String name){
		if(NPCsName.keySet().contains(name)) {
			Entity npc = create(NPCsName.get(name));
			npc.setType(Type.ACTOR);
//			npc.addComponent(new TimedComponent());
			return npc;
		}
		else {
			return null;
		}
	}
	
	public static Entity createNPC(int id) {
		if(NPCsId.keySet().contains(id)) {
			Entity npc = create(NPCsId.get(id));
			npc.setType(Type.ACTOR);
//			npc.addComponent(new TimedComponent());
			return npc;
		}
		else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		initialize();
		NPCsId.keySet().forEach(s -> create(NPCsId.get(s)));
		NPCsName.keySet().forEach(s -> create(NPCsName.get(s)));
	}
	
}
