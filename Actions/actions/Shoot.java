package actions;

import java.util.ArrayList;

import components.BodyC;
import components.ContainerC;
import components.PositionC;
import components.SkillsC;
import components.SkillsC.Skill;
import effects.Effects;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Att;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Shoot {
	
	private Shoot() {}
	
	public static void execute(Entity actor, Tile target) {
		ArrayList<Tile> trajectory = Map.getStraigthLine(actor.get(PositionC.class), target.pos);
		Entity ammunition = actor.get(ContainerC.class).remove("wooden arrow", 1).getFirst();
		
		for(int i = 1; i < trajectory.size(); i++) {
			Tile t = trajectory.get(i);
			if(t.get(Type.ACTOR) != null) {
				t.put(ammunition);
				float damage = calculateDamage(actor, ammunition);
				Effects.receiveDamage(t.get(Type.ACTOR), damage);
				Console.addMessage("You shoot at the- " + t.get(Type.ACTOR).name + "-.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
				EndTurn.execute(actor, ActionType.WALK);
				return;
			}
		}
		trajectory.get(trajectory.size()-1).put(ammunition);
		actor.get(SkillsC.class).change(Skill.ARCHERY, 0.1f);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	private static float calculateDamage(Entity actor, Entity ammunition) {
		float skillMod = actor.get(SkillsC.class).get(Skill.ARCHERY) / 5f;
		float bowDmg = actor.get(BodyC.class).getWeapon().get(Att.DAMAGE);
		return (bowDmg + ammunition.get(Att.DAMAGE)) * skillMod;
	}

}
