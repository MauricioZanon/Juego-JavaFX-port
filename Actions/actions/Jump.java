package actions;

import effects.Effects;
import gameScreen.GameScreenController;
import main.Entity;
import system.RenderSystem;
import tile.Tile;

public abstract class Jump {
	
	public static void execute(Entity actor, Tile target) {
		Effects.move(actor, target.getPos());
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	public static void setListener() {

		GameScreenController controller = (GameScreenController) RenderSystem.getInstance().getController();
		controller.startTileSelection("jump", 3, true, 1);
	}
	
}
