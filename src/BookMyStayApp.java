import java.util.*;

class BookMyStrayApp {
    String reservationId;
    String guestName;
    String roomType;

    Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingHistory {

    private List<Reservation> history;

    BookingHistory() {
        history = new ArrayList<>();
    }

    void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    List<Reservation> getAllBookings() {
        return history;
    }

    void displayReport() {
        System.out.println("===== Booking History Report =====");
        for (Reservation r : history) {
            System.out.println("Reservation ID: " + r.reservationId +
                    " | Guest: " + r.guestName +
                    " | Room Type: " + r.roomType);
        }
        System.out.println("Total Bookings: " + history.size());
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();

        bookingHistory.addReservation(new Reservation("RES-101", "Alice", "Single Room"));
        bookingHistory.addReservation(new Reservation("RES-102", "Bob", "Double Room"));
        bookingHistory.addReservation(new Reservation("RES-103", "Charlie", "Suite Room"));

        bookingHistory.displayReport();
    }
}