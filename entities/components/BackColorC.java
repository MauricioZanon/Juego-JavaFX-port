package components;

import RNG.RNG;
import javafx.scene.paint.Color;

/**Este component tiene el color de fondo de las entidades que lo necesiten*/
public class BackColorC extends Component{

	public Color[] colors = new Color[10];
	
	public BackColorC(Color baseColor) {
		for(int i = 0; i < colors.length; i++) {
			colors[i] = RNG.getAproximateColor(baseColor);
		}
		isBase = true;
	}
	
	@Override
	public BackColorC clone() {
		return null;
	}

	@Override
	public String serialize() {
		return "";
	}

}
