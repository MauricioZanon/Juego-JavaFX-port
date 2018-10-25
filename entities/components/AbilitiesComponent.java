package components;

import java.util.HashMap;
import java.util.Map;

import main.Component;
import spells.Spell;

public class AbilitiesComponent extends Component{
	
	private Map<String, Spell> spells = new HashMap<>();
	
	public void addSpell(Spell spell) {
		spells.put(spell.getName(), spell);
	}
	
	public void removeSpell(String spellName) {
		spells.remove(spellName);
	}
	
	public Spell getSpell(String spellName) {
		return spells.get(spellName);
	}
	
	public Map<String, Spell> getSpells(){
		return spells;
	}

	@Override
	public Component clone() {
		AbilitiesComponent ac = new AbilitiesComponent();
		spells.values().forEach(s -> ac.addSpell(s));
		return ac;
	}

	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder("ABI ");
		
		for(Spell spell : spells.values()) {
			sb.append(spell.getName() + "-");
		}
		
		return sb.toString();
	}

}
