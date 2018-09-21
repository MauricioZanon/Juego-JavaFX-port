package system;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RenderSystem {
	
	private static Stage primaryStage;
	Parent root;
	
	public static void initialize(Stage ps) {
		primaryStage = ps;
	}
	
	public static void changeScene(Scene scene) {
		primaryStage.setScene(scene);
	}
	
	public static void render() {
		primaryStage.show();
	}
	
	public static double getSceneHeight() {
		return primaryStage.getScene().getHeight();
	}

	public static double getSceneWidth() {
		return primaryStage.getScene().getWidth();
	}

}
