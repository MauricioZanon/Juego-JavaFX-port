package components;

import pathFind.Path;

public class MovementC extends Component {

	public MovementType movementType = MovementType.WALK;
	public Path path = new Path();
	
	public MovementC() {
		isBase = false;
	}
	
	@Override
	public MovementC clone() {
		MovementC comp = new MovementC();
		comp.movementType = movementType;
		return comp;
	}

	@Override
	public String serialize() {
		return "MOV " + movementType.toString();
	}
	
	public enum MovementType{
		FLY,
		WALK,
		SWIM,
		AMPHIBIOUS,
	}

}
