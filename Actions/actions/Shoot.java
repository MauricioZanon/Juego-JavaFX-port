package actions;

import java.util.ArrayList;

import components.BodyComponent;
import components.ContainerComponent;
import components.PositionComponent;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import effects.Effects;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Shoot {
	
	private Shoot() {}
	
	public static void execute(Entity actor, Tile target) {
		ArrayList<Tile> trajectory = Map.getStraigthLine(actor.get(PositionComponent.class), target.getPos());
		Entity ammunition = actor.get(ContainerComponent.class).remove("wooden arrow", 1).getFirst();
		
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
		actor.get(SkillsComponent.class).change(Skill.ARCHERY, 0.1f);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
	private static float calculateDamage(Entity actor, Entity ammunition) {
		float skillMod = actor.get(SkillsComponent.class).get(Skill.ARCHERY) / 5f;
		float bowDmg = actor.get(BodyComponent.class).getWeapon().get("damage");
		return (bowDmg + ammunition.get("damage")) * skillMod;
	}

}
