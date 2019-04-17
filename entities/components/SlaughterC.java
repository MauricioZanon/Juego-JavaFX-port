package components;
//package components;
//
//import java.util.EnumMap;
//
//public class SlaughterC extends Component{
//	
//	/** Las posibles técnicas y lo que se obtiene de cada una */
//	public EnumMap<SlaughterTechnique, String> techniques = new EnumMap<>(SlaughterTechnique.class);
//	
//	public SlaughterC() {
//		isShared = false;
//	}
//
//	@Override
//	public SlaughterC clone() {
//		SlaughterC c = new SlaughterC();
//		c.techniques.putAll(techniques);
//		return c;
//	}
//
//	@Override
//	public void serialize(StringBuilder sb) {
//		//TODO tiene que guardar los cambios si ya se uso una forma de slaughter
//		sb.append("SLA ");
//	}
//	
//	public enum SlaughterTechnique{
//		BUTCHER(),
//		DRESS_FIELD(),
//		SKIN(),
//	}
//
//}
