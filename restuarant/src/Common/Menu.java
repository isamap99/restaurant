package Common;


import java.util.ArrayList;
import java.util.List;

public class Menu {
    public List<Food> foods = new ArrayList<>();
    public List<Dessert> desserts = new ArrayList<>();
    public List<Appetizer> appetizers = new ArrayList<>();
    public List<Drink> drinks = new ArrayList<>();

    public Menu() {
        loadDefaultMenu(); // لود منوی پیش‌فرض موقع ساخت شیء
    }

    // لود منوی پیش‌فرض ایرانی با قیمت‌های افزایش‌یافته
    public void loadDefaultMenu() {
        // پاک کردن لیست‌های قبلی (برای اطمینان)
        foods.clear();
        desserts.clear();
        appetizers.clear();
        drinks.clear();

        // 10 غذای ایرانی با قیمت‌های جدید
        foods.add(new Food("چلوکباب کوبیده", 180));
        foods.add(new Food("جوجه‌کباب", 150));
        foods.add(new Food("زرشک‌پلو با مرغ", 140));
        foods.add(new Food("قورمه‌سبزی", 120));
        foods.add(new Food("قیمه", 115));
        foods.add(new Food("دیزی", 125));
        foods.add(new Food("کشک بادمجان", 90));
        foods.add(new Food("باقالی‌پلو با گوشت", 170));
        foods.add(new Food("فسنجان", 145));
        foods.add(new Food("ته‌چین", 140));

        // 5 پیش‌غذای ایرانی (شامل کشک و بادمجان)
        appetizers.add(new Appetizer("سوپ جو", 35));
        appetizers.add(new Appetizer("آش رشته", 40));
        appetizers.add(new Appetizer("سالاد شیرازی", 36));
        appetizers.add(new Appetizer("زیتون پرورده", 38));
        appetizers.add(new Appetizer("ترشی مخلوط", 32));

        // 5 دسر (شامل باقلوا)
        desserts.add(new Dessert("باقلوا", 50));
        desserts.add(new Dessert("فرنی", 30));
        desserts.add(new Dessert("حلوا", 45));
        desserts.add(new Dessert("بستنی زعفرانی", 55));
        desserts.add(new Dessert("فالوده شیرازی", 45));

        // 5 نوشیدنی (شامل نوشابه)
        drinks.add(new Drink("دوغ", 25));
        drinks.add(new Drink("شربت زعفران", 30));
        drinks.add(new Drink("شربت خاکشیر", 28));
        drinks.add(new Drink("نوشابه", 20));
        drinks.add(new Drink("چای ایرانی", 15));
    }

    public static class Food {
        public String name;
        public double price;

        public Food(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    public static class Dessert {
        public String name;
        public double price;

        public Dessert(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    public static class Appetizer {
        public String name;
        public double price;

        public Appetizer(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    public static class Drink {
        public String name;
        public double price;

        public Drink(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }
}