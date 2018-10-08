package time;

import java.util.Calendar;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

public class Clock {
	
	/**
	 * TODO: Hacer otra clase que maneje el clima y agregarle estaciones y estados climáticos
	 * TODO: Test
	 */
	private static Calendar calendar = Calendar.getInstance();
	
	private static float surfaceLightLevel = 1f;
	public static final SimpleStringProperty hourProperty = new SimpleStringProperty();
	
	private Clock() {}
	
	public static void initialize() {
		calendar.clear();
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 45);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.MONTH, 0);
	}
	
	public static String getHour() {
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		return calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d:%02d", minutes, seconds);
	}
	
	public static void advanceTime(int seconds) {
		calendar.add(Calendar.SECOND, seconds);
		recalculateLightLevel();
		Platform.runLater(() -> hourProperty.set(getHour()));
	}
	
	private static void recalculateLightLevel() {
		float minutes = calendar.get(Calendar.MINUTE) + calendar.get(Calendar.HOUR_OF_DAY)*60;
		if(minutes >= 300 && minutes < 480) {
			surfaceLightLevel = (float) (0.00635199f * Math.pow(1.01059529f, minutes));
		}else if(minutes >= 1080 && minutes < 1260) {
			surfaceLightLevel = (float) (2.14833262E+37f * Math.pow(minutes, -12.30691935f));
		}else if(minutes >= 1260 || minutes < 300) {
			surfaceLightLevel = 0.05f;
		}else {
			surfaceLightLevel = 1f;
		}
	}

	public static float getSurfaceLightLevel() {
		return surfaceLightLevel;
	}
	
}