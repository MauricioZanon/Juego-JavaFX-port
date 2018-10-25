package statusEffects;

import components.HealthComponent;
import effects.Effects;
import gameScreen.Console;
import main.Entity;
import main.Type;

//TODO esta status es solo apra probar, hay que borrarlo y hacer otros
public class Poisoned extends Status{
	
	public Poisoned(Entity affected, int duration) {
		trigger = StTrigger.START_TURN;
		name = "poisoned";
		this.affected = affected;
		this.duration = duration;
	}

	@Override
	public void makeEffect() {
		HealthComponent hp = affected.get(HealthComponent.class);
		float damage = hp.curHP / 5;
		
		Effects.receiveDamage(affected, damage);
		
		if(affected.TYPE == Type.PLAYER) {
			Console.addMessage("you suffer from poison");
		}
	}

}
