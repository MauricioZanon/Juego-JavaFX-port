package behaviours;

import RNG.RNG;
import actions.Bump;
import components.AIComponent;
import components.PositionComponent;
import components.VisionComponent;
import main.Entity;
import map.Map;
import tile.Tile;
import world.Direction;

public class Wandering extends Behaviour{ //TODO test
	
	public Wandering(Entity actor) {
		this.actor = actor;
	}
	
	public void update() {
		super.update();
		
		VisionComponent fov = actor.get(VisionComponent.class);
		if(fov.enemyTiles.isEmpty()) {
			Bump.execute(actor.get(PositionComponent.class), RNG.getRandom(Direction.values()));
		}else {
			AIComponent ai = actor.get(AIComponent.class);
			Tile actorTile = actor.get(PositionComponent.class).getTile();
			Tile targetTile = Map.getClosestTile(actorTile, fov.enemyTiles);
			
			ai.changeBeh(new AttackingBeh(actor, targetTile));
		}
	}
	
}
