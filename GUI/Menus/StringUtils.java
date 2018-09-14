package Menus;

public abstract class StringUtils {
	
	public static String toTitle(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
	}
	
}
