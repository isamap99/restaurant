package grestaurant;


import Common.Customer;
import Common.Table;
import Manager.TableManager;

public class Main {
  public static void main(String[] args) {
    TableManager tm = new TableManager("tables.txt");

    // ساخت میزها
    Table t1 = new Table("t10", 4);
    Table t2 = new Table("t11", 6);
    tm.addTable(t1);
    tm.addTable(t2);

    // ساخت مشتری
    Customer c = new Customer();
    c.setName("محمد");
    c.setFamily("جعفری");
    c.setAddress("بناب");
    c.setPhone("09123456789");

    // رزرو میز t10 برای مشتری
    tm.reserveTable("t10", c);

    // نمایش مشتری روی میز t10
    tm.getCustomerOfTable("t10");

  }
}
