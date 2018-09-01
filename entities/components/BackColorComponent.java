package components;

import javafx.scene.paint.Color;
import main.Component;

/**Este component tiene el color de fondo de las entidades que lo necesiten*/
public class BackColorComponent extends Component{

	public Color color;
	
	@Override
	public BackColorComponent clone() {
		BackColorComponent c = new BackColorComponent();
		c.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
		return c;
	}

}
