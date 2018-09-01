package components;

import main.Component;

/**
 * No es un flag porque el que sea transitable o no depende del tipo de movimiento usado
 * TODO: implementar los distintos tipos de movimiento y sacar este comentario
 */

public class TransitableComponent extends Component{
	
	public boolean transitable = true;
	
	@Override
	public TransitableComponent clone() {
		TransitableComponent c = new TransitableComponent();
		c.transitable = transitable;
		return c;
	}

}
