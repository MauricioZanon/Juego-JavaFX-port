package spells;

import java.util.Set;

import RNG.RNG;
import components.ContainerC;
import components.MovementC;
import components.MovementC.MovementType;
import components.SkillsC.Skill;
import effects.Effects;
import main.Entity;
import map.Map;
import tile.Tile;

public class TeleportSelf extends Spell{
	
	public static TeleportSelf instance = null;
	
	private TeleportSelf() {
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
		Effects.move(caster, selectedTile);
	}
	
	public static TeleportSelf getInstance() {
		if(instance == null) {
			instance = new TeleportSelf();
		}
		return instance;
	}
	
}
