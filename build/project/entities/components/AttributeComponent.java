package components;

import java.util.HashMap;

import main.Component;

public class AttributeComponent extends Component{
	
	/**
	 * TODO: unir esta clase con DescriptionComponent asi toda la informacion de las entidades esta en una misma clase
	 */
	
	private HashMap<String, Float> att = new HashMap<>();
	
	public void set(String a, float value) {
		att.put(a, value);
	}
	
	/**Suma el valor al attributo*/
	public void change(String a, float value) {
		if(att.keySet().contains(a)) {
			att.put(a, att.get(a) + value);
		}
	}
	
	public float get(String a) {
		return att.keySet().contains(a) ? att.get(a) : 0;
	}
	
	@Override
	public AttributeComponent clone() {
		AttributeComponent comp = new AttributeComponent();
		att.forEach((k, v) -> comp.set(k, v));
		return comp;
	}
	
}