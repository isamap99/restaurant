package Common;

public class Takeaway {
    private int takeawayId;
    private int orderId;
    private Customer customer;
    private String deliveryTime;
    private double deliveryCost;
    private boolean delivered;

    public Takeaway(int takeawayId, int orderId, Customer customer, String deliveryTime, double deliveryCost) {
        this.takeawayId = takeawayId;
        this.orderId = orderId;
        this.customer = customer;
        this.deliveryTime = deliveryTime;
        this.deliveryCost = deliveryCost;
        this.delivered = false;
    }

    public int getTakeawayId() {
        return takeawayId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void markDelivered() {
        this.delivered = true;
    }
}
