//package playerViews;
//
//import RNG.RNG;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//public class WeatherLayer extends Canvas{
//	
//	private static WeatherLayer instance = null;
//	private GraphicsContext gc;
//	
//	public Weather currentWeather = Weather.RAIN;
//	
//	private WeatherLayer() {
//		setHeight(EntitiesLayer.getInstance().getHeight());
//		setWidth(EntitiesLayer.getInstance().getWidth());
//		
//		setMouseTransparent(true);
//		setFocusTraversable(false);
//		
//		gc = getGraphicsContext2D();
//		refresh();
//	}
//	
//	public synchronized void setCurrentWeather(Weather w) {
//		currentWeather = w;
//	}
//	
//	public void refresh() {
//		new Thread(() -> {
//			switch(currentWeather) {
//			case CLEAR_SKY:
//				gc.clearRect(0, 0, getWidth(), getHeight());
//				break;
//			case RAIN:
//				renderRain();
//				break;
//			case SNOW:
//				renderSnow();
//				break;
//			default:
//				break;
//			
//			}
//		}).start();
//	}
//	
//	private void renderRain() {
//		float thunderIntensity = 0;
//		gc.setStroke(Color.CADETBLUE);
//		gc.setLineWidth(2);
//		
//		while(currentWeather == Weather.RAIN) {
//			gc.clearRect(0, 0, getWidth(), getHeight());
//
//			gc.setGlobalAlpha(0.2);
//			gc.setFill(Color.BLACK);
//			gc.fillRect(0, 0, getWidth(), getHeight());
//			
//			gc.setGlobalAlpha(0.8);
//			int quantity = RNG.nextInt(100, 200); // de aca se maneja la cantidad de lluvia
//			for(int i = 0; i < quantity; i++) {
//				int x = RNG.nextInt((int) getWidth());
//				int y = RNG.nextInt((int) getHeight());
//				gc.strokeLine(x, y, x-2, y+10); // con la modificacion de la x se le da el ángulo a la lluvia
//			}
//
//			if(RNG.nextInt(100) < 1) { // cambiando esto se maneja la frecuencia de los truenos
//				thunderIntensity = RNG.nextFloat();
//			}
//			if(thunderIntensity > 0) {
//				gc.setGlobalAlpha(thunderIntensity);
//				gc.setFill(Color.WHITE);
//				gc.fillRect(0, 0, getWidth(), getHeight());
//				thunderIntensity -= 0.05;
//			}
//			try {
//				Thread.sleep(75); // con este valor se puede cambiar la velocidad a la que cae la lluvia
//			} catch (InterruptedException e) {}
//		}
//	}
//	
//	private void renderSnow() {
//		while(currentWeather == Weather.SNOW) {
//			gc.clearRect(0, 0, getWidth(), getHeight());
//
//			gc.setGlobalAlpha(0.2);
//			gc.setFill(Color.BLACK);
//			gc.fillRect(0, 0, getWidth(), getHeight());
//			
//			gc.setFill(Color.WHITE);
//			gc.setGlobalAlpha(0.8);
//			
//			int quantity = RNG.nextInt(100, 200); // de aca se maneja la cantidad de nieve
//			for(int i = 0; i < quantity; i++) {
//				int x = RNG.nextInt((int) getWidth());
//				int y = RNG.nextInt((int) getHeight());
//				
//				gc.fillOval(x, y, 4, 4);
//			}
//			try {
//				Thread.sleep(300); // con este valor se puede cambiar la velocidad a la que cae la nieve
//			} catch (InterruptedException e) {}
//		}
//	}
//	
//	public static WeatherLayer getInstance() {
//		if(instance == null) {
//			instance = new WeatherLayer();
//		}
//		return instance;
//	}
//	
//	public enum Weather{
//		RAIN,
//		SNOW,
//		CLEAR_SKY,
//	}
//
//}
