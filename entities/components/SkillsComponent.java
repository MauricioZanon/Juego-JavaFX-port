package components;

import java.util.EnumMap;
import java.util.Map.Entry;

import main.Component;

/**
 * Una lista de todas las cosas que la entidad sabe hacer
 */
public class SkillsComponent extends Component{
	
	private final EnumMap<Skill, Float> skills = new EnumMap<>(Skill.class);
	
	public void set(Skill skill, float value) {
		skills.put(skill, value);
	}
	
	/** Devuelve el nivel del skill en forma de entero redondeado para abajo */
	public int get(Skill skill) {
		return skills.containsKey(skill) ? skills.get(skill).intValue() : 1;
	}
	
	/** Devuelve el nivel exacto del skill */
	public float getExact(Skill skill) {
		return skills.containsKey(skill) ? skills.get(skill) : 1;
	}
	
	public void change(Skill skill, float value) {
		if(!skills.containsKey(skill)) {
			skills.put(skill, 1 + value);
		}
		else {
			float newValue = skills.get(skill) + value * skill.dificultyMod * 1 / skills.get(skill);
			if(newValue > 10) {
				newValue = 10;
			}
			else if(newValue < 1) {
				newValue = 1;
			}
			skills.put(skill, newValue);
		}
	}
	
	@Override
	public SkillsComponent clone() {
		SkillsComponent newComp = new SkillsComponent();
		for(Entry<Skill, Float> entry : skills.entrySet()) {
			newComp.set(entry.getKey(), entry.getValue());
		}
		return newComp;
	}
	
	public enum Skill{
		/** Saltos, dodge y cheks de agilidad */
		ACROBATICS(1),
		AEROMANCY(1),
		AGRICULTURE(1),
		ALCHEMY(1),
		ARCHERY(1),
		/** Predice los eventos que esten por paar */
		ASTROLOGY(1),
		AXES(1),
		BLOCKING(1),
		BOTANY(1),
		BUTCHERY(1),
		CARPENTRY(1),
		/** Crea items sin usar materiales */
		CONJURATION(1),
		COOKING(1),
		CRYOMANCY(1),
		DIVINATION(1),
		DODGE(1),
		DUAL_WIELD(1),
		ENCHANTING(1),
		FLETCHERY(1),
		GEOMANCY(1),
		HEAVY_ARMOR(1),
		HEMOMANCY(1),
		HYDROMANCY(1),
		ILLUSION(1),
		LIGHT_ARMOR(1),
		LITERACY(1),
		LOCKPICKING(1),
		LORE(1),
		MACES(1),
		MAGIC(1),
		MASONRY(1),
		MEDICINE(1),
		MELEE(1),
		NECROMANCY(1),
		PYROMANCY(1),
		RUNES(1),
		SEWING(1),
		SHORT_BLADES(1),
		SMITHING(1),
		STEALTH(1),
		SUMMONING(1),
		/** Crear fogatas, carpas, etc. predecir el clima, pescar */
		SURVIVAL(1),
		SWIMING(1),
		SWORDS(1),
		THROWING(1),
		TRANSFIGURAION(1),
		TRAPPING(1),
		TWO_HANDED_WEAPONS(1),
		UNNARMED_COMBAT(1);
		
		/** La dificultad para lvlear el skill, mientras mas bajo mas dificil es (0 - 1) */
		public float dificultyMod;
		
		Skill(float dificultyMod) {
			this.dificultyMod = dificultyMod;
		}
	}

	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder("SKI ");
		
		for(Entry<Skill, Float> entry : skills.entrySet()) {
			sb.append(entry.getKey().toString() + "." + entry.getValue() + "-");
		}
		
		return sb.toString();
	}
	
}
