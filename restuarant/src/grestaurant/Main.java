package grestaurant;


import Common.Customer;
import Common.Table;
import Manager.TableManager;
import Menu;
import Order;
import Common.*;
import Manager.*;

public class Main {
    public static void main(String[] args) {
        // 1. ساخت مشتری
        Customer customer = new Customer("علی", "رضایی", "تهران", "09123456789");

        // 2. ساخت سفارش و افزودن آیتم‌ها
        Order order = new Order(1, customer, "t2");
        order.addItem(new Menu.Food("جوجه‌کباب", 150), 2);
        order.addItem(new Menu.Drink("دوغ", 17), 1);
        order.setDiscount(10);

        // 3. ساخت InvoiceManager و ثبت فاکتور
        InvoiceManager invoiceManager = new InvoiceManager();
        invoiceManager.createInvoice(order);

        // 4. چاپ همه فاکتورها
        System.out.println("\n📄 همه فاکتورها:");
        invoiceManager.printAllInvoices();

        // 5. جستجوی فاکتور بر اساس شماره
        System.out.println("\n🔍 جستجو با شماره فاکتور:");
        invoiceManager.searchInvoice(1, null);

        // 6. حذف فاکتور بر اساس نام مشتری
        //System.out.println("\n❌ حذف فاکتور بر اساس نام:");
        //invoiceManager.removeInvoice(-1, "علی");

        // 7. چاپ نهایی فاکتورها (باید خالی باشه)
        System.out.println("\n📄 فاکتورهای باقیمانده:");
        invoiceManager.printAllInvoices();
        
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

    }
}
