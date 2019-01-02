package factories;

import java.util.ArrayList;
import java.util.HashMap;

import RNG.RNG;
import behaviours.WanderingBeh;
import components.AIC;
import components.BodyC;
import components.SkillsC;
import components.StatusEffectsC;
import components.VisionC;
import main.Entity;

public class NPCFactory extends EntityFactory{
	
	protected static HashMap<String, Entity> NPCs = new HashMap<>();
	protected static ArrayList<Entity> NPCsByID = new ArrayList<>();
	
	private NPCFactory() {}
	
	public static Entity createNPC(){
		return createNPC(RNG.getRandom(NPCs.keySet()));
	}
	
	public static Entity createNPC(String name) {
		if(NPCs.keySet().contains(name)) {
			Entity npc = NPCs.get(name).clone();
			addBasicComponents(npc);
			return npc;
		}
		else {
			return null;
		}
	}
	
	public static Entity createNPC(int ID) {
		if(ID >=NPCsByID.size() || ID < 0) {
			System.out.println("ID de NPC incorrecta, el máximo es " + (NPCsByID.size()-1) + " y se pidió " + ID);
			return null;
		}else {
			Entity npc = NPCsByID.get(ID).clone();
			addBasicComponents(npc);
			return npc;
		}
	}
	
	private static void addBasicComponents(Entity npc) {
		npc.addComponent(new VisionC());
		npc.addComponent(new StatusEffectsC());
		npc.addComponent(new BodyC());
		npc.addComponent(new SkillsC());
		AIC AI = new AIC();
		AI.changeBeh(new WanderingBeh(npc));
		npc.addComponent(AI);
	}
	
}
