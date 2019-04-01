
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends Application{
	
	private Stage primaryStage;
	private Group root;

	@Override
	public void start(Stage stage) {
		this.primaryStage=stage;
		
        primaryStage.setTitle("Lord of the Blooners");
        root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.GRAY);    
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
            	switch (t.getCode()) {
				case ESCAPE :
					Configuration.END=true;
		               primaryStage.close();
		               System.exit(0);
		               
					break;

				default:
					break;
				}
            }
        });
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());

        board();
	
        
        for (int i = 0; i < Setup.getKrok().size(); i++) {
			microbe(Setup.getKrok(),i);
		
		}
        for (int i = 0; i < Setup.getBlurp().size(); i++) {
			microbe(Setup.getBlurp(),i);
		
		}
        for (int i = 0; i < Setup.getGrounch().size(); i++) {
			microbe(Setup.getGrounch(),i);
		
		}
        
        primaryStage.show();
	}

	private void board() {

        Circle board = new Circle();

        board.setCenterX(primaryStage.getWidth()/2);
        board.setCenterY(primaryStage.getHeight()/2);
        board.setRadius(primaryStage.getHeight()/2-10);
        board.setFill(Color.BLACK);
        board.setStroke(Color.RED);
        board.setStrokeWidth(1);	
        
        TranslateTransition translateTransition = new TranslateTransition(); 
        translateTransition.setDuration(Duration.millis(1000)); 
        
        translateTransition.setNode(board);  
        translateTransition.setByX(500); 
        translateTransition.setCycleCount(50); 
        translateTransition.setAutoReverse(false);  
        translateTransition.play(); 
        
        root.getChildren().add(board);
		
	}
	private void microbe( Team team ,int i) {
        Circle pion = new Circle();

        pion.setCenterX(team.getPlayerList().get(i).getPosition().getX());
        pion.setCenterY(team.getPlayerList().get(i).getPosition().getY());
        pion.setRadius(Configuration.PIONSIZE);
        switch (team.getColor()) {
		case "red":
	        pion.setFill(Color.RED);
			break;
		case "yellow":
	        pion.setFill(Color.YELLOW);
			break;
		case "blue":
	        pion.setFill(Color.BLUE);
			break;

		default:
			break;
		}
        pion.setStroke(Color.BLACK);
        
        pion.setStrokeWidth(0);	
        
        TranslateTransition translateTransition = new TranslateTransition(); 
        translateTransition.setDuration(Duration.millis(1000)); 
        
        translateTransition.setNode(pion);  
        translateTransition.setByX(1); 
        translateTransition.setCycleCount(50); 
        translateTransition.setAutoReverse(false);  
        translateTransition.play(); 
        
        root.getChildren().add(pion);
		
	}

}
