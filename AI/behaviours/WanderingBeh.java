package behaviours;

import RNG.RNG;
import actions.Bump;
import components.AIC;
import components.PositionC;
import components.VisionC;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public class WanderingBeh extends Behaviour{ //TODO test
	
	public WanderingBeh(Entity actor) {
		this.actor = actor;
	}
	
	public void update() {
		super.update();
		
		VisionC fov = actor.get(VisionC.class);
		if(fov.enemyTiles.isEmpty()) {
			Bump.execute(actor.get(PositionC.class), RNG.getRandom(Direction.values()));
		}else {
			AIC ai = actor.get(AIC.class);
			Tile actorTile = actor.get(PositionC.class).getTile();
			Tile targetTile = Map.getClosestTile(actorTile, fov.enemyTiles);
			
			ai.changeBeh(new AttackingBeh(actor, targetTile.get(Type.ACTOR)));
		}
	}
	
}
