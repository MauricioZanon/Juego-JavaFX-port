package components;

import main.Component;

public class TranslucentComponent extends Component{
	
	public boolean translucent = true;
	
	@Override
	public TranslucentComponent clone() {
		TranslucentComponent c = new TranslucentComponent();
		c.translucent = translucent;
		return c;
	}

}
