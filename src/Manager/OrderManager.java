package Manager;

import Common.Menu;
import Common.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders; // لیست سفارش‌ها
    private static List<Order> savedOrders = null; // لیست استاتیک برای ذخیره سفارش‌ها
    private int nextOrderId; // برای تخصیص شماره سفارش جدید
    private MenuManager MenuManager; // برای دسترسی به منو

    public OrderManager(MenuManager MenuManager) {
        this.MenuManager = MenuManager;
        orders = new ArrayList<>();
        nextOrderId = 1;
        loadOrders(); // لود سفارش‌ها از حافظه استاتیک
    }

    // اضافه کردن سفارش جدید
    public Order createOrder() {
        Order order = new Order(nextOrderId++);
        orders.add(order);
        return order;
    }

    // اضافه کردن آیتم به سفارش با چک موجودی منو
    public boolean addItemToOrder(int orderId, String itemName, String itemType, int quantity) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("سفارش با شماره " + orderId + " پیدا نشد.");
            return false;
        }

        Menu menu = MenuManager.getMenu();
        switch (itemType.toLowerCase()) {
            case "food":
                for (Menu.Food food : menu.foods) {
                    if (food.name.equalsIgnoreCase(itemName)) {
                        order.addItem(food, quantity);
                        System.out.println("آیتم " + itemName + " به سفارش اضافه شد.");
                        return true;
                    }
                }
                break;
            case "appetizer":
                for (Menu.Appetizer appetizer : menu.appetizers) {
                    if (appetizer.name.equalsIgnoreCase(itemName)) {
                        order.addItem(appetizer, quantity);
                        System.out.println("آیتم " + itemName + " به سفارش اضافه شد.");
                        return true;
                    }
                }
                break;
            case "dessert":
                for (Menu.Dessert dessert : menu.desserts) {
                    if (dessert.name.equalsIgnoreCase(itemName)) {
                        order.addItem(dessert, quantity);
                        System.out.println("آیتم " + itemName + " به سفارش اضافه شد.");
                        return true;
                    }
                }
                break;
            case "drink":
                for (Menu.Drink drink : menu.drinks) {
                    if (drink.name.equalsIgnoreCase(itemName)) {
                        order.addItem(drink, quantity);
                        System.out.println("آیتم " + itemName + " به سفارش اضافه شد.");
                        return true;
                    }
                }
                break;
            default:
                System.out.println("نوع آیتم نامعتبر: " + itemType);
                return false;
        }
        System.out.println("آیتم " + itemName + " در منو پیدا نشد.");
        return false;
    }

    // حذف سفارش
    public boolean removeOrder(int orderId) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("سفارش با شماره " + orderId + " پیدا نشد.");
            return false;
        }
        orders.remove(order);
        System.out.println("سفارش شماره " + orderId + " حذف شد.");
        return true;
    }

    // ویرایش سفارش (حذف آیتم)
    public boolean removeItemFromOrder(int orderId, String itemName) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("سفارش با شماره " + orderId + " پیدا نشد.");
            return false;
        }
        if (order.removeItem(itemName)) {
            System.out.println("آیتم " + itemName + " از سفارش حذف شد.");
            return true;
        }
        System.out.println("آیتم " + itemName + " در سفارش پیدا نشد.");
        return false;
    }

    // جستجوی سفارش
    public void searchOrder(int orderId) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("سفارش با شماره " + orderId + " پیدا نشد.");
            return;
        }
        System.out.println("=== سفارش شماره: " + order.getOrderId() + " ===");
        System.out.println("آیتم‌ها:");
        for (Order.OrderItem item : order.getItems()) {
            System.out.printf("- %s: %d عدد (%.0f تومن هر واحد، جمع: %.0f تومن)%n",
                    item.getName(), item.getQuantity(), item.getUnitPrice(), item.getUnitPrice() * item.getQuantity());
        }
        System.out.printf("تخفیف: %.0f%%%n", order.getDiscount());
        System.out.printf("مجموع قیمت سفارش: %.0f تومن%n", order.getTotalPrice());
        System.out.println("-------------------");
    }

    // محاسبه مجموع قیمت همه سفارش‌ها
    public double getTotalAllOrders() {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotalPrice();
        }
        return total;
    }

    // نمایش همه سفارش‌ها
    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("هیچ سفارشی ثبت نشده است.");
            return;
        }
        System.out.println("=== لیست سفارش‌ها ===");
        for (Order order : orders) {
            System.out.println("سفارش شماره: " + order.getOrderId());
            System.out.println("آیتم‌ها:");
            for (Order.OrderItem item : order.getItems()) {
                System.out.printf("- %s: %d عدد (%.0f تومن هر واحد، جمع: %.0f تومن)%n",
                        item.getName(), item.getQuantity(), item.getUnitPrice(), item.getUnitPrice() * item.getQuantity());
            }
            System.out.printf("تخفیف: %.0f%%%n", order.getDiscount());
            System.out.printf("مجموع قیمت سفارش: %.0f تومن%n", order.getTotalPrice());
            System.out.println("-------------------");
        }
        System.out.printf("مجموع کل سفارش‌ها: %.0f تومن%n", getTotalAllOrders());
    }

    // محاسبه مجموع قیمت همه سفارش‌ها
    public double getTotalAllOrders() {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotalPrice();
        }
        return total;
    }

    // نمایش همه سفارش‌ها
    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("هیچ سفارشی ثبت نشده است.");
            return;
        }
        System.out.println("=== لیست سفارش‌ها ===");
        for (Order order : orders) {
            System.out.println("سفارش شماره: " + order.getOrderId());
            System.out.println("آیتم‌ها:");
            for (Order.OrderItem item : order.getItems()) {
                System.out.printf("- %s: %d عدد (%.0f تومن هر واحد، جمع: %.0f تومن)%n",
                        item.getName(), item.getQuantity(), item.getUnitPrice(), item.getUnitPrice() * item.getQuantity());
            }
            System.out.printf("تخفیف: %.0f%%%n", order.getDiscount());
            System.out.printf("مجموع قیمت سفارش: %.0f تومن%n", order.getTotalPrice());
            System.out.println("-------------------");
        }
        System.out.printf("مجموع کل سفارش‌ها: %.0f تومن%n", getTotalAllOrders());
    }
    
    // ذخیره سفارش‌ها تو حافظه استاتیک
    public void saveOrders() {
        savedOrders = new ArrayList<>();
        for (Order order : orders) {
            Order savedOrder = new Order(order.getOrderId());
            for (Order.OrderItem item : order.getItems()) {
                savedOrder.addItem(new Menu.Food(item.getName(), item.getUnitPrice()), item.getQuantity());
            }
            savedOrder.setDiscount(order.getDiscount()); // تنظیم تخفیف بدون پیام
            savedOrders.add(savedOrder);
        }
        System.out.println("سفارش‌ها با موفقیت ذخیره شدند.");
    }

    // لود سفارش‌ها از حافظه استاتیک
    public void loadOrders() {
        if (savedOrders != null && !savedOrders.isEmpty()) {
            orders.clear();
            for (Order savedOrder : savedOrders) {
                Order newOrder = new Order(savedOrder.getOrderId());
                for (Order.OrderItem item : savedOrder.getItems()) {
                    newOrder.addItem(new Menu.Food(item.getName(), item.getUnitPrice()), item.getQuantity());
                }
                newOrder.setDiscount(savedOrder.getDiscount()); // تنظیم تخفیف بدون پیام
                orders.add(newOrder);
            }
            nextOrderId = orders.stream().mapToInt(Order::getOrderId).max().orElse(0) + 1;
            System.out.println("سفارش‌ها با موفقیت از حافظه لود شدند.");
        } else {
            orders.clear();
            nextOrderId = 1;
            System.out.println("هیچ سفارشی ذخیره نشده است، لیست سفارش‌ها خالی است.");
        }
    }

    // پیدا کردن سفارش با شماره
    private Order findOrder(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
}
