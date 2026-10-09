public class Main {
    private static Manager manager = new Manager();
    private static int idCounter = 1;
    private static final String ID_PREFIX = "EVE560";
    private static final double DISCOUNT_PERCENT = 11.0;

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Hostel Charging System");
        System.out.println(" Name: MWESIGWA NEKAMIA");
        System.out.println(" Reg : VU-BIT-2511-0560-EVE");
        System.out.println(" Discount: 11% | Sort: Charge (highest first)");
        System.out.println("=====================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Resident Tenant");
            System.out.println("2. Add Short-Stay Tenant");
            System.out.println("3. Show Report (sorted)");
            System.out.println("4. Find Tenant by ID");
            System.out.println("5. Remove Tenant");
            System.out.println("6. Summary");
            System.out.println("7. Exit");

            int choice = InputHelper.readPositiveInt("Choose option: ");

            switch (choice) {
                case 1: addResident(); break;
                case 2: addShortStay(); break;
                case 3: showReport(); break;
                case 4: findTenant(); break;
                case 5: removeTenant(); break;
                case 6: showSummary(); break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("  ⚠ Please choose 1-7.");
            }
        }
    }

    private static void addResident() {
        try {
            String name = InputHelper.readNonEmptyString("Customer name: ");
            int months = InputHelper.readPositiveInt("Months paid: ");
            boolean discountApplies = InputHelper.readYesNo("Apply discount?");
            String id = nextId();
            ResidentTenant r = new ResidentTenant(id, name, discountApplies, months);
            manager.addRecord(r);
            System.out.println("  Added: " + id + " | Charge: UGX " + format(r.calculateCharge()));
        } catch (InvalidTenantException e) {
            System.out.println("   Error: " + e.getMessage());
        }
    }

    private static void addShortStay() {
        try {
            String name = InputHelper.readNonEmptyString("Customer name: ");
            int nights = InputHelper.readPositiveInt("Number of nights: ");
            String id = nextId();
            ShortStayTenant s = new ShortStayTenant(id, name, false, nights);
            manager.addRecord(s);
            System.out.println("   Added: " + id + " | Charge: UGX " + format(s.calculateCharge()));
        } catch (InvalidTenantException e) {
            System.out.println("   Error: " + e.getMessage());
        }
    }

    private static void showReport() {
        if (manager.getRecords().isEmpty()) {
            System.out.println("  (No records yet)");
            return;
        }
        System.out.println("\n--- REPORT (sorted by charge, highest first) ---");
        System.out.printf("%-12s %-20s %-16s %-14s%n", "ID", "Name", "Type", "Charge");
        for (Tenant t : manager.getSortedReport()) {
            String type = (t instanceof ResidentTenant) ? "Resident" : "ShortStay";
            System.out.printf("%-12s %-20s %-16s UGX %-10s%n",
                    t.getId(), t.getCustomerName(), type, format(t.calculateCharge()));
        }
    }

    private static void findTenant() {
        String id = InputHelper.readNonEmptyString("Enter tenant ID: ");
        Tenant t = manager.findById(id);
        if (t == null) {
            System.out.println("   Not found.");
            return;
        }
        System.out.println("  Found: " + t.getId() + " | " + t.getCustomerName()
                + " | Charge: UGX " + format(t.calculateCharge()));
    }

    private static void removeTenant() {
        String id = InputHelper.readNonEmptyString("Enter tenant ID to remove: ");
        Tenant t = manager.findById(id);
        if (t == null) {
            System.out.println("   Not found.");
            return;
        }
        System.out.println("  Found: " + t.getCustomerName() + " (" + t.getId() + ")");
        if (InputHelper.readYesNo("Confirm removal?")) {
            manager.removeRecord(id);
            System.out.println("   Removed.");
        } else {
            System.out.println("  Cancelled.");
        }
    }

    private static void showSummary() {
        System.out.println("\n--- SUMMARY ---");
        System.out.println("Total records      : " + manager.getRecords().size());
        System.out.println("Total charges      : UGX " + format(manager.getTotalCharges()));
        System.out.println("Total discount given: UGX " + format(manager.getTotalDiscount()));
    }

    private static String nextId() {
        return String.format("%s-%03d", ID_PREFIX, idCounter++);
    }

    private static String format(double amount) {
        return String.format("%,.0f", amount);
    }
}