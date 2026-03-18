import java.util.*;

public class BookMyStayApp {

    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
        }

        public synchronized boolean allocateRoom(String roomType) {
            int available = inventory.getOrDefault(roomType, 0);

            if (available > 0) {
                inventory.put(roomType, available - 1);
                return true;
            }
            return false;
        }

        public void display() {
            System.out.println("Final Inventory:");
            for (String key : inventory.keySet()) {
                System.out.println(key + " : " + inventory.get(key));
            }
        }
    }

    static class BookingProcessor implements Runnable {

        private RoomInventory inventory;
        private String guestName;
        private String roomType;

        BookingProcessor(RoomInventory inventory, String guestName, String roomType) {
            this.inventory = inventory;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public void run() {
            boolean success = inventory.allocateRoom(roomType);

            if (success) {
                System.out.println("Booking Successful for " + guestName);
            } else {
                System.out.println("Booking Failed for " + guestName);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();

        Thread t1 = new Thread(new BookingProcessor(inventory, "Alice", "Single Room"));
        Thread t2 = new Thread(new BookingProcessor(inventory, "Bob", "Single Room"));
        Thread t3 = new Thread(new BookingProcessor(inventory, "Charlie", "Single Room"));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("===== All Threads Completed =====");
        inventory.display();
    }
}