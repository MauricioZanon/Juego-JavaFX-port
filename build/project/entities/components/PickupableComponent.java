package components;

import main.Component;

public class PickupableComponent extends Component{

	@Override
	public PickupableComponent clone() {
		return new PickupableComponent();
	}

}
