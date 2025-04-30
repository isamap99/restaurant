package Manager;

import Common.Customer;
import Common.Order;
import Common.Payment;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentManager {
    private TableManager tableManager;

    public PaymentManager(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    public void processPayment(Order order) {
        if (order == null) {
            System.out.println("❌ سفارش نامعتبر است.");
            return;
        }

        if (order.isPaid()) {
            System.out.println("⚠️ این سفارش قبلاً پرداخت شده است.");
            return;
        }

        // ساخت Payment
        Payment payment = new Payment(order.getOrderId(), order.getCustomer(), order.getTableNumber(), order.getTotalPrice());
        payment.markAsPaid();
        order.setPaid(true); // علامت‌گذاری سفارش به عنوان پرداخت‌شده

        // خالی کردن میز
        tableManager.freeTable(order.getTableNumber());

        // نمایش پیام موفقیت
        System.out.println("✅ پرداخت سفارش شماره " + order.getOrderId() + " انجام شد. مبلغ: " + payment.getAmount() + " تومان");

        // ذخیره در فایل
        savePaymentToFile(payment);
    }

    private void savePaymentToFile(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payments.txt", true))) {
            writer.write(payment.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("⚠️ خطا در ذخیره پرداخت در فایل: " + e.getMessage());
        }
    }

    public void removePayment(int orderId) {
        List<String> payments = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("payments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Order ID: " + orderId)) {
                    found = true;
                    continue; // این خط را نادیده می‌گیریم
                }
                payments.add(line);
            }
        } catch (IOException e) {
            System.out.println("⚠️ خطا در خواندن فایل پرداخت‌ها: " + e.getMessage());
        }

        if (!found) {
            System.out.println("⚠️ پرداخت با شماره فاکتور " + orderId + " یافت نشد.");
            return;
        }

        // بازنویسی فایل با پرداخت‌های جدید
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payments.txt"))) {
            for (String payment : payments) {
                writer.write(payment);
                writer.newLine();
            }
            System.out.println("✅ پرداخت با شماره فاکتور " + orderId + " حذف شد.");
        } catch (IOException e) {
            System.out.println("⚠️ خطا در ذخیره فایل پس از حذف پرداخت: " + e.getMessage());
        }
    }
    
    public void displayAllPayments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("payments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("⚠️ خطا در خواندن فایل پرداخت‌ها: " + e.getMessage());
        }
    }
    public void updatePaymentStatus(int orderId, boolean isSuccessful) {
        try {
            List<String> payments = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("payments.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Order ID: " + orderId)) {
                    // به روز رسانی وضعیت پرداخت
                    line = line.replace("Status: Pending", "Status: " + (isSuccessful ? "Successful" : "Failed"));
                }
                payments.add(line);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("payments.txt"));
            for (String payment : payments) {
                writer.write(payment);
                writer.newLine();
            }
            writer.close();

            System.out.println("✅ وضعیت پرداخت سفارش " + orderId + " به روز شد.");

        } catch (IOException e) {
            System.out.println("⚠️ خطا در به روز رسانی وضعیت پرداخت: " + e.getMessage());
        }
    }

}
