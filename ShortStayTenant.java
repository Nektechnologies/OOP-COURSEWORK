public class ShortStayTenant extends Tenant {
    private int nights;
    public static final double RATE_PER_NIGHT = 25000.0;

    public ShortStayTenant(String id, String customerName, boolean discountApplies,
                           int nights) throws InvalidTenantException {
        super(id, customerName, discountApplies);
        setNights(nights);
    }

    public int getNights() { return nights; }

    public void setNights(int nights) throws InvalidTenantException {
        if (nights <= 0)
            throw new InvalidTenantException("Nights must be greater than zero.");
        this.nights = nights;
    }

    @Override
    public double calculateCharge() {
        return nights * RATE_PER_NIGHT; // no discount
    }
}