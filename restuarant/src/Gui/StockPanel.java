package Gui;

import Common.Stock;
import Manager.StockManager;

import javax.swing.*;
import java.awt.*;

public class StockPanel extends JPanel {
    private JTextArea stockTextArea;
    private StockManager stockManager;
    private JTextField itemNameField;
    private JTextField quantityField;
    private JTextField unitField;
    private JTextField searchKeywordField;

    public StockPanel() {
        // ایجاد مدیریت موجودی
        stockManager = new StockManager();

        // تنظیم Layout برای پنل
        setLayout(new BorderLayout());

        // ایجاد فیلدهای ورودی برای نام آیتم، تعداد، واحد و کلمه کلیدی جستجو
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        itemNameField = new JTextField();
        quantityField = new JTextField();
        unitField = new JTextField();
        searchKeywordField = new JTextField();

        inputPanel.add(new JLabel("نام آیتم:"));
        inputPanel.add(itemNameField);
        inputPanel.add(new JLabel("تعداد:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("واحد اندازه‌گیری:"));
        inputPanel.add(unitField);
        inputPanel.add(new JLabel("کلمه کلیدی برای جستجو:"));
        inputPanel.add(searchKeywordField);

        // دکمه‌ها برای عملیات‌های مختلف
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("اضافه کردن آیتم");
        JButton updateButton = new JButton("به‌روزرسانی آیتم");
        JButton removeButton = new JButton("حذف آیتم");
        JButton searchButton = new JButton("جستجوی آیتم");
        JButton showAllButton = new JButton("نمایش همه آیتم‌ها");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);

        // استفاده از JTextArea برای نمایش آیتم‌های موجودی
        stockTextArea = new JTextArea(20, 50);
        stockTextArea.setEditable(false); // غیرقابل ویرایش بودن
        JScrollPane scrollPane = new JScrollPane(stockTextArea);

        // اضافه کردن اجزا به پنل اصلی
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // بارگذاری اولیه آیتم‌های موجودی
        loadStockItems();

        // رویدادها
        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        removeButton.addActionListener(e -> removeItem());
        searchButton.addActionListener(e -> searchItem());
        showAllButton.addActionListener(e -> loadStockItems());
    }

    // بارگذاری آیتم‌های موجودی و نمایش آن‌ها در JTextArea
    private void loadStockItems() {
        StringBuilder sb = new StringBuilder();
        if (stockManager.getStockList().isEmpty()) {
            sb.append("انبار خالی است!\n");
        } else {
            sb.append("لیست انبار:\n");
            for (Stock item : stockManager.getStockList()) {
                sb.append("آیتم: ").append(item.getItemName())
                  .append(" - تعداد: ").append(item.getQuantity())
                  .append(" ").append(item.getUnit()).append("\n");
            }
        }
        stockTextArea.setText(sb.toString());
    }

    // اضافه کردن آیتم جدید
    private void addItem() {
        String itemName = itemNameField.getText();
        String quantityStr = quantityField.getText();
        String unit = unitField.getText();

        if (itemName.isEmpty() || quantityStr.isEmpty() || unit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً تمام فیلدها را پر کنید.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            stockManager.addItem(itemName, quantity, unit);
            loadStockItems(); // بارگذاری مجدد آیتم‌ها
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً تعداد را به صورت عدد وارد کنید.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // به‌روزرسانی آیتم
    private void updateItem() {
        String itemName = itemNameField.getText();
        String quantityStr = quantityField.getText();
        String unit = unitField.getText();

        if (itemName.isEmpty() || quantityStr.isEmpty() || unit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً تمام فیلدها را پر کنید.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            stockManager.updateItem(itemName, quantity, unit);
            loadStockItems(); // بارگذاری مجدد آیتم‌ها
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً تعداد را به صورت عدد وارد کنید.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // حذف آیتم
    private void removeItem() {
        String itemName = itemNameField.getText();

        if (itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً نام آیتم را وارد کنید.");
            return;
        }

        stockManager.removeItem(itemName);
        loadStockItems(); // بارگذاری مجدد آیتم‌ها
    }

    // جستجوی آیتم
    private void searchItem() {
        String keyword = searchKeywordField.getText();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً کلمه کلیدی را برای جستجو وارد کنید.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("نتایج جستجو برای: ").append(keyword).append("\n");
        boolean found = false;
        for (Stock item : stockManager.getStockList()) {
            if (item.getItemName().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append("آیتم: ").append(item.getItemName())
                  .append(" - تعداد: ").append(item.getQuantity())
                  .append(" ").append(item.getUnit()).append("\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("هیچ آیتمی با نام \"").append(keyword).append("\" پیدا نشد.\n");
        }
        stockTextArea.setText(sb.toString());
    }
}