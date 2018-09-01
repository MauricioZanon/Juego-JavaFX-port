package factories;

import java.util.HashMap;

import RNG.RNG;
import behaviours.Wandering;
import components.AIComponent;
import components.StatusEffectsComponent;
import components.VisionComponent;
import main.Entity;

public abstract class NPCFactory extends EntityFactory{
	
	protected static HashMap<String, Entity> NPCs = new HashMap<>();
	
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
	
	private static void addBasicComponents(Entity npc) {
		npc.addComponent(new VisionComponent());
		npc.addComponent(new StatusEffectsComponent()); //TODO sacar esta linea cuando se agregue este component en el XML
		AIComponent AI = new AIComponent();
		AI.changeBeh(new Wandering(npc));
		npc.addComponent(AI);
	}
	
}
