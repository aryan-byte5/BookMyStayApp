import java.util.*;

public class BookMyStayApp {

    static class InvalidBookingException extends Exception {
        InvalidBookingException(String message) {
            super(message);
        }
    }

    static class RoomInventory {

        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        void decrement(String roomType) throws InvalidBookingException {
            if (!inventory.containsKey(roomType)) {
                throw new InvalidBookingException("Invalid Room Type");
            }

            if (inventory.get(roomType) <= 0) {
                throw new InvalidBookingException("Room Not Available");
            }

            inventory.put(roomType, inventory.get(roomType) - 1);
        }

        void display() {
            System.out.println("Current Inventory:");
            for (String key : inventory.keySet()) {
                System.out.println(key + " : " + inventory.get(key));
            }
        }
    }

    static void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Invalid Guest Name");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Invalid Room Type");
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No Availability for Selected Room");
        }
    }

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        Scanner scanner = new Scanner(System.in);

        try {

            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter Room Type: ");
            String roomType = scanner.nextLine();

            validate(guestName, roomType, inventory);

            inventory.decrement(roomType);

            System.out.println("Booking Successful!");

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());

        }

        System.out.println("System Running Safely...");
        inventory.display();

        scanner.close();
    }
}