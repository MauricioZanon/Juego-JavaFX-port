package components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.Component;
import statusEffects.StTrigger;
import statusEffects.Status;

public class StatusEffectsComponent extends Component{
	
	public final Map<StTrigger, Set<Status>> effects = new HashMap<>();
	
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
		// TODO Auto-generated method stub
		return null;
	}
	
}

