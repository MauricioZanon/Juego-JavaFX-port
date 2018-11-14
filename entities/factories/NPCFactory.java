package factories;

import java.util.ArrayList;
import java.util.HashMap;

import RNG.RNG;
import behaviours.WanderingBeh;
import components.AIComponent;
import components.BodyComponent;
import components.SkillsComponent;
import components.StatusEffectsComponent;
import components.VisionComponent;
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
		if(ID >=NPCsByID.size()) {
			System.out.println("ID de NPC incorrecta, el máximo es " + (NPCsByID.size()-1) + " y se pidió " + ID);
			return null;
		}else {
			Entity npc = NPCsByID.get(ID).clone();
			addBasicComponents(npc);
			return npc;
		}
	}
	
	private static void addBasicComponents(Entity npc) {
		npc.addComponent(new VisionComponent());
		npc.addComponent(new StatusEffectsComponent());
		npc.addComponent(new BodyComponent());
		npc.addComponent(new SkillsComponent());
		AIComponent AI = new AIComponent();
		AI.changeBeh(new WanderingBeh(npc));
		npc.addComponent(AI);
	}
	
}
