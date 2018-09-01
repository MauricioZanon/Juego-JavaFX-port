package behaviours;

import actions.FollowPath;
import components.AIComponent;
import components.MovementComponent;
import components.PositionComponent;
import components.VisionComponent;
import main.Entity;
import pathFind.AStar;
import tile.Tile;

public class AttackingBeh extends Behaviour{
	
	private Tile target = null;
	
	public AttackingBeh(Entity actor, Tile target) {
		this.actor = actor;
		this.target = target;
	}

	public void update() {
		super.update();
		refreshPath();
		if(actor.get(MovementComponent.class).path.isEnded()) {
			actor.get(AIComponent.class).resumePreviousBeh();
		}else {
			FollowPath.execute(actor);
		}
	}
	
	private void refreshPath() {
		if(actor.get(VisionComponent.class).visionMap.contains(target)) {
			actor.get(MovementComponent.class).path = AStar.findPath(actor.get(PositionComponent.class), target.getPos(), actor);
		}
		
	}

}
