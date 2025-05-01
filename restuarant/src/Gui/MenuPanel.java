package Gui;

import Common.Menu;
import Manager.menuManager;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JTextArea menuTextArea;
    private menuManager menuManager;
    private JTextField itemTypeField;
    private JTextField itemNameField;
    private JTextField priceField;
    private JTextField newNameField;
    private JTextField newPriceField;
    private JTextField reduceAmountField;
    private JTextField searchItemField;

    public MenuPanel(menuManager menuManager) {
        // ایجاد مدیریت منو
        this.menuManager = menuManager;

        // تنظیم Layout برای پنل
        setLayout(new BorderLayout());

        // ایجاد فیلدهای ورودی برای نوع آیتم، نام آیتم، قیمت، نام جدید، قیمت جدید، مقدار کاهش و جستجو
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        itemTypeField = new JTextField();
        itemNameField = new JTextField();
        priceField = new JTextField();
        newNameField = new JTextField();
        newPriceField = new JTextField();
        reduceAmountField = new JTextField();
        searchItemField = new JTextField();

        inputPanel.add(new JLabel("نوع آیتم (food/dessert/appetizer/drink):"));
        inputPanel.add(itemTypeField);
        inputPanel.add(new JLabel("نام آیتم:"));
        inputPanel.add(itemNameField);
        inputPanel.add(new JLabel("قیمت (تومن):"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("نام جدید (برای ویرایش):"));
        inputPanel.add(newNameField);
        inputPanel.add(new JLabel("قیمت جدید (برای ویرایش):"));
        inputPanel.add(newPriceField);
        inputPanel.add(new JLabel("مقدار کاهش قیمت (تومن):"));
        inputPanel.add(reduceAmountField);
        inputPanel.add(new JLabel("نام آیتم برای جستجو:"));
        inputPanel.add(searchItemField);

        // دکمه‌ها برای عملیات‌های مختلف
        JPanel buttonPanel = new JPanel();
        JButton addItemButton = new JButton("اضافه کردن آیتم");
        JButton removeItemButton = new JButton("حذف آیتم");
        JButton editItemButton = new JButton("ویرایش آیتم");
        JButton reducePriceButton = new JButton("کاهش قیمت آیتم");
        JButton searchItemButton = new JButton("جستجوی آیتم");
        JButton showAllButton = new JButton("نمایش همه آیتم‌ها");
        JButton saveMenuButton = new JButton("ذخیره منو");
        JButton loadMenuButton = new JButton("لود منو");

        buttonPanel.add(addItemButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(editItemButton);
        buttonPanel.add(reducePriceButton);
        buttonPanel.add(searchItemButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(saveMenuButton);
        buttonPanel.add(loadMenuButton);

        // استفاده از JTextArea برای نمایش آیتم‌های منو
        menuTextArea = new JTextArea(20, 50);
        menuTextArea.setEditable(false); // غیرقابل ویرایش بودن
        JScrollPane scrollPane = new JScrollPane(menuTextArea);

        // اضافه کردن اجزا به پنل اصلی
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // بارگذاری اولیه آیتم‌های منو
        loadMenuDisplay();
        
        // رویدادها
        addItemButton.addActionListener(e -> addItem());
        removeItemButton.addActionListener(e -> removeItem());
        editItemButton.addActionListener(e -> editItem());
        reducePriceButton.addActionListener(e -> reducePrice());
        searchItemButton.addActionListener(e -> searchItem());
        showAllButton.addActionListener(e -> loadMenuDisplay());
        saveMenuButton.addActionListener(e -> saveMenu());
        loadMenuButton.addActionListener(e -> loadMenu());
    }

    // بارگذاری آیتم‌های منو و نمایش آن‌ها در JTextArea
    private void loadMenuDisplay() {
        Menu menu = menuManager.getMenu();
        StringBuilder sb = new StringBuilder();
        sb.append("=== لیست منو ===\n");

        // نمایش غذاها
        sb.append("غذاها:\n");
        if (menu.foods.isEmpty()) {
            sb.append("هیچ غذایی وجود ندارد.\n");
        } else {
            for (Menu.Food food : menu.foods) {
                sb.append("- ").append(food.name).append(": ").append(food.price).append(" تومن\n");
            }
        }

        // نمایش دسرها
        sb.append("\nدسرها:\n");
        if (menu.desserts.isEmpty()) {
            sb.append("هیچ دسری وجود ندارد.\n");
        } else {
            for (Menu.Dessert dessert : menu.desserts) {
                sb.append("- ").append(dessert.name).append(": ").append(dessert.price).append(" تومن\n");
            }
        }

        // نمایش پیش‌غذاها
        sb.append("\nپیش‌غذاها:\n");
        if (menu.appetizers.isEmpty()) {
            sb.append("هیچ پیش‌غذایی وجود ندارد.\n");
        } else {
            for (Menu.Appetizer appetizer : menu.appetizers) {
                sb.append("- ").append(appetizer.name).append(": ").append(appetizer.price).append(" تومن\n");
            }
        }

        // نمایش نوشیدنی‌ها
        sb.append("\nنوشیدنی‌ها:\n");
        if (menu.drinks.isEmpty()) {
            sb.append("هیچ نوشیدنی‌ای وجود ندارد.\n");
        } else {
            for (Menu.Drink drink : menu.drinks) {
                sb.append("- ").append(drink.name).append(": ").append(drink.price).append(" تومن\n");
            }
        }

        menuTextArea.setText(sb.toString());
    }

    // اضافه کردن آیتم جدید
    private void addItem() {
        String itemType = itemTypeField.getText();
        String itemName = itemNameField.getText();
        String priceStr = priceField.getText();

        if (itemType.isEmpty() || itemName.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً نوع آیتم، نام آیتم و قیمت را وارد کنید.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            menuManager.addItem(itemType, itemName, price);
            loadMenuDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً قیمت را به صورت عدد وارد کنید.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در اضافه کردن آیتم: " + e.getMessage());
        }
    }

    // حذف آیتم
    private void removeItem() {
        String itemType = itemTypeField.getText();
        String itemName = itemNameField.getText();

        if (itemType.isEmpty() || itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً نوع آیتم و نام آیتم را وارد کنید.");
            return;
        }

        try {
            menuManager.removeItem(itemType, itemName);
            loadMenuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در حذف آیتم: " + e.getMessage());
        }
    }

    // ویرایش آیتم
    private void editItem() {
        String itemType = itemTypeField.getText();
        String oldName = itemNameField.getText();
        String newName = newNameField.getText();
        String newPriceStr = newPriceField.getText();

        if (itemType.isEmpty() || oldName.isEmpty() || newName.isEmpty() || newPriceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً نوع آیتم، نام فعلی، نام جدید و قیمت جدید را وارد کنید.");
            return;
        }

        try {
            double newPrice = Double.parseDouble(newPriceStr);
            menuManager.editItem(itemType, oldName, newName, newPrice);
            loadMenuDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً قیمت جدید را به صورت عدد وارد کنید.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در ویرایش آیتم: " + e.getMessage());
        }
    }

    // کاهش قیمت آیتم
    private void reducePrice() {
        String itemType = itemTypeField.getText();
        String itemName = itemNameField.getText();
        String amountStr = reduceAmountField.getText();

        if (itemType.isEmpty() || itemName.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً نوع آیتم، نام آیتم و مقدار کاهش قیمت را وارد کنید.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            menuManager.reducePrice(itemType, itemName, amount);
            loadMenuDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً مقدار کاهش قیمت را به صورت عدد وارد کنید.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در کاهش قیمت: " + e.getMessage());
        }
    }

    // جستجوی آیتم
    private void searchItem() {
        String itemName = searchItemField.getText();
        if (itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً نام آیتم را برای جستجو وارد کنید.");
            return;
        }

        try {
            String result = menuManager.searchItem(itemName);
            menuTextArea.setText(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در جستجوی آیتم: " + e.getMessage());
        }
    }

    // ذخیره منو
    private void saveMenu() {
        try {
            menuManager.saveMenu();
            JOptionPane.showMessageDialog(this, "منو با موفقیت در فایل ذخیره شد.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در ذخیره منو: " + e.getMessage());
        }
    }

    // لود منو
    private void loadMenu() {
        try {
            menuManager.loadMenu();
            loadMenuDisplay();
            JOptionPane.showMessageDialog(this, "منو با موفقیت از فایل لود شد.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در لود منو: " + e.getMessage());
            loadMenuDisplay();
        }
    }
}