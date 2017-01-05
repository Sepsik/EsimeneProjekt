import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class NoDeadPlants extends Application {

    Stage window;
    Image logo = new Image("file:NoDeadPlants.png");
    PlantDictionary dictionary = new PlantDictionary();


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

            window = primaryStage;
            window.setTitle("NoDeadPlants");

            StackPane layout = new StackPane();
            layout.setStyle("-fx-background-color: transparent;");

            TextField searchInput = new TextField();
            searchInput.setMaxWidth(280);
            searchInput.setPromptText("name a plant");
            searchInput.setFocusTraversable(false);


            searchInput.setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    String plantName = searchInput.getText();

                    Plant plant = dictionary.find(plantName);
                    if (plant == null) {
                        plant = new Plant();
                        plant.setName(plantName);
                    }
                    new PlantScene(window, dictionary, plant);


                }
            });


            ImageView logoIV = new ImageView();
            logoIV.setImage(logo);
            logoIV.setTranslateY(-50);

            layout.getChildren().addAll(searchInput, logoIV);


            Scene scene = new Scene(layout, 500, 500, Color.YELLOWGREEN);


            window.setScene(scene);
            window.show();


    }


}
