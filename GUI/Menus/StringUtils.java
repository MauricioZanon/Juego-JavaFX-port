package menus;

import components.ContainerComponent;
import main.Entity;

public class StringUtils {
	
	private StringUtils() {};
	
	public static String toTitle(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
	}
	
	public static String createItemName(Entity item, ContainerComponent container) {
		String result = StringUtils.toTitle(item.name);
		int quantity = container.getQuantity(item.name);
		if(quantity > 1) {
			result += (" x" + (quantity));
		}
		return result;
	}
	
}
