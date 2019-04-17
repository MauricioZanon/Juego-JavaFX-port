package actions;

import components.MovementC;
import components.PositionC;
import main.Entity;
import pathFind.Path;
import world.Direction;

//TODO test
public abstract class FollowPath {
	
	public static void execute(Entity actor) {
		Path path = actor.get(MovementC.class).path;
		PositionC actualPos = actor.get(PositionC.class);
		if(path != null && !path.isEnded()) {
			PositionC nextPos = path.getNext();
			path.advance();
			Bump.execute(actualPos, Direction.get(actualPos, nextPos));
		}
		else {
			EndTurn.execute(actor, ActionType.WAIT);
		}
	}
	

}
