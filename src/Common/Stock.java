package Common;
//کلاس Stock برای مدیریت اطلاعات آیتم‌های موجودی (بدون فیلد value)
public class Stock {
 private final String itemName; // نام آیتم
 private int quantity;          // تعداد موجودی
 private String unit;           // واحد اندازه‌گیری

 // سازنده با اعتبارسنجی ورودی‌ها
 public Stock(String itemName, int quantity, String unit) {
     if (itemName == null || itemName.isEmpty())
         throw new IllegalArgumentException("نام آیتم نمی‌تواند خالی باشد.");
     if (unit == null || unit.isEmpty())
         throw new IllegalArgumentException("واحد نمی‌تواند خالی باشد.");
     if (quantity < 0)
         throw new IllegalArgumentException("تعداد نمی‌تواند منفی باشد.");

     this.itemName = itemName;
     this.quantity = quantity;
     this.unit = unit;
 }

 // گترها
 public String getItemName() {
     return itemName;
 }

 public int getQuantity() {
     return quantity;
 }

 public String getUnit() {
     return unit;
 }

 // سترها
 public void setQuantity(int quantity) {
     if (quantity < 0)
         throw new IllegalArgumentException("تعداد نمی‌تواند منفی باشد.");
     this.quantity = quantity;
 }

 public void setUnit(String unit) {
     if (unit == null || unit.isEmpty())
         throw new IllegalArgumentException("واحد نمی‌تواند خالی باشد.");
     this.unit = unit;
 }

 // تبدیل به رشته برای ذخیره در فایل
 @Override
 public String toString() {
     return itemName + "," + quantity + "," + unit;
 }

 // مقایسه بر اساس نام آیتم
 @Override
 public boolean equals(Object obj) {
     if (this == obj) return true;
     if (obj == null || getClass() != obj.getClass()) return false;
     Stock stock = (Stock) obj;
     return itemName.equals(stock.itemName);
 }

 @Override
 public int hashCode() {
     return itemName.hashCode();
 }

 // ساخت شیء Stock از رشته متنی
 public static Stock fromString(String line) {
     if (line == null || line.isEmpty()) {
         throw new IllegalArgumentException("خط ورودی خالی است.");
     }

     String[] parts = line.split(",");
     if (parts.length != 3) {
         throw new IllegalArgumentException("فرمت خط نادرست است: " + line);
     }

     String itemName = parts[0].trim();
     int quantity = Integer.parseInt(parts[1].trim());
     String unit = parts[2].trim();

     return new Stock(itemName, quantity, unit);
 }
}
