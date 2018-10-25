package components;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Component;

public class HealthComponent extends Component implements ObservableValue<Float>{
	
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

	@Override
	public void addListener(InvalidationListener listener) {
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		
	}

	@Override
	public void addListener(ChangeListener<? super Float> listener) {
		
	}

	@Override
	public Float getValue() {
		return null;
	}

	@Override
	public void removeListener(ChangeListener<? super Float> listener) {
	}

	@Override
	public String serialize() {
		return "HP " + maxHP + "." + curHP + "." + HPreg;
	}

}
