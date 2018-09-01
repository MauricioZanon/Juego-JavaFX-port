package eventSystem;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Test;

import RNG.RNG;
import components.AIComponent;
import factories.NPCFactory;
import main.Entity;

public class EventSystemTests {
	
	@Test
	public void correctOrderTest() {
		Set<Entity> entities = new HashSet<>();
		for(int i = 0; i < 1000; i++) {
			Entity npc = NPCFactory.createNPC();
			npc.get(AIComponent.class).nextTurn = RNG.nextInt(100000);
			entities.add(npc);
		}
		EventSystem.setTimedEntities(entities);
		
		int lastTurn = 0;
		PriorityQueue<Entity> queue = EventSystem.getEntities();
		
		while(!queue.isEmpty()) {
			Entity e = queue.remove();
			int entityTurn = e.get(AIComponent.class).nextTurn;
			assertTrue("lastTurn " + lastTurn + " entityTurn " + entityTurn, lastTurn <= entityTurn);
			lastTurn = entityTurn;
			if(RNG.nextInt(10) == 1) {
				Entity npc = NPCFactory.createNPC();
				npc.get(AIComponent.class).nextTurn = RNG.nextInt(lastTurn, 100000);
				queue.add(npc);
			}
		}
	}
	
	

}
