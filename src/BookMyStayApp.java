import java.io.*;
import java.util.*;

public class BookMyStayApp implements Serializable {

    private static final long serialVersionUID = 1L;

    static class RoomInventory implements Serializable {

        private static final long serialVersionUID = 1L;
        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
        }

        void decrement(String roomType) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }

        void display() {
            System.out.println("Current Inventory:");
            for (String key : inventory.keySet()) {
                System.out.println(key + " : " + inventory.get(key));
            }
        }
    }

    private static final String FILE_NAME = "bookmystay_data.ser";

    static void saveState(RoomInventory inventory) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            out.writeObject(inventory);
            System.out.println("System State Saved Successfully.");
        } catch (IOException e) {
            System.out.println("Error Saving State.");
        }
    }

    static RoomInventory loadState() {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {
            System.out.println("System State Restored Successfully.");
            return (RoomInventory) in.readObject();
        } catch (Exception e) {
            System.out.println("No Previous State Found. Starting Fresh.");
            return new RoomInventory();
        }
    }

    public static void main(String[] args) {

        RoomInventory inventory = loadState();

        System.out.println("===== System Running =====");
        inventory.display();

        if (inventory.inventory.get("Single Room") > 0) {
            inventory.decrement("Single Room");
            System.out.println("Single Room Booked.");
        }

        saveState(inventory);

        System.out.println("===== Final State =====");
        inventory.display();
    }
}