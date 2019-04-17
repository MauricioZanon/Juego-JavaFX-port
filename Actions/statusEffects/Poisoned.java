package statusEffects;

import components.HealthC;
import effects.Effects;
import gameScreen.Console;
import main.Entity;
import main.Type;

//TODO esta status es solo para probar, hay que borrarlo y hacer otros
public class Poisoned extends Status{
	
	public Poisoned(int duration) {
		trigger = StTrigger.START_TURN;
		name = "poisoned";
		this.duration = duration;
	}

	@Override
	public void makeEffect(Entity affected) {
		HealthC hp = affected.get(HealthC.class);
		float damage = hp.getCurHP() / 5;
		
		Effects.receiveDamage(affected, damage);
		
		if(affected.type == Type.PLAYER) {
			Console.addMessage("You suffer from poison\n");
		}
	}

}
