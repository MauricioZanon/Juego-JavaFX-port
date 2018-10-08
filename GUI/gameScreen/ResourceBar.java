package gameScreen;

import java.util.function.Supplier;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ResourceBar extends HBox{
	
	private Supplier<Float> getPercentage;
	private String barColor;
	private String backColor;
	private Label text;
	
	public ResourceBar(String resourceName, Color color, Supplier<Float> getResourceFunction) {
		getPercentage = getResourceFunction;
		text = new Label(resourceName);
		getChildren().add(text);
		
		Tooltip tt = new Tooltip("player health");
		text.setTooltip(tt);
		
		barColor = color.toString().replaceAll("0x", "#");
		backColor = color.darker().toString().replaceAll("0x", "#");
		setWidth(100);
		
		setOnMouseEntered(e -> text.setText(getPercentage.get().intValue() + "%"));
		setOnMouseExited(e -> text.setText(resourceName));
		
		refresh();
	}
	
	//TODO se debe actualizar el porcentaje cuando el mouse está encima
	public void refresh() {
		float barPercentage = getPercentage.get();
		setStyle("-fx-background-color: linear-gradient(to right," + barColor + " " + barPercentage +"%, " + backColor + " " + barPercentage +"%)");
	}
}
