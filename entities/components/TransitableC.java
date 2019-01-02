package components;

import java.util.EnumMap;

import components.MovementC.MovementType;

/**
 * No es un flag porque el que sea transitable o no depende del tipo de movimiento usado
 * TODO: implementar los distintos tipos de movimiento y sacar este comentario
 */

public class TransitableC extends Component{
	
	private EnumMap<MovementType, Float> acceptedMovementTypes = new EnumMap<>(MovementType.class);
	
	public TransitableC() {
		isBase = true;
	}
	
	public boolean isTransitable(MovementType movType) {
		return acceptedMovementTypes.containsKey(movType);
	}
	
	public float getMovCost(MovementType movType) {
		if(acceptedMovementTypes.containsKey(movType)) {
			return acceptedMovementTypes.get(movType);
		}
		else {
			return 0;
		}
	}
	
	public void add(MovementType type, float cost) {
		acceptedMovementTypes.put(type, cost);
	}

	@Override
	public TransitableC clone() {
		return null;
	}

	@Override
	public String serialize() {
		return "";
	}

}
