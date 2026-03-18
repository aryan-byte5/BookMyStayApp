import java.util.*;

class BookMyStayApp {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory {
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

    void decrementRoom(String type) {
        int count = getAvailability(type);
        if (count > 0) {
            inventory.put(type, count - 1);
        }
    }
}

class BookingService {

    private Queue<Reservation> queue;
    private HashMap<String, Set<String>> allocatedRooms;
    private Set<String> allRoomIds;

    BookingService(Queue<Reservation> queue) {
        this.queue = queue;
        allocatedRooms = new HashMap<>();
        allRoomIds = new HashSet<>();
    }

    void processBookings(RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();
            String type = r.roomType;

            if (inventory.getAvailability(type) > 0) {

                String roomId = generateRoomId(type);

                while (allRoomIds.contains(roomId)) {
                    roomId = generateRoomId(type);
                }

                allRoomIds.add(roomId);

                allocatedRooms.putIfAbsent(type, new HashSet<>());
                allocatedRooms.get(type).add(roomId);

                inventory.decrementRoom(type);

                System.out.println("Booking Confirmed for " + r.guestName +
                        " | Room Type: " + type +
                        " | Room ID: " + roomId);
            } else {
                System.out.println("Booking Failed for " + r.guestName +
                        " | Room Type: " + type + " (Not Available)");
            }
        }
    }

    private String generateRoomId(String type) {
        return type.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 2);
        inventory.addRoom("Double Room", 1);
        inventory.addRoom("Suite Room", 1);

        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Double Room"));
        queue.add(new Reservation("Charlie", "Suite Room"));
        queue.add(new Reservation("David", "Single Room"));
        queue.add(new Reservation("Eve", "Single Room"));

        BookingService service = new BookingService(queue);

        System.out.println("===== Processing Bookings =====");

        service.processBookings(inventory);
    }
}