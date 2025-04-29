package Manager;

import Common.Order;
import Common.Invoice;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceManager {
    private List<Invoice> invoices;

    public InvoiceManager() {
        invoices = new ArrayList<>();
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    // فاکتور جدید
    public void createInvoice(Order order) {
        Invoice invoice = new Invoice(order);
        invoices.add(invoice);
        invoice.printInvoice();  // چاپ فاکتور در کنسول
        saveInvoiceToFile(invoice);  // ذخیره فاکتور در فایل
    }

    // ذخیره فاکتور در فایل
    private void saveInvoiceToFile(Invoice invoice) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("invoices.txt", true))) {
            writer.write(invoice.toString());  // فرض بر این است که متد toString در Invoice پیاده‌سازی شده
            writer.newLine();  // اضافه کردن یک خط جدید پس از هر فاکتور
        } catch (IOException e) {
            System.out.println("خطا در ذخیره فاکتور: " + e.getMessage());
        }
    }

    // چاپ همه فاکتورها
    public void printAllInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("هیچ فاکتوری ثبت نشده است.");
            return;
        }
        for (Invoice invoice : invoices) {
            invoice.printInvoice();
            System.out.println();
        }
    }

    // حذف فاکتور
    public void removeInvoice(int orderId, String customerName) {
        for (int i = 0; i < invoices.size(); i++) {
            Invoice invoice = invoices.get(i);
            Order order = invoice.getOrder();

            if (order.getOrderId() == orderId || order.getCustomer().getName().equalsIgnoreCase(customerName)) {
                invoices.remove(i);
                System.out.println("✅ فاکتور حذف شد.");
                return;
            }
        }
        System.out.println("⚠️ فاکتور پیدا نشد.");
    }

    // جستجو فاکتور
    public void searchInvoice(int orderId, String customerName) {
        for (Invoice invoice : invoices) {
            Order order = invoice.getOrder();

            if (order.getOrderId() == orderId || order.getCustomer().getName().equalsIgnoreCase(customerName)) {
                System.out.println("✅ فاکتور پیدا شد:");
                invoice.printInvoice();
                return;
            }
        }
        System.out.println("⚠️ فاکتور پیدا نشد.");
    }
}
