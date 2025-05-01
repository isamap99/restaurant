package Manager;
import java.io.*;
import java.util.ArrayList;

import Common.Stock;


public class StockManager {
    private final ArrayList<Stock> stockList;
    private final String fileName = "stock.txt";

    // سازنده که لیست رو آماده می‌کنه و فایل رو می‌خونه
    public StockManager() {
        stockList = new ArrayList<>();
        loadFromFile(); // موقع شروع، داده‌ها رو از فایل می‌خونه
    }

    // اضافه کردن یه آیتم جدید به انبار
    public void addItem(String itemName, int quantity, String unit) {
        Stock newItem = new Stock(itemName, quantity, unit);
        stockList.add(newItem);
        saveToFile(); // بعد از اضافه کردن، تو فایل ذخیره می‌کنه
        System.out.println("آیتم " + itemName + " با تعداد " + quantity + " " + unit + " به انبار اضافه شد!");
    }

    // حذف یه آیتم از انبار
    public void removeItem(String itemName) {
        for (int i = 0; i < stockList.size(); i++) {
            if (stockList.get(i).getItemName().equals(itemName)) {
                stockList.remove(i);
                saveToFile(); // بعد از حذف، فایل رو به‌روزرسانی می‌کنه
                System.out.println("آیتم " + itemName + " از انبار حذف شد!");
                return;
            }
        }
        System.out.println("آیتم " + itemName + " تو انبار پیدا نشد!");
    }

    // آپدیت کردن تعداد یا واحد یه آیتم
    public void updateItem(String itemName, int newQuantity, String newUnit) {
        for (Stock item : stockList) {
            if (item.getItemName().equals(itemName)) {
                item.setQuantity(newQuantity);
                item.setUnit(newUnit);
                saveToFile(); // بعد از تغییر، فایل رو به‌روزرسانی می‌کنه
                System.out.println("آیتم " + itemName + " به‌روزرسانی شد: تعداد " + newQuantity + " " + newUnit);
                return;
            }
        }
        System.out.println("آیتم " + itemName + " تو انبار پیدا نشد!");
    }

    // نمایش همه آیتم‌های انبار
    public void displayStock() {
        if (stockList.isEmpty()) {
            System.out.println("انبار خالیه!");
        } else {
            System.out.println("لیست انبار:");
            for (Stock item : stockList) {
                System.out.println("آیتم: " + item.getItemName() + " - تعداد: " + item.getQuantity() + " " + item.getUnit());
            }
        }
    }

    // ذخیره کردن لیست تو فایل
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Stock item : stockList) {
                writer.write(item.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("یه مشکلی تو ذخیره کردن فایل پیش اومد: " + e.getMessage());
        }
    }

    // خوندن داده‌ها از فایل
    private void loadFromFile() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile(); // اگه فایل وجود نداشته باشه، یه فایل جدید می‌سازه
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Stock item = Stock.fromString(line);
                    stockList.add(item);
                } catch (IllegalArgumentException e) {
                    System.out.println("خط نادرست در فایل: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("یه مشکلی تو خوندن فایل پیش اومد: " + e.getMessage());
        }
    }
    
 // جستجوی آیتم‌ها بر اساس نام (کامل یا بخشی از نام)
    public void searchItem(String keyword) {
        boolean found = false;
        System.out.println("نتایج جستجو برای: " + keyword);
        for (Stock item : stockList) {
            if (item.getItemName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("آیتم: " + item.getItemName() + " - تعداد: " + item.getQuantity() + " " + item.getUnit());
                found = true;
            }
        }

        if (!found) {
            System.out.println("هیچ آیتمی با نام \"" + keyword + "\" پیدا نشد.");
        }
    }

	
}
