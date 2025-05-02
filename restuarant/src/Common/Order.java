package Common;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<OrderItem> items;
    private double discount;
    private Customer customer;
    private String tableNumber;
    private boolean isPaid;

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.discount = 0;
    }

    public Order(int orderId, Customer customer) {
        this(orderId);
        this.customer = customer;
    }

    public Order(int orderId, Customer customer, String tableNumber) {
        this(orderId, customer);
        this.tableNumber = tableNumber;
    }

    // متد جدید و کامل‌شده
    public void addItem(Object item, int quantity) {
        String name;
        double price;

        if (item instanceof Menu.Food food) {
            name = food.name;
            price = food.price;
        } else if (item instanceof Menu.Appetizer appetizer) {
            name = appetizer.name;
            price = appetizer.price;
        } else if (item instanceof Menu.Dessert dessert) {
            name = dessert.name;
            price = dessert.price;
        } else if (item instanceof Menu.Drink drink) {
            name = drink.name;
            price = drink.name.equalsIgnoreCase("دوغ") ? 17.0 : drink.price;
        } else {
            System.out.println("نوع آیتم نامعتبر است.");
            return;
        }

        items.add(new OrderItem(name, price, quantity, item));
    }

    public boolean removeItem(String itemName) {
        for (OrderItem item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    public void applyDiscount(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            System.out.println("تخفیف باید بین 0 تا 100 درصد باشد.");
            return;
        }
        this.discount = discountPercent;
        System.out.println("تخفیف " + discountPercent + "% اعمال شد.");
    }

    public void setDiscount(double discountPercent) {
        if (discountPercent >= 0 && discountPercent <= 100) {
            this.discount = discountPercent;
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        total = total * (1 - discount / 100);
        return Math.round(total);
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getDiscount() {
        return discount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    // کلاس داخلی اصلاح‌شده OrderItem
    public static class OrderItem {
        private String name;
        private double unitPrice;
        private int quantity;
        private Object source;

        public OrderItem(String name, double unitPrice, int quantity, Object source) {
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.source = source;
        }

        public String getName() {
            return name;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public Object getSource() {
            return source;
        }
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
