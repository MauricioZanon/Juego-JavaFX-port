package actions;

import components.MovementComponent;
import components.PositionComponent;
import main.Entity;
import pathFind.Path;
import world.Direction;

//TODO test
public abstract class FollowPath {
	
	public static void execute(Entity actor) {
		Path path = actor.get(MovementComponent.class).path;
		PositionComponent actualPos = actor.get(PositionComponent.class);
		PositionComponent nextPos = path.getNext();
		path.advance();
		
		Bump.execute(actualPos, Direction.get(actualPos, nextPos));
	}
	

}
