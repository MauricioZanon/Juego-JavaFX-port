package components;

import javafx.scene.paint.Color;
import main.Component;

/**Este component tiene el color de fondo de las entidades que lo necesiten*/
public class BackColorComponent extends Component{

	public Color[] colors = new Color[10];
	
	@Override
	public BackColorComponent clone() {
		BackColorComponent c = new BackColorComponent();
		for(int i = 0; i < colors.length; i++) {
			c.colors[i] = new Color(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), colors[i].getOpacity());
		}
		return c;
	}

	@Override
	public String serialize() {
		return "";
	}

}
