package Common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Invoice {
    private Order order;

    public Invoice(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }

    // چاپ فاکتور در کنسول
    public void printInvoice() {
        System.out.println("======= فاکتور سفارش شماره " + order.getOrderId() + " =======");
        System.out.println("مشتری: " + order.getCustomer().getName() + " " + order.getCustomer().getFamily());
        System.out.println("شماره میز: " + order.getTableNumber());
        System.out.println("آیتم‌ها:");
        for (Order.OrderItem item : order.getItems()) {
            double totalPrice = item.getQuantity() * item.getUnitPrice();
            System.out.printf("- %s x%d = %.0f تومان%n", item.getName(), item.getQuantity(), totalPrice);
        }
        System.out.println("--------------------------------------");
        System.out.printf("تخفیف: %.0f%%%n", order.getDiscount());
        System.out.printf("مبلغ نهایی قابل پرداخت: %.0f تومان%n", order.getTotalPrice());
        System.out.println("======================================");
    }

    // ذخیره فاکتور در فایل
    public void saveInvoiceToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("invoices.txt", true))) {
            writer.write(this.toString()); // ذخیره جزئیات فاکتور به صورت متنی
            writer.newLine();  // خط جدید بعد از هر فاکتور
        } catch (IOException e) {
            System.out.println("خطا در ذخیره فاکتور: " + e.getMessage());
        }
    }

    // پیاده‌سازی متد toString برای ذخیره‌سازی در فایل
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("======= فاکتور سفارش شماره ").append(order.getOrderId()).append(" =======\n");
        sb.append("مشتری: ").append(order.getCustomer().getName()).append(" ").append(order.getCustomer().getFamily()).append("\n");
        sb.append("شماره میز: ").append(order.getTableNumber()).append("\n");
        sb.append("آیتم‌ها:\n");
        
        for (Order.OrderItem item : order.getItems()) {
            double totalPrice = item.getQuantity() * item.getUnitPrice();
            sb.append("- ").append(item.getName()).append(" x").append(item.getQuantity())
              .append(" = ").append(String.format("%.0f", totalPrice)).append(" تومان\n");
        }
        
        sb.append("--------------------------------------\n");
        sb.append("تخفیف: ").append(String.format("%.0f", order.getDiscount())).append("%\n");
        sb.append("مبلغ نهایی قابل پرداخت: ").append(String.format("%.0f", order.getTotalPrice())).append(" تومان\n");
        sb.append("======================================\n");
        
        return sb.toString();
    }
}
