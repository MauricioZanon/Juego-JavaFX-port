package components;

import main.Component;

public class TransitableComponent extends Component{
	
	public boolean transitable = true;
	
	@Override
	public TransitableComponent clone() {
		TransitableComponent c = new TransitableComponent();
		c.transitable = transitable;
		return c;
	}

}
