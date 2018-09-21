package eventSystem;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

import components.AIComponent;
import gameScreen.GameScreen;
import main.Entity;

public class EventSystem {
	
	private static int gameTurn = 0;
	private static PriorityQueue<Entity> entities = new PriorityQueue<>(createComparator());
	
	public static boolean waitingOnPlayerInput = false;
	
	public static void setTimedEntities(Set<Entity> e) {
		entities.clear();
		if(!e.isEmpty()) {
			entities.addAll(e);
		}
	}
	
	public static void update() {
		while(!waitingOnPlayerInput) {
			Entity entity = entities.remove();
			AIComponent AI = entity.get(AIComponent.class);
			
			if(!AI.isActive) {
				continue;
			}
			
			int entityTurn = AI.nextTurn;
			if(entityTurn < gameTurn) { // Si el turno de la entidad es menor al turno actual se lo actualiza y no actua
				AI.nextTurn = gameTurn + 6;
			}else {
				gameTurn = entityTurn;
				AI.getBeh().update();
			}
			
			entities.add(entity);
			
			if(entity.ID == -1) break;
		}
		
		GameScreen.getInstance().refreshPlayerView();
		
	}
	
	private static Comparator<Entity> createComparator(){
		return new Comparator<Entity>() {
			@Override
			public int compare(Entity a, Entity b) {
				long turnA = a.get(AIComponent.class).nextTurn;
				long turnB = b.get(AIComponent.class).nextTurn;
				
				if(turnA > turnB) return 1;
				else if(turnA < turnB) return -1;
				else return 0;
			}
		};
	}

	public static PriorityQueue<Entity> getEntities() {
		return entities;
	}
}