import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager {
    private ArrayList<Tenant> records = new ArrayList<>();

    public void addRecord(Tenant tenant) {
        records.add(tenant);
    }

    public Tenant findById(String id) {
        for (Tenant t : records) {
            if (t.getId().equalsIgnoreCase(id)) return t;
        }
        return null;
    }

    public boolean removeRecord(String id) {
        Tenant found = findById(id);
        if (found != null) {
            records.remove(found);
            return true;
        }
        return false;
    }

    public double getTotalCharges() {
        double total = 0;
        for (Tenant t : records) {
            total += t.calculateCharge();
        }
        return total;
    }

    public double getTotalDiscount() {
        double discountTotal = 0;
        for (Tenant t : records) {
            if (t instanceof ResidentTenant) {
                ResidentTenant r = (ResidentTenant) t;
                double raw = r.getMonthsPaid() * ResidentTenant.RATE_PER_MONTH;
                double actual = r.calculateCharge();
                discountTotal += (raw - actual);
            }
        }
        return discountTotal;
    }

    public List<Tenant> getSortedReport() {
        ArrayList<Tenant> copy = new ArrayList<>(records);
        copy.sort(Comparator.comparingDouble(Tenant::calculateCharge).reversed());
        return copy;
    }

    public ArrayList<Tenant> getRecords() {
        return records;
    }
}