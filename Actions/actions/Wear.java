package actions;

import java.util.Set;

import components.BodyC;
import components.BodyC.BodyPart;
import components.OccupiesC;
import gameScreen.Console;
import javafx.scene.paint.Color;
import main.Entity;

public abstract class Wear {
	
	public static void execute(Entity actor, Entity armor) {
		Set<BodyPart> freePartsNeeded = armor.get(OccupiesC.class).occupies;
		
		if(actor.get(BodyC.class).isOccupied(freePartsNeeded)) {
			createFailMessage(actor.get(BodyC.class).getConflictingEquipment(freePartsNeeded));
		}else {
			Console.addMessage("You wear the -" + armor.name + "-.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}
		EndTurn.execute(actor, ActionType.EQUIP);
	}
	
	private static void createFailMessage(Set<Entity> confEquipment) {
		StringBuilder sb = new StringBuilder();
		sb.append("First you need to take off your -");
		confEquipment.forEach(e -> sb.append(e.name + " "));
		Console.addMessage(sb.toString(), Color.WHITE, Color.CADETBLUE, Color.WHITE);
	}
	
}
