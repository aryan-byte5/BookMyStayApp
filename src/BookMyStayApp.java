import java.util.*;

class BookMyStayApp {
    String name;
    double price;

    Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> serviceMap;

    AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.price;
            }
        }
        return total;
    }

    void displayServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services != null && !services.isEmpty()) {
            for (Service s : services) {
                System.out.println(s.name + " - ₹" + s.price);
            }
        } else {
            System.out.println("No add-on services selected.");
        }
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        String reservationId1 = "RES-101";
        String reservationId2 = "RES-102";

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservationId1, new Service("Breakfast", 500));
        manager.addService(reservationId1, new Service("Airport Pickup", 1200));
        manager.addService(reservationId2, new Service("Extra Bed", 800));

        System.out.println("===== Add-On Services for " + reservationId1 + " =====");
        manager.displayServices(reservationId1);
        System.out.println("Total Cost: ₹" + manager.calculateTotalCost(reservationId1));

        System.out.println("===== Add-On Services for " + reservationId2 + " =====");
        manager.displayServices(reservationId2);
        System.out.println("Total Cost: ₹" + manager.calculateTotalCost(reservationId2));
    }
}