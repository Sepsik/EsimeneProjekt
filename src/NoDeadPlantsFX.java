import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class NoDeadPlantsFX extends Application {

    private StackPane layout = new StackPane();
    private Image logo = new Image("file:NoDeadPlants.png");
    private ImageView logoIV = new ImageView();
    private PlantDictionary dictionary = new PlantDictionary();
    private TextField searchInput = new TextField();


    @Override
    public void start(Stage window) throws Exception {


        window.setTitle("NoDeadPlants");

        layout.setStyle("-fx-background-color: transparent;");

        searchInput.setMaxWidth(280);
        searchInput.setStyle("-fx-border-color: black;");
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


        logoIV.setImage(logo);
        logoIV.setTranslateY(-50);

        layout.getChildren().addAll(searchInput, logoIV);


        Scene scene = new Scene(layout, 500, 500, Color.YELLOWGREEN);


        window.setScene(scene);
        window.show();


    }


}
