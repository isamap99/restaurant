package Manager;

import Common.Menu;
import java.util.ArrayList;
import java.util.List;

public class menuManager {
    private Menu menu;
    private static Menu savedMenu = null; // لیست استاتیک برای ذخیره منو

    public menuManager() {
        menu = new Menu(); // منوی پیش‌فرض تو Menu لود می‌شه
        loadMenu(); // لود منو از حافظه استاتیک (اگه موجود باشه)
    }

    // گرفتن منو
    public Menu getMenu() {
        return menu;
    }

    // ذخیره منو تو حافظه استاتیک
    public void saveMenu() {
        savedMenu = new Menu();
        savedMenu.foods.clear();
        savedMenu.desserts.clear();
        savedMenu.appetizers.clear();
        savedMenu.drinks.clear();

        // کپی عمیق آیتم‌ها به savedMenu
        for (Menu.Food food : menu.foods) {
            savedMenu.foods.add(new Menu.Food(food.name, food.price));
        }
        for (Menu.Dessert dessert : menu.desserts) {
            savedMenu.desserts.add(new Menu.Dessert(dessert.name, dessert.price));
        }
        for (Menu.Appetizer appetizer : menu.appetizers) {
            savedMenu.appetizers.add(new Menu.Appetizer(appetizer.name, appetizer.price));
        }
        for (Menu.Drink drink : menu.drinks) {
            savedMenu.drinks.add(new Menu.Drink(drink.name, drink.price));
        }
        System.out.println("منو با موفقیت ذخیره شد.");
    }

    // لود منو از حافظه استاتیک
    public void loadMenu() {
        if (savedMenu != null && !savedMenu.foods.isEmpty()) {
            // کپی عمیق آیتم‌ها از savedMenu به menu
            menu.foods.clear();
            menu.desserts.clear();
            menu.appetizers.clear();
            menu.drinks.clear();

            for (Menu.Food food : savedMenu.foods) {
                menu.foods.add(new Menu.Food(food.name, food.price));
            }
            for (Menu.Dessert dessert : savedMenu.desserts) {
                menu.desserts.add(new Menu.Dessert(dessert.name, dessert.price));
            }
            for (Menu.Appetizer appetizer : savedMenu.appetizers) {
                menu.appetizers.add(new Menu.Appetizer(appetizer.name, appetizer.price));
            }
            for (Menu.Drink drink : savedMenu.drinks) {
                menu.drinks.add(new Menu.Drink(drink.name, drink.price));
            }
            System.out.println("منو با موفقیت از حافظه لود شد.");
        } else {
            System.out.println("هیچ منوی ذخیره‌شده‌ای وجود ندارد، منوی پیش‌فرض لود شد.");
            menu = new Menu(); // لود منوی پیش‌فرض
        }
    }

    // جستجو آیتم با اسم
    public String searchItem(String name) {
        for (Menu.Food food : menu.foods) {
            if (food.name.equalsIgnoreCase(name)) {
                return "Food: " + food.name + ", " + food.price + " تومن";
            }
        }
        for (Menu.Dessert dessert : menu.desserts) {
            if (dessert.name.equalsIgnoreCase(name)) {
                return "Dessert: " + dessert.name + ", " + dessert.price + " تومن";
            }
        }
        for (Menu.Appetizer appetizer : menu.appetizers) {
            if (appetizer.name.equalsIgnoreCase(name)) {
                return "Appetizer: " + appetizer.name + ", " + appetizer.price + " تومن";
            }
        }
        for (Menu.Drink drink : menu.drinks) {
            if (drink.name.equalsIgnoreCase(name)) {
                return "Drink: " + drink.name + ", " + drink.price + " تومن";
            }
        }
        return "آیتم " + name + " پیدا نشد.";
    }

