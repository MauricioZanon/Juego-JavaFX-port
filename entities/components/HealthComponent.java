package components;

import main.Component;

public class HealthComponent extends Component {
	
	public float maxHP = 20;
	public float curHP = 20;
	public float HPreg = 0.1f;
	
	@Override
	public HealthComponent clone() {
		HealthComponent comp = new HealthComponent();
		comp.maxHP = maxHP;
		comp.curHP = curHP;
		comp.HPreg = HPreg;
		return comp;
	}

}
