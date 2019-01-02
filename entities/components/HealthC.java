package components;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class HealthC extends Component implements ObservableValue<Float>{
	
	private float maxHP = 20;
	private float curHP = 20;
	private float HPreg = 0.1f;
	
	public HealthC() {
		isBase = false;
	}

	@Override
	public HealthC clone() {
		HealthC comp = new HealthC();
		comp.maxHP = maxHP;
		comp.curHP = curHP;
		comp.HPreg = HPreg;
		return comp;
	}
	
	public void regenerate() {
		changeCurHP(HPreg);
	}
	
	public float getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(float maxHP) {
		this.maxHP = maxHP;
	}

	public void setCurHP(float curHP) {
		this.curHP = curHP;
	}
	
	public float getCurHP() {
		return curHP;
	}

	public void changeCurHP(float quantity) {
		curHP += quantity;
		if(curHP > maxHP)
			curHP = maxHP;
	}

	public float getHPreg() {
		return HPreg;
	}

	public void setHPreg(float hPreg) {
		HPreg = hPreg;
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
