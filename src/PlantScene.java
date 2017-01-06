import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;




public class PlantScene {

    Stage window;
    Scene oldScene;
    GridPane upperGrid= new GridPane();
    GridPane lowerGrid= new GridPane();
    BorderPane border = new BorderPane();
    StackPane stackpane = new StackPane();
    TextField wateringIntervalDays = new TextField();
    DatePicker lastWatered = new DatePicker();
    Label result = new Label();



    public PlantScene(Stage window, PlantDictionary dictionary, Plant plant) {
        this.window = window;
        border.setTop(upperGrid);
        border.setCenter(stackpane);
        border.setBottom(lowerGrid);

        upperGrid.setStyle("-fx-background-color: yellowgreen;");
        upperGrid.setPadding(new Insets(20, 10, 10, 10));

        stackpane.setStyle("-fx-background-color: yellowgreen;");
        stackpane.setPadding(new Insets(10, 10, 10, 10));

        lowerGrid.setStyle("-fx-background-color: yellowgreen;");
        lowerGrid.setHgap(25);
        lowerGrid.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(border, 500, 500, Color.YELLOWGREEN);

        Text plantTitle = new Text(plant.getName().toUpperCase());
        plantTitle.setFont(Font.font("Impact", 35));

        TextArea plantDescription = new TextArea(plant.getDescription());
        plantDescription.setWrapText(true);
        plantDescription.setMaxHeight(280);
        plantDescription.setPrefWidth(500);
        plantDescription.setTranslateY(-60);
        plantDescription.setFont(new Font("Arial", 14));
        plantDescription.setStyle("-fx-focus-color: black; -fx-text-box-border: black;");

        Label interval = new Label("Watering interval");
        interval.setTranslateY(117);
        interval.setTranslateX(-187);
        interval.setFont(Font.font("Arial", 13));

        wateringIntervalDays.setTranslateY(113);
        wateringIntervalDays.setMaxWidth(200);
        wateringIntervalDays.setStyle("-fx-border-color: black;");

        Label watered = new Label ("Last watered");
        watered.setTranslateY(157);
        watered.setTranslateX(-173);
        watered.setFont(Font.font("Arial", 13));

        lastWatered.setTranslateY(153);
        lastWatered.setMinWidth(200);
        lastWatered.setStyle("-fx-border-color: black;");

        result.setTranslateX(-60);
        result.setTranslateY(182);
        result.setFont(Font.font("Arial", FontPosture.ITALIC, 10));


        wateringIntervalDays.setPromptText("in days");
        if (plant.getWateringIntervalDays() != 0) {
            wateringIntervalDays.setText(String.valueOf(plant.getWateringIntervalDays()));
        }
        lastWatered.setValue(plant.getLastWatered());


        //Nupud
        Button backButton = new Button("back");
        backButton.setStyle("-fx-font: 14 impact; -fx-background-color: black ; -fx-text-fill: yellowgreen;");

        backButton.setOnAction(e -> goBack());

        Button saveButton = new Button("save");
        saveButton.setStyle("-fx-font: 14 impact; -fx-background-color: black ; -fx-text-fill: yellowgreen;");

        saveButton.setOnAction(event -> {
            try {
                plant.setDescription(plantDescription.getText());
                plant.setLastWatered(lastWatered.getValue());
                plant.setWateringIntervalDays(Integer.parseInt(wateringIntervalDays.getText()));
                dictionary.save(plant);
                goBack();
            }
            catch (Exception e) {
                result.setText("Cannot save");
            }
        });

        Button deleteButton = new Button("delete");
        deleteButton.setStyle("-fx-font: 14 impact; -fx-background-color: black ; -fx-text-fill: yellowgreen;");

        deleteButton.setOnAction(e -> {
            dictionary.delete(plant);
            goBack();
        });


        upperGrid.add(plantTitle, 0, 0);
        stackpane.getChildren().addAll(plantDescription, interval, watered, wateringIntervalDays, lastWatered, result);
        lowerGrid.add(backButton, 11, 0);
        lowerGrid.add(saveButton, 12, 0);
        lowerGrid.add(deleteButton, 13, 0);

        oldScene = window.getScene();
        window.setScene(scene);

        //Et TextArea taust oleks sama, mis scenel
        Region region = (Region) plantDescription.lookup(".content");
        region.setStyle("-fx-background-color: yellowgreen;");

        wateringIntervalDays.textProperty().addListener(e -> updater());
        lastWatered.valueProperty().addListener(e -> updater());
        updater();
    }

    void goBack() {
        window.setScene(oldScene);
    }

    void updater() {
        try {
            // http://stackoverflow.com/questions/27005861/calculate-days-between-two-dates-in-java-8
            long daysSinceLastWatered = ChronoUnit.DAYS.between(lastWatered.getValue(), LocalDate.now());
            int interval = Integer.parseInt(wateringIntervalDays.getText());
            long daysToNextWatering = interval - daysSinceLastWatered;
            result.setText(daysToNextWatering + " days to watering");
        }
        catch (Exception e) {
            result.setText("Cannot calculate");
        }
    }
}
