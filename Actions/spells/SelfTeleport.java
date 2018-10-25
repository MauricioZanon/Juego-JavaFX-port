package spells;

import java.util.Set;

import RNG.RNG;
import actions.ActionType;
import actions.EndTurn;
import components.ContainerComponent;
import components.SkillsComponent.Skill;
import effects.Effects;
import main.Entity;
import map.Map;
import tile.Tile;

public class SelfTeleport extends Spell{
	
	public static SelfTeleport instance = null;
	
	private SelfTeleport() {
		name = "Teleport self";
		description = "Teleports you to a random location.";
		usedSkill = Skill.ILLUSION;
		range = 0;
		area = 1;
		isProjectile = false;
		
		condition = e -> e.get(ContainerComponent.class).contains("slime goo");
	}

	@Override
	public void cast(Entity caster, Tile target) {
		caster.get(ContainerComponent.class).remove("slime goo", 1);
		Set<Tile> area = Map.getCircundatingAreaAsSet(15, target, true);
		Tile selectedTile = RNG.getRandom(area, t-> t.isTransitable());
		Effects.move(caster, selectedTile.getPos());
		
		EndTurn.execute(caster, ActionType.CAST_SPELL);
	}
	
	public static SelfTeleport getInstance() {
		if(instance == null) {
			instance = new SelfTeleport();
		}
		return instance;
	}
	
}
