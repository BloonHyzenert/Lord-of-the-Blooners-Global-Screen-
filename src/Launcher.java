
import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {
		new Setup();
		new Thread(new Frame()).start();
		new Server();
		Application.launch(Display.class, args);
		
	}
}
