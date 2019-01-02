package spells;

import java.util.Set;

import RNG.RNG;
import actions.ActionType;
import actions.EndTurn;
import components.ContainerC;
import components.MovementC;
import components.MovementC.MovementType;
import components.SkillsC.Skill;
import effects.Effects;
import main.Entity;
import map.Map;
import tile.Tile;

public class SelfTeleport extends Spell{
	
	public static SelfTeleport instance = null;
	
	private SelfTeleport() {
		name = "Teleport self";
		description = "Teleports you to a random location. You need some slime goo to make this work";
		usedSkill = Skill.ILLUSION;
		range = 0;
		area = 1;
		isProjectile = false;
		
		condition = e -> e.get(ContainerC.class).contains("slime goo");
	}

	@Override
	public void cast(Entity caster, Tile target) {
		caster.get(ContainerC.class).remove("slime goo", 1);
		Set<Tile> area = Map.getCircundatingAreaAsSet(15, target, true);
		MovementType movType = caster.get(MovementC.class).movementType;
		Tile selectedTile = RNG.getRandom(area, t-> t.isTransitable(movType));
		Effects.move(caster, selectedTile.pos);
		
		EndTurn.execute(caster, ActionType.CAST_SPELL);
	}
	
	public static SelfTeleport getInstance() {
		if(instance == null) {
			instance = new SelfTeleport();
		}
		return instance;
	}
	
}
