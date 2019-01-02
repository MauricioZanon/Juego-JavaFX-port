package actions;

import components.SkillsC;
import components.SkillsC.Skill;
import effects.Effects;
import main.Entity;
import tile.Tile;

public abstract class Jump {
	
	public static void execute(Entity actor, Tile target) {
		Effects.move(actor, target.pos);
		actor.get(SkillsC.class).change(Skill.ACROBATICS, 0.1f);
		EndTurn.execute(actor, ActionType.WALK);
	}
	
}
