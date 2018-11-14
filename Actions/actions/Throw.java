package actions;

import java.util.ArrayList;

import components.ContainerComponent;
import components.PositionComponent;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import effects.Effects;
import gameScreen.Console;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Throw {
	
	private Throw() {}
	
	public static void execute(Entity actor, Tile target, String thrownItemName) {
		ArrayList<Tile> trajectory = Map.getStraigthLine(actor.get(PositionComponent.class), target.getPos());
		Entity thrownItem = actor.get(ContainerComponent.class).remove(thrownItemName, 1).getFirst();
		
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
		actor.get(SkillsComponent.class).change(Skill.THROWING, 0.1f);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	private static float calculateDamage(Entity actor, Entity thrownItem) {
		float skillMod = actor.get(SkillsComponent.class).get(Skill.THROWING) / 5;
		return thrownItem.get("damage") * skillMod;
	}
	
}
