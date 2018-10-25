package components;

import java.util.Stack;

import behaviours.Behaviour;
import main.Component;

public class AIComponent extends Component{ //TODO test
	
	public int nextTurn = 0;
	public boolean isActive = true;
	private Stack<Behaviour> behaviourStack = new Stack<>();

	@Override
	public Component clone() {
		AIComponent newAI = new AIComponent();
		newAI.nextTurn = nextTurn;
		newAI.isActive = isActive;
		newAI.behaviourStack.addAll(behaviourStack); //TODO ver si se copian en el orden correcto
		return null;
	}
	
	public Behaviour getBeh() {
		return behaviourStack.peek();
	}
	
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
