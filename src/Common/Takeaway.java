package Common;

import java.util.ArrayList;
import java.util.List;

public class Takeaway {
    private int takeawayId; // شماره سفارش بیرون‌بر
    private int orderId; // شماره سفارش
    private String customerName; // اسم مشتری
    private String phone; // شماره تلفن
    private String address; // آدرس
    private String deliveryTime; // زمان تحویل
    private double deliveryCost; // هزینه ارسال
    private boolean delivered; // وضعیت تحویل

    public Takeaway(int takeawayId, int orderId, String customerName, String phone, String address, String deliveryTime, double deliveryCost) {
        this.takeawayId = takeawayId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.deliveryTime = deliveryTime;
        this.deliveryCost = deliveryCost;
        this.delivered = false; // هنوز تحویل نشده
    }

    // گترها
    public int getTakeawayId() {
        return takeawayId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
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

    // علامت‌گذاری به عنوان تحویل‌شده
    public void markDelivered() {
        this.delivered = true;
    }
}