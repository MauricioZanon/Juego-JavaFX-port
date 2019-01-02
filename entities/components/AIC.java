package components;

import java.util.ArrayDeque;

import behaviours.Behaviour;

public class AIC extends Component{ //TODO test
	
	public int nextTurn = 0;
	public boolean isActive = true;
	private ArrayDeque<Behaviour> behaviourStack = new ArrayDeque<>();

	public AIC() {
		isBase = false;
	}

	@Override
	public AIC clone() {
		AIC newAI = new AIC();
		newAI.nextTurn = nextTurn;
		newAI.isActive = isActive;
		newAI.behaviourStack.addAll(behaviourStack); //TODO ver si se copian en el orden correcto
		return newAI;
	}
	
	public Behaviour getBeh() {
		return behaviourStack.peek();
	}
	
	/**
	 * Cambia en behaviour actual de la AI, el anterior queda guardado en la pila
	 */
	public void changeBeh(Behaviour newBeh) {
		behaviourStack.push(newBeh);
	}
	
	public void resumePreviousBeh() {
		behaviourStack.pop();
	}

	@Override
	public String serialize() {
		return "";
	}

}
