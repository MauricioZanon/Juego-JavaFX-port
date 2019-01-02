package text;

import components.ContainerC;
import main.Entity;

public class StringUtils {
	
	private StringUtils() {};
	
	public static String toTitle(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
	}
	
	public static String createItemName(Entity item, ContainerC container) {
		String result = StringUtils.toTitle(item.name);
		int quantity = container.getQuantity(item.name);
		if(quantity > 1) {
			result += (" x" + (quantity));
		}
		return result;
	}
	
}
