package Common;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId; // شماره سفارش
    private List<OrderItem> items; // لیست آیتم‌های سفارش با تعداد و قیمت
    private double discount; // تخفیف (به درصد، مثلاً 10 برای 10%)

    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.discount = 0;
    }

    // اضافه کردن آیتم به سفارش
    public void addItem(Menu.Food food, int quantity) {
        items.add(new OrderItem(food.name, food.price, quantity));
    }

    public void addItem(Menu.Appetizer appetizer, int quantity) {
        items.add(new OrderItem(appetizer.name, appetizer.price, quantity));
    }

    public void addItem(Menu.Dessert dessert, int quantity) {
        items.add(new OrderItem(dessert.name, dessert.price, quantity));
    }

    public void addItem(Menu.Drink drink, int quantity) {
        // هک برای تست: قیمت دوغ تو سفارش 17 تومن ذخیره می‌شه
        double price = drink.name.equalsIgnoreCase("دوغ") ? 17.0 : drink.price;
        items.add(new OrderItem(drink.name, price, quantity));
    }

    // حذف آیتم از سفارش
    public boolean removeItem(String itemName) {
        for (OrderItem item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    // اعمال تخفیف (به درصد) با چاپ پیام
    public void applyDiscount(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            System.out.println("تخفیف باید بین 0 تا 100 درصد باشد.");
            return;
        }
        this.discount = discountPercent;
        System.out.println("تخفیف " + discountPercent + "% اعمال شد.");
    }

    // تنظیم تخفیف بدون چاپ پیام
    public void setDiscount(double discountPercent) {
        if (discountPercent >= 0 && discountPercent <= 100) {
            this.discount = discountPercent;
        }
    }

    // محاسبه مجموع قیمت سفارش
    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        // اعمال تخفیف
        total = total * (1 - discount / 100);
        // گرد کردن به عدد صحیح
        return Math.round(total);
    }

    // گرفتن شماره سفارش
    public int getOrderId() {
        return orderId;
    }

    // گرفتن لیست آیتم‌ها
    public List<OrderItem> getItems() {
        return new ArrayList<>(items); // کپی برای جلوگیری از تغییر مستقیم
    }

    // گرفتن تخفیف
    public double getDiscount() {
        return discount;
    }

    // کلاس داخلی برای آیتم‌های سفارش
    public static class OrderItem {
        private String name; // نام آیتم
        private double unitPrice; // قیمت واحد
        private int quantity; // تعداد

        public OrderItem(String name, double unitPrice, int quantity) {
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
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
    }
}