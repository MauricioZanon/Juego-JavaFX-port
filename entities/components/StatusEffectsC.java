package components;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import statusEffects.StTrigger;
import statusEffects.Status;

public class StatusEffectsC extends Component{
	
	public final EnumMap<StTrigger, Set<Status>> effects = new EnumMap<>(StTrigger.class);
	
	public StatusEffectsC() {
		isBase = false;
	}
	
	public void add(Status st) {
		StTrigger trigger = st.getTrigger();
		if(!effects.containsKey(trigger)) {
			effects.put(trigger, new HashSet<>());
		}
		effects.get(trigger).add(st);
	}
	
	public void triggerStatus(StTrigger trigger) {
		if(effects.containsKey(trigger)) {
			effects.get(trigger).forEach(st -> st.makeEffect());
		}
	}
	
	public void diminishDurations() {
		for(Set<Status> set : effects.values()) {
			set.forEach(st -> st.diminishDuration());
			set.removeIf(st -> st.getDuration() < 0);
		}
		effects.keySet().removeIf(key -> effects.get(key).isEmpty());
	}

	@Override
	public Component clone() {
		StatusEffectsC sc = new StatusEffectsC();
		for(Set<Status> set : effects.values()) {
			set.forEach(status -> sc.add(status));
		}
		return sc;
	}

	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder("STA ");
		
		for(Set<Status> set : effects.values()) {
			set.forEach(e -> sb.append(e.getName() + "." + e.getDuration() + "-"));
		}
		
		return sb.toString();
	}
	
}

