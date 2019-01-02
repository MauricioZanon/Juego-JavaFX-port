package behaviours;

import actions.FollowPath;
import components.AIC;
import components.MovementC;
import components.PositionC;
import components.VisionC;
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
		if(actor.get(MovementC.class).path.isEnded()) {
			actor.get(AIC.class).resumePreviousBeh();
		}else {
			FollowPath.execute(actor);
		}
	}
	
	private void refreshPath() {
		PositionC targetPos = target.get(PositionC.class);
		if(actor.get(VisionC.class).visionMap.contains(targetPos.getTile())) {
			actor.get(MovementC.class).path = AStar.findPath(actor.get(PositionC.class), targetPos, actor);
		}
	}

}
