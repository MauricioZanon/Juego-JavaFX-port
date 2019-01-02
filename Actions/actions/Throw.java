package actions;

import java.util.ArrayList;

import components.ContainerC;
import components.PositionC;
import components.SkillsC;
import components.SkillsC.Skill;
import effects.Effects;
import gameScreen.Console;
import main.Att;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Throw {
	
	private Throw() {}
	
	public static void execute(Entity actor, Tile target, String thrownItemName) {
		ArrayList<Tile> trajectory = Map.getStraigthLine(actor.get(PositionC.class), target.pos);
		Entity thrownItem = actor.get(ContainerC.class).remove(thrownItemName, 1).getFirst();
		
		for(int i = 1; i < trajectory.size(); i++) {
			Tile t = trajectory.get(i);
			if(t.get(Type.ACTOR) != null) {
				t.put(thrownItem);
				float damage = calculateDamage(actor, thrownItem);
				Effects.receiveDamage(t.get(Type.ACTOR), damage);
				Console.addMessage("The " + thrownItem.name + " hits the " + t.get(Type.ACTOR).name);
				EndTurn.execute(actor, ActionType.WALK);
				return;
			}
		}
		trajectory.get(trajectory.size()-1).put(thrownItem);
		actor.get(SkillsC.class).change(Skill.THROWING, 0.1f);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	private static float calculateDamage(Entity actor, Entity thrownItem) {
		float skillMod = actor.get(SkillsC.class).get(Skill.THROWING) / 5;
		return thrownItem.get(Att.DAMAGE) * skillMod;
	}
	
}
