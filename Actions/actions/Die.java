package actions;

import application.Main;
import components.AIC;
import components.GraphicC;
import components.PositionC;
import components.StatusEffectsC;
import components.VisionC;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import statusEffects.StTrigger;
import tile.Tile;

public abstract class Die {
	
	public static void execute(Entity actor) {
		Tile tile = actor.get(PositionC.class).getTile();
		tile.remove(Type.ACTOR);
		
		if(Main.player.get(VisionC.class).visionMap.contains(tile)) {
			Console.addMessage("The -" + actor.name + "- dies.\n", Color.WHITE, Color.RED, Color.WHITE);
		}
		
		actor.get(AIC.class).isActive = false;
		actor.type = Type.CORPSE;
		actor.name += " corpse";
		actor.get(GraphicC.class).ASCII = "%";
		
		tile.put(actor);
		
		actor.get(StatusEffectsC.class).triggerStatus(StTrigger.DIE);
	}
	
}
