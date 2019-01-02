package actions;

import main.Att;

/**
 * Los tipos de acción que pueden terminar un turno
 * TODO Cada acción tiene que tener un delay base (6 segundos para caminar por ejemplo) que se reduce o aumenta dependiendo del stat
 * 		asociado
 */
public enum ActionType{
	WALK(Att.MOV_SPEED),
	ATTACK(Att.ATTACK_SPEED),
	WAIT(Att.MOV_SPEED),
	CAST_SPELL(Att.CAST_SPEED),
	USE_ITEM(Att.MOV_SPEED),
	EQUIP(Att.MOV_SPEED),
	WIELD(Att.MOV_SPEED),
	THROW(Att.ATTACK_SPEED),
	CRAFT(Att.MOV_SPEED),
	PICK_UP(Att.MOV_SPEED);
	
	public Att asociatedStat;
	
	ActionType(Att as){
		asociatedStat = as;
	}
}
