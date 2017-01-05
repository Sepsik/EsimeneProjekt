import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class PlantScene {

    Scene oldScene;
    GridPane layout = new GridPane();
    Stage window;
    TextField wateringIntervalDays = new TextField();
    DatePicker lastWatered = new DatePicker();
    Label result = new Label();


    public PlantScene(Stage window, PlantDictionary dictionary, Plant plant) {
        this.window = window;
        layout.setHgap(50);
        layout.setVgap(10);
        layout.setPadding(new Insets(35, 10, 10, 10));
        layout.setStyle("-fx-background-color: transparent;");

        Scene scene = new Scene(layout, 500, 500, Color.YELLOWGREEN);

        Text plantTitle = new Text(plant.getName().toUpperCase());
        plantTitle.setFont(Font.font("Impact", 35));

        TextArea plantDescription = new TextArea(plant.getDescription());
        plantDescription.setWrapText(true);
        plantDescription.setPrefHeight(300);
        plantDescription.setPrefWidth(500);
        plantDescription.setFont(new Font("Arial", 14));
        plantDescription.setStyle("-fx-focus-color: black; -fx-text-box-border: black;");

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

        layout.add(plantTitle, 0, 0);
        layout.add(plantDescription, 0, 1, 4, 1);
        layout.add(backButton, 0, 5);
        layout.add(saveButton, 1, 5);
        layout.add(deleteButton, 2, 5);

        layout.add(new Label("Interval"), 0, 2);
        layout.add(wateringIntervalDays, 1, 2);



        layout.add(new Label("Last watered"), 0, 3);
        layout.add(lastWatered, 1, 3);

        layout.add(result, 0, 4);


        layout.setGridLinesVisible(false);
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
