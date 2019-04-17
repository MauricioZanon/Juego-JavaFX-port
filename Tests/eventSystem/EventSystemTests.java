package eventSystem;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Test;

import RNG.RNG;
import components.AIC;
import factories.EntityFactory;
import main.Entity;
import main.Type;

public class EventSystemTests {
	
	@Test
	public void correctOrderTest() {
		Set<Entity> entities = new HashSet<>();
		for(int i = 0; i < 1000; i++) {
			Entity npc = EntityFactory.createRandom(Type.NPC);
			npc.get(AIC.class).nextTurn = RNG.nextInt(100000);
			entities.add(npc);
		}
		EventSystem.setTimedEntities(entities);
		
		float lastTurn = 0;
		PriorityQueue<Entity> queue = EventSystem.getEntities();
		
		while(!queue.isEmpty()) {
			Entity e = queue.remove();
			float entityTurn = e.get(AIC.class).nextTurn;
			assertTrue("lastTurn " + lastTurn + " entityTurn " + entityTurn, lastTurn <= entityTurn);
			lastTurn = entityTurn;
			if(RNG.nextInt(10) == 1) {
				Entity npc = EntityFactory.createRandom(Type.NPC);
				npc.get(AIC.class).nextTurn = RNG.nextFloat(lastTurn, 100000);
				queue.add(npc);
			}
		}
	}
	
	

}
