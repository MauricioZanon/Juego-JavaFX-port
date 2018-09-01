package gameScreen;

import application.Main;
import components.PositionComponent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class PlayerPosLabel extends Label{
	
	private static PlayerPosLabel instance = new PlayerPosLabel();
	
	private PlayerPosLabel() {
		setText(getPlayerPos());
		setTextFill(Color.WHITE);
		this.setOnMouseEntered(e -> {
			setTextFill(Color.RED);
		});
		setOnMouseExited(e -> {
			setTextFill(Color.WHITE);
		});
	}
	
	public static PlayerPosLabel getInstance() {
		return instance;
	}
	
	public void refresh() {
		setText(getPlayerPos());
	}
	
	private String getPlayerPos() {
		return Main.player.get(PositionComponent.class).toString();
	}

}
