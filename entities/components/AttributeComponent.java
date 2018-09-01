//package components;
//
//import java.util.HashMap;
//
//import main.Component;
//
//public class AttributeComponent extends Component{
//
//	//TODO ver si conviene eliminar este component y pasar los atributos a la clase @Entity
//	private HashMap<String, Float> att = new HashMap<>();
//	
//	/**Setea el atributo al valor dado*/
//	public void set(String a, float value) {
//		att.put(a, value);
//	}
//	
//	/**Suma el valor al attributo*/
//	public void change(String a, float value) {
//		if(att.keySet().contains(a)) {
//			att.put(a, att.get(a) + value);
//		}
//	}
//	
//	public float get(String a) {
//		return att.keySet().contains(a) ? att.get(a) : 0;
//	}
//	
//	@Override
//	public AttributeComponent clone() {
//		AttributeComponent comp = new AttributeComponent();
//		att.forEach((k, v) -> comp.set(k, v));
//		return comp;
//	}
//	
//}