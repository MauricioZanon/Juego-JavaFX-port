package text;

public class StringUtils {
	
	private StringUtils() {};
	
	/** Hace que la primer letra del String sea mayúscula */
	public static String toTitle(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
	}
	
	/** Devuelve un String con el nombre dado y la cantidad (si es mayor a 1) */
	public static String createItemName(String name, int quantity) {
		String result = StringUtils.toTitle(name);
		if(quantity > 1) {
			result += (" x" + (quantity));
		}
		return result;
	}
	
}
