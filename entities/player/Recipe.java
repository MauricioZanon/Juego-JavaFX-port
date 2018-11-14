package player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import application.Main;
import components.ContainerComponent;
import components.PositionComponent;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class Recipe {
	
	public final String name;
	public final String materials;
	public final String skills;
	public final String workStations;
	public final String tools;
	public final int craftTime;
	public final int difficulty;
	
	public Recipe(String name, String skills, String materials, String workStations, String tools, int craftTime, int difficulty) {
		this.name = name;
		this.materials = materials;
		this.skills = skills;
		this.workStations = workStations;
		this.tools = tools;
		this.craftTime = craftTime;
		this.difficulty = difficulty;
	}

	public boolean isCraftable() {
		return workStationsNearby() && hasSkills() && hasNecessaryItems();
	}
	
	//TODO debe buscar en un radio cercano
	private boolean hasNecessaryItems() {
		ContainerComponent inv = Main.player.get(ContainerComponent.class);
		String[] neededMats = materials.split("&");
		for(int i = 0; i < neededMats.length; i++) {
			String[] interchangeableMats = neededMats[i].split("\\|");
			for(int j = 0; j < interchangeableMats.length; j++) {
				String[] mat = interchangeableMats[j].split(":");
				if(!inv.contains(mat[0], Integer.parseInt(mat[1]))) {
					return false;
				}
			}
		}

		Set<String> neededTools = new HashSet<>(Arrays.asList(tools.split("-")));
		for(String tool : neededTools) {
			if(!inv.contains(tool)) return false;
		}
		
		return true;
	}
	
	private boolean hasSkills() {
		SkillsComponent playerSkills = Main.player.get(SkillsComponent.class);
		String[] neededSkills = skills.split(" ");
		for(int i = 0; i < neededSkills.length; i++) {
			String[] skill = neededSkills[i].split(":");
			if((playerSkills.get(Skill.valueOf(skill[0])) < Integer.parseInt(skill[1]))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean workStationsNearby() {
		Set<Tile> area = Map.getCircundatingAreaAsSet(6, Main.player.get(PositionComponent.class).getTile(), false);
		Set<String> neededStations = new HashSet<>(Arrays.asList(workStations.split("-")));
		
		for(Tile t : area) {
			Entity feature = t.get(Type.FEATURE);
			if(feature != null) {
				if(neededStations.contains(feature.name)) {
					neededStations.remove(feature.name);
				}
				if(neededStations.isEmpty()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void consumeMaterials() {
		ContainerComponent inv = Main.player.get(ContainerComponent.class);
		String[] neededMats = materials.split("&");
		for(int i = 0; i < neededMats.length; i++) {
			String[] interchangeableMats = neededMats[i].split("\\|");
			for(int j = 0; j < interchangeableMats.length; j++) {
				String[] mat = interchangeableMats[j].split(":");
				if(inv.contains(mat[0], Integer.parseInt(mat[1]))) {
					inv.remove(mat[0], Integer.parseInt(mat[1]));
					break;
				}
			}
		}
	}

}
