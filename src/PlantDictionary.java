import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;


public class PlantDictionary {

    private Map<String, Plant> plants;

    public PlantDictionary() {
        readDatabase();
    }

    public Plant find(String name) {
        return plants.get(name.toLowerCase());
    }

    public void save(Plant plant) {
        plants.put(plant.getName().toLowerCase(), plant);
        saveDatabase();
    }

    public void delete(Plant plant) {
        plants.remove(plant.getName().toLowerCase());
        saveDatabase();
    }

    //Salvestan kõik taimed faili
    //http://avajava.com/tutorials/lessons/how-do-i-write-an-object-to-a-file-and-read-it-back.html
    void saveDatabase() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("plants.db"));
            os.writeObject(plants);
            os.flush();
            os.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Loen kõik taimed failist mällu
    void readDatabase() {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("plants.db"));
            plants = (Map<String, Plant>) is.readObject();
            is.close();
        }
        catch (Exception e) {
            plants = new HashMap<>();
        }
    }
}
