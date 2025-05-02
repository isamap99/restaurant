package Manager;

import Common.Menu;
import Common.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders;
    private int nextOrderId;
    private menuManager menuManager;
    private ArrayList<Order> order = new ArrayList<>();

    public OrderManager(menuManager menuManager) {
        this.menuManager = menuManager;
        this.orders = new ArrayList<>();
        this.nextOrderId = 1;
    }

    public Order createOrder(int orderId) {
        Order order = new Order(nextOrderId++);
        orders.add(order);
        return order;
    }
    
    public ArrayList<Order> getOrders() {
        return order;
    }


    public boolean addItemToOrder(int orderId, String itemName, String itemType, int quantity) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("سفارش با شماره " + orderId + " پیدا نشد.");
            return false;
        }

        Menu menu = menuManager.getMenu();
        switch (itemType.toLowerCase()) {
            case "food":
                for (Menu.Food food : menu.foods) {
                    if (food.name.equalsIgnoreCase(itemName)) {
                        order.addItem(food, quantity);
                        return true;
                    }
                }
                break;
            case "appetizer":
                for (Menu.Appetizer appetizer : menu.appetizers) {
                    if (appetizer.name.equalsIgnoreCase(itemName)) {
                        order.addItem(appetizer, quantity);
                        return true;
                    }
                }
                break;
            case "dessert":
                for (Menu.Dessert dessert : menu.desserts) {
                    if (dessert.name.equalsIgnoreCase(itemName)) {
                        order.addItem(dessert, quantity);
                        return true;
                    }
                }
                break;
            case "drink":
                for (Menu.Drink drink : menu.drinks) {
                    if (drink.name.equalsIgnoreCase(itemName)) {
                        order.addItem(drink, quantity);
                        return true;
                    }
                }
                break;
            default:
                System.out.println("نوع آیتم نامعتبر است.");
                return false;
        }
        System.out.println("آیتم پیدا نشد.");
        return false;
    }

    public boolean removeOrder(int orderId) {
        Order order = findOrder(orderId);
        if (order != null) {
            orders.remove(order);
            System.out.println("سفارش حذف شد.");
            return true;
        }
        System.out.println("سفارش پیدا نشد.");
        return false;
    }

    public boolean removeItemFromOrder(int orderId, String itemName) {
        Order order = findOrder(orderId);
        if (order != null && order.removeItem(itemName)) {
            System.out.println("آیتم حذف شد.");
            return true;
        }
        System.out.println("آیتم یا سفارش پیدا نشد.");
        return false;
    }

    public void searchOrder(int orderId) {
        Order order = findOrder(orderId);
        if (order != null) {
            System.out.println("=== سفارش شماره " + order.getOrderId() + " ===");
            for (Order.OrderItem item : order.getItems()) {
                System.out.printf("- %s: %d عدد (%.0f تومن) جمع: %.0f%n",
                        item.getName(), item.getQuantity(), item.getUnitPrice(),
                        item.getQuantity() * item.getUnitPrice());
            }
            System.out.printf("تخفیف: %.0f%%%n", order.getDiscount());
            System.out.printf("مبلغ نهایی: %.0f تومن%n", order.getTotalPrice());
        } else {
            System.out.println("سفارش پیدا نشد.");
        }
    }

    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("هیچ سفارشی ثبت نشده است.");
            return;
        }
        for (Order order : orders) {
            searchOrder(order.getOrderId());
            System.out.println("----------------------");
        }
        System.out.printf("مجموع کل سفارش‌ها: %.0f تومن%n", getTotalAllOrders());
    }

    public double getTotalAllOrders() {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotalPrice();
        }
        return total;
    }

    public void saveOrdersToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Order order : orders) {
                writer.println("OrderId:" + order.getOrderId());
                writer.println("Discount:" + order.getDiscount());
                for (Order.OrderItem item : order.getItems()) {
                    writer.printf("Item:%s;%s;%.0f;%d%n",
                            getItemType(item), item.getName(), item.getUnitPrice(), item.getQuantity());
                }
                writer.println("END_ORDER");
            }
            System.out.println("سفارش‌ها با موفقیت در فایل ذخیره شدند.");
        } catch (IOException e) {
            System.out.println("خطا در ذخیره سفارش‌ها: " + e.getMessage());
        }
    }

    public void loadOrdersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            orders.clear();
            String line;
            Order currentOrder = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("OrderId:")) {
                    int orderId = Integer.parseInt(line.substring(8));
                    currentOrder = new Order(orderId);
                } else if (line.startsWith("Discount:")) {
                    if (currentOrder != null)
                        currentOrder.setDiscount(Double.parseDouble(line.substring(9)));
                } else if (line.startsWith("Item:")) {
                    String[] parts = line.substring(5).split(";");
                    String type = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int qty = Integer.parseInt(parts[3]);

                    Object item = createMenuItem(type, name, price);
                    if (currentOrder != null && item != null)
                        currentOrder.addItem(item, qty);
                } else if (line.equals("END_ORDER")) {
                    if (currentOrder != null)
                        orders.add(currentOrder);
                }
            }

            nextOrderId = orders.stream().mapToInt(Order::getOrderId).max().orElse(0) + 1;
            System.out.println("سفارش‌ها از فایل بارگذاری شدند.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("خطا در خواندن فایل سفارش‌ها: " + e.getMessage());
        }
    }

    private Order findOrder(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId)
                return order;
        }
        return null;
    }

    private String getItemType(Order.OrderItem item) {
        Object source = item.getSource();
        if (source instanceof Menu.Food) return "food";
        if (source instanceof Menu.Appetizer) return "appetizer";
        if (source instanceof Menu.Dessert) return "dessert";
        if (source instanceof Menu.Drink) return "drink";
        return "unknown";
    }
    

    private Object createMenuItem(String type, String name, double price) {
        return switch (type.toLowerCase()) {
            case "food" -> new Menu.Food(name, price);
            case "drink" -> new Menu.Drink(name, price);
            case "appetizer" -> new Menu.Appetizer(name, price);
            case "dessert" -> new Menu.Dessert(name, price);
            default -> null;
        };
    }
    
   

}
