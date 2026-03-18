import java.util.HashMap;

class BookMyStayApp {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
    }

    void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    void updateAvailability(String type, int count) {
        inventory.put(type, count);
    }

    void displayInventory() {
        for (String type : inventory.keySet()) {
            System.out.println(type + " Available: " + inventory.get(type));
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        String version = "v3.1";

        RoomInventory inventory = new RoomInventory();

        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 3);
        inventory.addRoom("Suite Room", 2);

        System.out.println("===== Book My Stay - Hotel Booking System =====");
        System.out.println("Version: " + version);
        System.out.println("===============================================");

        inventory.displayInventory();

        System.out.println("---- After Update ----");

        inventory.updateAvailability("Single Room", 4);

        inventory.displayInventory();
    }
}