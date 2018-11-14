package behaviours;

import actions.FollowPath;
import components.AIComponent;
import components.MovementComponent;
import components.PositionComponent;
import components.VisionComponent;
import main.Entity;
import pathFind.AStar;

public class AttackingBeh extends Behaviour{
	
	private Entity target = null;
	
	public AttackingBeh(Entity actor, Entity target) {
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
		PositionComponent targetPos = target.get(PositionComponent.class);
		if(actor.get(VisionComponent.class).visionMap.contains(targetPos.getTile())) {
			actor.get(MovementComponent.class).path = AStar.findPath(actor.get(PositionComponent.class), targetPos, actor);
		}
	}

}
