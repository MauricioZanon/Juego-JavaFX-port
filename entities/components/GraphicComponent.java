package components;

import javafx.scene.paint.Color;
import main.Component;

/** Este component tiene el ascii y el color de las entidades*/
public class GraphicComponent extends Component{
	
	public String ASCII;
	public Color color;
	
	@Override
	public GraphicComponent clone() {
		GraphicComponent c = new GraphicComponent();
		c.ASCII = ASCII;
		c.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
		return c;
	}
	
	
}
