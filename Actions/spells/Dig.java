package spells;

import java.util.List;

import actions.ActionType;
import actions.EndTurn;
import components.PositionComponent;
import components.SkillsComponent.Skill;
import factories.TerrainFactory;
import gameScreen.Console;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Dig extends Spell{
	
	public static Dig instance = null;
	
	private Dig() {
		name = "Dig";
		description = "Used to dig through walls.";
		usedSkill = Skill.GEOMANCY;
		range = 10;
		area = 1;
		isProjectile = true;
	}

	@Override
	public void cast(Entity caster, Tile target) {
		List<Tile> trajectory = Map.getStraigthLine(caster.get(PositionComponent.class), target.getPos());
		for(Tile t : trajectory) {
			String terrainName = t.get(Type.TERRAIN).name;
			if(terrainName.contains("wall")) {
				t.remove(Type.TERRAIN);
				t.put(TerrainFactory.get(terrainName.replace("wall", "floor")));
				Console.addMessage("The " + terrainName + " crumbles down.\n");
				break;
			}
		}
		
		EndTurn.execute(caster, ActionType.CAST_SPELL);
	}
	
	public static Dig getInstance() {
		if(instance == null) {
			instance = new Dig();
		}
		return instance;
	}
	
}
