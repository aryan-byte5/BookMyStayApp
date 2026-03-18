import java.util.*;

public class BookMyStayApp {

    static class Reservation {
        String reservationId;
        String guestName;
        String roomType;
        String roomId;
        boolean active;

        Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
            this.active = true;
        }
    }

    static class RoomInventory {

        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        void decrement(String roomType) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }

        void increment(String roomType) {
            inventory.put(roomType, inventory.get(roomType) + 1);
        }

        void display() {
            System.out.println("Current Inventory:");
            for (String key : inventory.keySet()) {
                System.out.println(key + " : " + inventory.get(key));
            }
        }
    }

    static class CancellationService {

        private Stack<String> rollbackStack = new Stack<>();
        private Map<String, Reservation> reservationMap;
        private RoomInventory inventory;

        CancellationService(Map<String, Reservation> reservationMap, RoomInventory inventory) {
            this.reservationMap = reservationMap;
            this.inventory = inventory;
        }

        void cancel(String reservationId) {

            if (!reservationMap.containsKey(reservationId)) {
                System.out.println("Cancellation Failed: Reservation Not Found");
                return;
            }

            Reservation r = reservationMap.get(reservationId);

            if (!r.active) {
                System.out.println("Cancellation Failed: Already Cancelled");
                return;
            }

            rollbackStack.push(r.roomId);

            inventory.increment(r.roomType);

            r.active = false;

            System.out.println("Cancellation Successful for " + r.guestName);
        }

        void showRollback() {
            System.out.println("Rollback Stack (LIFO):");
            while (!rollbackStack.isEmpty()) {
                System.out.println(rollbackStack.pop());
            }
        }
    }

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        Map<String, Reservation> reservationMap = new HashMap<>();

        Reservation r1 = new Reservation("RES-1", "Alice", "Single Room", "SI-101");
        Reservation r2 = new Reservation("RES-2", "Bob", "Double Room", "DO-202");

        reservationMap.put(r1.reservationId, r1);
        reservationMap.put(r2.reservationId, r2);

        inventory.decrement("Single Room");
        inventory.decrement("Double Room");

        CancellationService service =
                new CancellationService(reservationMap, inventory);

        System.out.println("===== Before Cancellation =====");
        inventory.display();

        service.cancel("RES-1");

        System.out.println("===== After Cancellation =====");
        inventory.display();

        service.showRollback();
    }
}