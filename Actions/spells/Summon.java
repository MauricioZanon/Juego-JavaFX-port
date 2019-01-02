package spells;

import actions.ActionType;
import actions.EndTurn;
import components.SkillsC.Skill;
import factories.NPCFactory;
import main.Entity;
import tile.Tile;

public class Summon extends Spell{
	
	public static Summon instance = null;
	
	private Summon() {
		name = "Summon lesser creature";
		description = "Summons a weak creature to aid you.";
		usedSkill = Skill.SUMMONING;
		range = 3;
		area = 1;
		isProjectile = false;
	}

	@Override
	public void cast(Entity caster, Tile target) {
		Entity summon = NPCFactory.createNPC();
		target.put(summon);
		EndTurn.execute(caster, ActionType.CAST_SPELL);
	}
	
	public static Summon getInstance() {
		if(instance == null) {
			instance = new Summon();
		}
		return instance;
	}
	
}
