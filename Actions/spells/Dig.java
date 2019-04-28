package spells;

import components.SkillsC.Skill;
import components.VisionC;
import factories.EntityFactory;
import gameScreen.Console;
import main.Entity;
import main.Type;
import tile.Tile;

public class Dig extends Spell{
	
	public static Dig instance = null;
	
	private Dig() {
		name = "Dig";
		description = "You make the terrain recede from the target area.";
		usedSkill = Skill.GEOMANCY;
		range = 10;
		area = 1;
		isProjectile = false;
	}

	@Override
	public void cast(Entity caster, Tile target) {
		if(!caster.get(VisionC.class).visionMap.contains(target)) return;
		
		Entity terrain = target.get(Type.TERRAIN);
		String terrainName = terrain.name;
		if(terrain.type.is(Type.WALL)) {
			target.remove(Type.TERRAIN);
			target.put(EntityFactory.create(terrainName.replace("wall", "floor")));
			Console.addMessage("The " + terrainName + " crumbles down.\n");
		}
	}
	
	public static Dig getInstance() {
		if(instance == null) {
			instance = new Dig();
		}
		return instance;
	}
	
}
