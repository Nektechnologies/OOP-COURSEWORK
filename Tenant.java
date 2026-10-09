public abstract class Tenant implements Chargeable {
    private String id;
    private String customerName;
    private boolean discountApplies;

    public Tenant(String id, String customerName, boolean discountApplies)
            throws InvalidTenantException {
        setId(id);
        setCustomerName(customerName);
        this.discountApplies = discountApplies;
    }

    public String getId() { return id; }

    public void setId(String id) throws InvalidTenantException {
        if (id == null || id.trim().isEmpty())
            throw new InvalidTenantException("ID cannot be blank.");
        this.id = id;
    }

    public String getCustomerName() { return customerName; }

    public void setCustomerName(String customerName) throws InvalidTenantException {
        if (customerName == null || customerName.trim().isEmpty())
            throw new InvalidTenantException("Customer name cannot be blank.");
        this.customerName = customerName;
    }

    public boolean isDiscountApplies() { return discountApplies; }

    public void setDiscountApplies(boolean discountApplies) {
        this.discountApplies = discountApplies;
    }

    @Override
    public abstract double calculateCharge();
}