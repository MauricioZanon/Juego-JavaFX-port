package spells;

import java.util.function.Predicate;

import components.SkillsC.Skill;
import main.Entity;
import tile.Tile;

public abstract class Spell {
	
	protected Skill usedSkill;
	
	protected Predicate<Entity> condition = e -> true;
	
	protected String name;
	protected String description;
	
	protected int range;
	protected int area;
	
	protected boolean isProjectile;
	
	public abstract void cast(Entity caster, Tile target);
	
	public boolean canBeCasted(Entity caster) {
		return condition.test(caster);
	}

	public Skill getUsedSkill() {
		return usedSkill;
	}

	public Predicate<Entity> getCondition() {
		return condition;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public int getRange() {
		return range;
	}

	public int getArea() {
		return area;
	}

	public boolean isProjectile() {
		return isProjectile;
	}

	@Override
	public String toString() {
		return name;
	}
	
	//TODO quitar el switch y hacer esto con Reflections
	public static Spell get(String spellName) {
		return switch(spellName) {
		case "teleport self" -> TeleportSelf.getInstance();	
		case "dig" -> Dig.getInstance();
		case "Summon lesser creature" -> SummonLesserCreature.getInstance();
		default -> null; 
		};
	}
}
