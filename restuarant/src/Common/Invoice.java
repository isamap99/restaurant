package Common;

public class Invoice {
    private int invoiceId;
    private Order order;
    private double totalAmount;
    private boolean isPaid;

    public Invoice(int invoiceId, Order order) {
        this.invoiceId = invoiceId;
        this.order = order;
        this.totalAmount = order.getTotalPrice();
        this.isPaid = false; // Initially, invoice is not paid
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void printInvoiceDetails() {
        System.out.println("=== Invoice #" + invoiceId + " ===");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Paid Status: " + (isPaid ? "Paid" : "Unpaid"));
        System.out.println("Order Items:");
        for (Order.OrderItem item : order.getItems()) {
            System.out.printf("- %s: %d x %.0f = %.0f%n",
                    item.getName(), item.getQuantity(), item.getUnitPrice(),
                    item.getQuantity() * item.getUnitPrice());
        }
    }
}
