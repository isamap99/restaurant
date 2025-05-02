package Gui;

import Manager.InvoiceManager;
import Common.Order;
import Common.Customer;
import Common.Invoice;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class InvoicePanel extends JPanel {
    private InvoiceManager invoiceManager;
    private JTextArea outputArea;
    private JTextField orderIdField, customerNameField, newPriceField;

    public InvoicePanel(InvoiceManager manager) {
        this.invoiceManager = manager;
        setLayout(new BorderLayout());

        // پنل ورودی‌ها و دکمه‌ها
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        orderIdField = new JTextField();
        customerNameField = new JTextField();
        newPriceField = new JTextField();

        inputPanel.add(new JLabel("شماره سفارش:"));
        inputPanel.add(orderIdField);
        inputPanel.add(new JLabel("نام مشتری:"));
        inputPanel.add(customerNameField);
        inputPanel.add(new JLabel("مبلغ جدید:"));
        inputPanel.add(newPriceField);

        JButton createBtn = new JButton("ساخت فاکتور");
        JButton printAllBtn = new JButton("چاپ همه فاکتورها");
        JButton searchBtn = new JButton("جستجو فاکتور");
        JButton deleteBtn = new JButton("حذف فاکتور");
        JButton editBtn = new JButton("ویرایش مبلغ");
        JButton saveBtn = new JButton("ذخیره در فایل");
        JButton loadBtn = new JButton("بارگذاری از فایل");

        inputPanel.add(createBtn);
        inputPanel.add(printAllBtn);
        inputPanel.add(searchBtn);
        inputPanel.add(deleteBtn);
        inputPanel.add(editBtn);
        inputPanel.add(saveBtn);
        inputPanel.add(loadBtn);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // دکمه ساخت فاکتور
        createBtn.addActionListener(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());

                // گرفتن سفارش از سیستم (باید پیاده‌سازی شده باشد)
                Order order = invoiceManager.getOrderById(orderId);

                if (order == null) {
                    outputArea.append("⚠️ سفارش با این شماره پیدا نشد.\n");
                    return;
                }

                invoiceManager.createInvoice(order);
                outputArea.append("✅ فاکتور ساخته شد.\n");

            } catch (Exception ex) {
                outputArea.append("⚠️ خطا در ساخت فاکتور.\n");
            }
        });


        // دکمه چاپ همه
        printAllBtn.addActionListener(e -> {
            outputArea.setText("");
            if (invoiceManager.getInvoices().isEmpty()) {
                outputArea.setText("هیچ فاکتوری وجود ندارد.\n");
            } else {
                for (Invoice inv : invoiceManager.getInvoices()) {
                    outputArea.append(inv.toString() + "\n------------------\n");
                }
            }
        });

        // دکمه جستجو
        searchBtn.addActionListener(e -> {
            try {
                int orderId = parseIntSafe(orderIdField.getText());
                String name = customerNameField.getText();
                outputArea.setText("");
                boolean found = false;
                for (Invoice inv : invoiceManager.getInvoices()) {
                    if (inv.getOrder().getOrderId() == orderId ||
                        inv.getOrder().getCustomer().getName().equalsIgnoreCase(name)) {
                        outputArea.append("✅ فاکتور پیدا شد:\n" + inv.toString() + "\n");
                        found = true;
                        break;
                    }
                }
                if (!found)
                    outputArea.append("⚠️ فاکتور پیدا نشد.\n");
            } catch (Exception ex) {
                outputArea.append("⚠️ خطا در جستجو.\n");
            }
        });

        // دکمه حذف
        deleteBtn.addActionListener(e -> {
            int orderId = parseIntSafe(orderIdField.getText());
            String name = customerNameField.getText();
            invoiceManager.removeInvoice(orderId, name);
            outputArea.append("⛔ تلاش برای حذف فاکتور انجام شد.\n");
        });

        // دکمه ویرایش مبلغ
        editBtn.addActionListener(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                double newPrice = Double.parseDouble(newPriceField.getText());
                boolean success = invoiceManager.editInvoice(orderId, newPrice);
                outputArea.append(success ? "✅ فاکتور ویرایش شد.\n" : "⚠️ فاکتور یافت نشد.\n");
            } catch (Exception ex) {
                outputArea.append("⚠️ خطا در ورودی برای ویرایش.\n");
            }
        });

        // دکمه ذخیره در فایل
        saveBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                invoiceManager.saveToFile();
                outputArea.append("💾 فاکتورها ذخیره شدند.\n");
            }
        });

        // دکمه بارگذاری از فایل
        loadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                invoiceManager.loadFromFile();
                outputArea.append("📥 فاکتورها بارگذاری شدند.\n");
            }
        });
    }

    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