    // اضافه کردن آیتم جدید
    public void addItem(String type, String name, double price) {
        switch (type.toLowerCase()) {
            case "food":
                menu.foods.add(new Menu.Food(name, price));
                break;
            case "dessert":
                menu.desserts.add(new Menu.Dessert(name, price));
                break;
            case "appetizer":
                menu.appetizers.add(new Menu.Appetizer(name, price));
                break;
            case "drink":
                menu.drinks.add(new Menu.Drink(name, price));
                break;
            default:
                System.out.println("نوع آیتم نامعتبر: " + type);
                return;
        }
        System.out.println("آیتم " + name + " اضافه شد.");
    }

    // حذف آیتم
    public void removeItem(String type, String name) {
        switch (type.toLowerCase()) {
            case "food":
                menu.foods.removeIf(food -> food.name.equalsIgnoreCase(name));
                break;
            case "dessert":
                menu.desserts.removeIf(dessert -> dessert.name.equalsIgnoreCase(name));
                break;
            case "appetizer":
                menu.appetizers.removeIf(appetizer -> appetizer.name.equalsIgnoreCase(name));
                break;
            case "drink":
                menu.drinks.removeIf(drink -> drink.name.equalsIgnoreCase(name));
                break;
            default:
                System.out.println("نوع آیتم نامعتبر: " + type);
                return;
        }
        System.out.println("آیتم " + name + " حذف شد.");
    }

    // ویرایش آیتم
    public void editItem(String type, String oldName, String newName, double newPrice) {
        switch (type.toLowerCase()) {
            case "food":
                for (Menu.Food food : menu.foods) {
                    if (food.name.equalsIgnoreCase(oldName)) {
                        food.name = newName;
                        food.price = newPrice;
                        break;
                    }
                }
                break;
            case "dessert":
                for (Menu.Dessert dessert : menu.desserts) {
                    if (dessert.name.equalsIgnoreCase(oldName)) {
                        dessert.name = newName;
                        dessert.price = newPrice;
                        break;
                    }
                }
                break;
            case "appetizer":
                for (Menu.Appetizer appetizer : menu.appetizers) {
                    if (appetizer.name.equalsIgnoreCase(oldName)) {
                        appetizer.name = newName;
                        appetizer.price = newPrice;
                        break;
                    }
                }
                break;
            case "drink":
                for (Menu.Drink drink : menu.drinks) {
                    if (drink.name.equalsIgnoreCase(oldName)) {
                        drink.name = newName;
                        drink.price = newPrice;
                        break;
                    }
                }
                break;
            default:
                System.out.println("نوع آیتم نامعتبر: " + type);
                return;
        }
        System.out.println("آیتم " + oldName + " به " + newName + " ویرایش شد.");
    }

    // کاهش قیمت آیتم
    public void reducePrice(String type, String name, double amount) {
        switch (type.toLowerCase()) {
            case "food":
                for (Menu.Food food : menu.foods) {
                    if (food.name.equalsIgnoreCase(name)) {
                        food.price = Math.max(0, food.price - amount);
                        break;
                    }
                }
                break;
            case "dessert":
                for (Menu.Dessert dessert : menu.desserts) {
                    if (dessert.name.equalsIgnoreCase(name)) {
                        dessert.price = Math.max(0, dessert.price - amount);
                        break;
                    }
                }
                break;
            case "appetizer":
                for (Menu.Appetizer appetizer : menu.appetizers) {
                    if (appetizer.name.equalsIgnoreCase(name)) {
                        appetizer.price = Math.max(0, appetizer.price - amount);
                        break;
                    }
                }
                break;
            case "drink":
                for (Menu.Drink drink : menu.drinks) {
                    if (drink.name.equalsIgnoreCase(name)) {
                        drink.price = Math.max(0, drink.price - amount);
                        break;
                    }
                }
                break;
            default:
                System.out.println("نوع آیتم نامعتبر: " + type);
                return;
        }
        System.out.println("قیمت آیتم " + name + " به مقدار " + amount + " کاهش یافت.");
    }
}