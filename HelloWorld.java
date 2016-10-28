import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class HelloWorld extends Application {
    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Hello World");

        StackPane stack = new StackPane();
        Scene scene = new Scene(stack, 400, 400);
        stack.setStyle("-fx-background-color: #242493;");

        Label label = new Label("Hello World");
        label.setFont(new Font("Impact", 32));
        label.setTextFill(Color.web("#ffffff"));
        label.setTranslateX(-15);

        stack.getChildren().add(label);
        stack.setAlignment(Pos.CENTER_RIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


