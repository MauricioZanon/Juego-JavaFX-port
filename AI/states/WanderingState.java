package states;

import java.util.Set;

import RNG.RNG;
import actions.FollowPath;
import components.AIC;
import components.MovementC;
import components.PositionC;
import main.Entity;
import map.Map;
import pathFind.AStar;
import pathFind.Path;
import tile.Tile;

public class WanderingState extends State{
	
	public WanderingState() {}
	
	public WanderingState(Entity actor) {
		this.actor = actor;
	}
	
	public void update() {
		super.update();
		
		if(enemySighted()) {
			actor.get(AIC.class).setState(StateType.COMBAT);
			return;
		}
		
		Path path = actor.get(MovementC.class).path;
		if(path == null || path.isEnded()) {
			Set<Tile> nearbyArea = Map.getCircundatingAreaAsSet(5, actor.get(PositionC.class).getTile(), false);
			Tile destination = RNG.getRandom(nearbyArea, t -> t.isTransitable(actor.get(MovementC.class).movementType));
			Path newPath = AStar.findPath(actor.get(PositionC.class), destination.pos, actor);
			actor.get(MovementC.class).path = newPath;
		}
		
		FollowPath.execute(actor);
		
	}
	
	@Override
	public void enterState() {
	}
	
	@Override
	public void exitState() {
		actor.get(MovementC.class).path = null;
	}

}
