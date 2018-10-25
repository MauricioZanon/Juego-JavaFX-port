package actions;

import RNG.RNG;
import components.BodyComponent;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import effects.Effects;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;

//TODO TEST
public abstract class Attack {
	
	public static void execute(Entity attacker, Entity receiver) {
		if(attackLanded(attacker, receiver)) {
			float damage = calculateDamage(attacker);
			Effects.receiveDamage(receiver, damage);
			
			if(attacker.TYPE == Type.PLAYER) {
				Console.addMessage("You attack the -" + receiver.name + "-.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
			}
			else if(receiver.TYPE == Type.PLAYER) {
				Console.addMessage("The -" + attacker.name + "- attacks you.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
			}
		}
		else {
			receiver.get(SkillsComponent.class).change(Skill.DODGE, 0.1f);
			
			if(attacker.TYPE == Type.PLAYER) {
				Console.addMessage("The -" + receiver.name + "- dodges your attack.\n", Color.WHITE, Color.CRIMSON, Color.WHITE);
			}
			else if(receiver.TYPE == Type.PLAYER) {
				Console.addMessage("You dodge the attack.\n");
			}
		}
		
		EndTurn.execute(attacker, ActionType.ATTACK);
	}
	
	private static boolean attackLanded(Entity attacker, Entity receiver) {
		float attackerDexMod = attacker.get("DEX") / 10;
		float attackerSkillMod = attacker.get(SkillsComponent.class).get(getSkillUsed(attacker)) * attacker.get(SkillsComponent.class).get(Skill.MELEE);
		float acc = 100 * attackerDexMod * attackerSkillMod;
		
		float receiverDexMod = receiver.get("DEX") / 10;
		float receiverSkillMod = receiver.get(SkillsComponent.class).get(Skill.DODGE);
		float evasion = 100 * receiverDexMod * receiverSkillMod;
		
		return RNG.nextFloat(acc) >= RNG.nextFloat(evasion);
	}

	private static float calculateDamage(Entity attacker) {
		Skill skillUsed = getSkillUsed(attacker);
		SkillsComponent skills = attacker.get(SkillsComponent.class);
		
		float baseDamage = attacker.get("damage");
		float strMod = attacker.get("STR") / 10;
		float skillMod = skills.get(skillUsed);
		float totalDamage = baseDamage * strMod * skillMod;
		
		skills.change(skillUsed, 0.1f);
		
		return totalDamage;
	}
	
	private static Skill getSkillUsed(Entity attacker) {
		Skill skillUsed = Skill.UNNARMED_COMBAT;
		BodyComponent equipment = attacker.get(BodyComponent.class);
		if(equipment != null) {
			Entity weapon = equipment.getWeapon();
			if(weapon != null) {
				if(weapon.TYPE.is(Type.AXE)) {
					skillUsed = Skill.AXES;
				}
				else if(weapon.TYPE.is(Type.SWORD)) {
					skillUsed = Skill.SWORDS;
				}
				else if(weapon.TYPE.is(Type.MACE)) {
					skillUsed = Skill.MACES;
				}
				else if(weapon.TYPE.is(Type.DAGGER)) {
					skillUsed = Skill.SHORT_BLADES;
				}
			}
		}
		return skillUsed;
	}
}
