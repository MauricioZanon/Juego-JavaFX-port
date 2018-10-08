package actions;

import effects.Effects;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;

//TODO TEST
public abstract class Attack {
	
	public static void execute(Entity attacker, Entity receiver) {
		float damage = calculateDamage(attacker);
		Effects.receiveDamage(receiver, damage);
		
		if(attacker.TYPE == Type.PLAYER) {
			Console.addMessage("You attack the -" + receiver.name + "-.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
		}
		else if(receiver.TYPE == Type.PLAYER) {
			Console.addMessage("The -" + attacker.name + "- attacks you.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
		}
		EndTurn.execute(attacker, ActionType.ATTACK);
	}
	
	private static float calculateDamage(Entity attacker) {
		float damage = attacker.get("damage");
		damage *= (attacker.get("STR") / 10);
		return damage;
	}
}
