package actions;

import components.SkillsComponent;
import components.SkillsComponent.Skill;
import effects.Effects;
import main.Entity;
import tile.Tile;

public abstract class Jump {
	
	public static void execute(Entity actor, Tile target) {
		Effects.move(actor, target.getPos());
		actor.get(SkillsComponent.class).change(Skill.ACROBATICS, 0.1f);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
