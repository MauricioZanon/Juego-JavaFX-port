package actions;

import application.Main;
import components.MovementC;
import components.PositionC;
import main.Entity;
import pathFind.AStar;
import pathFind.Path;
import tile.Tile;
import world.Direction;

//TODO test
public abstract class FollowPath {
	
	public static void execute(Entity actor) {
		Path path = actor.get(MovementC.class).path;
		if(path != null && !path.isEnded()) {
			PositionC nextPos = path.getNext();
			path.advance();
			PositionC actualPos = actor.get(PositionC.class);
			Bump.execute(actualPos, Direction.get(actualPos, nextPos), true);
		}
		else {
			EndTurn.execute(actor, ActionType.WAIT);
		}
	}
	
	/** Primero crea el Path desde el actor hasta el tile destino y luego lo hace avanzar */
	public static void execute(Entity actor, Tile destiny) {
		PositionC pos = Main.player.get(PositionC.class);
		Path path = AStar.findPath(pos, destiny.pos, Main.player);
		if(path.getLength() > 0) {
			Main.player.get(MovementC.class).path = path;
			execute(Main.player);
		}
	}
	

}
