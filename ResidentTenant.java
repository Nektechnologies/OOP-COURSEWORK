public class ResidentTenant extends Tenant {
    private int monthsPaid;
    public static final double RATE_PER_MONTH = 450000.0;

    public ResidentTenant(String id, String customerName, boolean discountApplies,
                          int monthsPaid) throws InvalidTenantException {
        super(id, customerName, discountApplies);
        setMonthsPaid(monthsPaid);
    }

    public int getMonthsPaid() { return monthsPaid; }

    public void setMonthsPaid(int monthsPaid) throws InvalidTenantException {
        if (monthsPaid <= 0)
            throw new InvalidTenantException("Months paid must be greater than zero.");
        this.monthsPaid = monthsPaid;
    }

    @Override
    public double calculateCharge() {
        double charge = monthsPaid * RATE_PER_MONTH;
        if (isDiscountApplies() && monthsPaid >= 4) {
            charge = charge * 0.89; // 11% discount
        }
        return charge;
    }
}