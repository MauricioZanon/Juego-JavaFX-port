package components;

import main.Component;

public class MovementComponent extends Component {

	public MovementType movementType = MovementType.WALK;
//	public Path path = null;
	
	public enum MovementType{
		FLY,
		WALK,
		SWIM,
		FLY_WALK,
		WALK_SWIM,
		ALL,
	}
	
	@Override
	public MovementComponent clone() {
		MovementComponent comp = new MovementComponent();
		comp.movementType = movementType;
		return comp;
	}

}
