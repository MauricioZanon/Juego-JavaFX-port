package gameScreen;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import time.Clock;

public class ClockLabel extends Label{
	
	private static ClockLabel instance = new ClockLabel();
	
	private ClockLabel() {
		setText(Clock.getHour());
		setTextFill(Color.WHITE);
		this.setOnMouseEntered(e -> {
			setTextFill(Color.RED);
		});
		setOnMouseExited(e -> {
			setTextFill(Color.WHITE);
		});
	}
	
	public static ClockLabel getInstance() {
		return instance;
	}
	
	public void refresh() {
		setText(Clock.getHour());
	}
	
}