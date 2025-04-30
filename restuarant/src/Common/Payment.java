package Common;

public class Payment {
    private int orderId;
    private Customer customer;     // استفاده مستقیم از Customer
    private String tableNumber;
    private double amount;
    private boolean isPaid;

    public Payment(int orderId, Customer customer, String tableNumber, double amount) {
        this.orderId = orderId;
        this.customer = customer;
        this.tableNumber = tableNumber;
        this.amount = amount;
        this.isPaid = false; // پیش‌فرض: پرداخت‌نشده
    }

    // متد برای علامت‌گذاری به عنوان پرداخت‌شده
    public void markAsPaid() {
        isPaid = true;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    @Override
    public String toString() {
        return "سفارش شماره " + orderId +
               " | مشتری: " + customer.getName() + " " + customer.getFamily() +
               " | میز: " + tableNumber +
               " | مبلغ: " + amount + " تومان" +
               " | وضعیت: " + (isPaid ? "پرداخت شده" : "پرداخت نشده");
    }
}
