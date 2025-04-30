package Manager;

import Common.Customer;
import Common.Order;
import Common.Takeaway;

import java.util.ArrayList;
import java.util.List;

public class TakeawayManager {
    private List<Takeaway> takeaways;
    private OrderManager orderManager;
    private int nextTakeawayId;

    public TakeawayManager(OrderManager orderManager) {
        this.orderManager = orderManager;
        this.takeaways = new ArrayList<>();
        this.nextTakeawayId = 1;
    }

    public void addTakeaway(int orderId, Customer customer, String deliveryTime, double distanceKm) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("سفارش شماره " + orderId + " پیدا نشد!");
            return;
        }

        if (customer == null || deliveryTime.trim().isEmpty()) {
            System.out.println("لطفاً اطلاعات مشتری و زمان تحویل را کامل وارد کنید.");
            return;
        }

        if (distanceKm < 0) {
            System.out.println("فاصله نمی‌تونه منفی باشه!");
            return;
        }

        double deliveryCost = distanceKm * 5.0;
        Takeaway takeaway = new Takeaway(nextTakeawayId++, orderId, customer, deliveryTime, deliveryCost);
        takeaways.add(takeaway);
        System.out.println("سفارش بیرون‌بر شماره " + takeaway.getTakeawayId() + " ثبت شد.");
    }

    public void markAsDelivered(int takeawayId) {
        Takeaway takeaway = findTakeaway(takeawayId);
        if (takeaway == null) {
            System.out.println("سفارش بیرون‌بر شماره " + takeawayId + " پیدا نشد!");
            return;
        }

        if (takeaway.isDelivered()) {
            System.out.println("این سفارش قبلاً تحویل شده!");
            return;
        }

        takeaway.markDelivered();
        System.out.println("سفارش شماره " + takeawayId + " تحویل شد.");
    }

    public void showTakeaways() {
        if (takeaways.isEmpty()) {
            System.out.println("هیچ سفارش بیرون‌بری ثبت نشده!");
            return;
        }

        System.out.println("=== لیست سفارش‌های بیرون‌بر ===");
        for (Takeaway takeaway : takeaways) {
            Customer c = takeaway.getCustomer();
            System.out.println("شماره بیرون‌بر: " + takeaway.getTakeawayId());
            System.out.println("شماره سفارش: " + takeaway.getOrderId());
            System.out.println("مشتری: " + c.getName() + " " + c.getFamily());
            System.out.println("تلفن: " + c.getPhone());
            System.out.println("آدرس: " + c.getAddress());
            System.out.println("زمان تحویل: " + takeaway.getDeliveryTime());
            System.out.println("هزینه ارسال: " + takeaway.getDeliveryCost() + " تومن");
            System.out.println("وضعیت: " + (takeaway.isDelivered() ? "تحویل‌شده" : "در انتظار تحویل"));
            System.out.println("-------------------");
        }
    }

    public void printReceipt(int takeawayId) {
        Takeaway takeaway = findTakeaway(takeawayId);
        if (takeaway == null) {
            System.out.println("سفارش بیرون‌بر شماره " + takeawayId + " پیدا نشد!");
            return;
        }

        Order order = findOrder(takeaway.getOrderId());
        if (order == null) {
            System.out.println("سفارش شماره " + takeaway.getOrderId() + " پیدا نشد!");
            return;
        }
        Customer c = takeaway.getCustomer();
        System.out.println("===== فاکتور سفارش بیرون‌بر =====");
        System.out.println("شماره بیرون‌بر: " + takeaway.getTakeawayId());
        System.out.println("نام مشتری: " + c.getName() + " " + c.getFamily());
        System.out.println("تلفن: " + c.getPhone());
        System.out.println("آدرس: " + c.getAddress());
        System.out.println("زمان تحویل: " + takeaway.getDeliveryTime());
        System.out.println("-------------------");
        System.out.println("آیتم‌های سفارش:");
        for (Order.OrderItem item : order.getItems()) {
            System.out.printf("- %s: %d عدد (%.0f تومن هر واحد، جمع: %.0f تومن)%n",
                    item.getName(), item.getQuantity(), item.getUnitPrice(), item.getUnitPrice() * item.getQuantity());
        }
        System.out.println("-------------------");
        System.out.printf("تخفیف: %.0f%%%n", order.getDiscount());
        System.out.printf("هزینه ارسال: %.0f تومن%n", takeaway.getDeliveryCost());
        System.out.printf("مجموع کل: %.0f تومن%n", order.getTotalPrice() + takeaway.getDeliveryCost());
        System.out.println("=============================");
    }

    private Takeaway findTakeaway(int takeawayId) {
        for (Takeaway takeaway : takeaways) {
            if (takeaway.getTakeawayId() == takeawayId) {
                return takeaway;
            }
        }
        return null;
    }

    private Order findOrder(int orderId) {
        for (Order order : orderManager.getOrders()) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public List<Order> getOrders() {
        List<Order> allOrders = new ArrayList<>();
        for (Takeaway takeaway : takeaways) {
            Order order = findOrder(takeaway.getOrderId());
            if (order != null) {
                allOrders.add(order);
            }
        }
        return allOrders;
    }
}