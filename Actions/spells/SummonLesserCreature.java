package spells;

import actions.ActionType;
import actions.EndTurn;
import components.SkillsC.Skill;
import factories.EntityFactory;
import main.Entity;
import main.Type;
import tile.Tile;

public class SummonLesserCreature extends Spell{
	
	public static SummonLesserCreature instance = null;
	
	private SummonLesserCreature() {
		name = "Summon lesser creature";
		description = "Summons a weak creature to aid you.";
		usedSkill = Skill.SUMMONING;
		range = 3;
		area = 1;
		isProjectile = false;
	}

	@Override
	public void cast(Entity caster, Tile target) {
		Entity summon = EntityFactory.createRandom(Type.NPC);
		target.put(summon);
		EndTurn.execute(caster, ActionType.CAST_SPELL);
	}
	
	public static SummonLesserCreature getInstance() {
		if(instance == null) {
			instance = new SummonLesserCreature();
		}
		return instance;
	}
	
}
