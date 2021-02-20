package lab1;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javafx.scene.shape.*;

public class Main extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 240);
        scene.setFill(Color.rgb(127, 255, 0));
        
        Rectangle tv = new Rectangle(90, 45, 220, 150);
        root.getChildren().add(tv);
        tv.setFill(Color.rgb(255, 165, 0));
        
        Polyline polyline = new Polyline(165.0, 10.0, 200.0, 45.0, 235.0, 10.0);
        root.getChildren().add(polyline);
        
        Rectangle screen = new Rectangle(105, 60, 145, 120);
        root.getChildren().add(screen);
        screen.setFill(Color.GRAY);
        screen.setArcWidth(25.0);
        screen.setArcHeight(25.0);
        
        Circle btn1 = new Circle(285, 120, 5);
        Circle btn2 = new Circle(285, 145, 5);
        Circle btn3 = new Circle(285, 170, 5);
        root.getChildren().add(btn1);
        root.getChildren().add(btn2);
        root.getChildren().add(btn3);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
