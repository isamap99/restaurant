package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Manager.CustomerManager;
import Common.Customer;

public class CustomerPanel extends JPanel {

    private CustomerManager customerManager;
    private JTextArea textArea;
    private JTextField nameField, familyField, phoneField, addressField;

    public CustomerPanel(CustomerManager manager) {
        this.customerManager = manager;
        setLayout(new BorderLayout());

        // قسمت ورودی داده‌ها
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("نام:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("نام‌خانوادگی:"));
        familyField = new JTextField();
        inputPanel.add(familyField);

        inputPanel.add(new JLabel("شماره تلفن:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("آدرس:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        // دکمه‌ها
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("افزودن مشتری");
        JButton searchButton = new JButton("جستجو");
        JButton updateButton = new JButton("ویرایش");
        JButton deleteButton = new JButton("حذف");
        JButton displayButton = new JButton("نمایش همه");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);

        // ناحیه نمایش نتایج
        textArea = new JTextArea(10, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // اضافه کردن به پنل اصلی
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // رویداد افزودن مشتری
        addButton.addActionListener(e -> {
            Customer customer = new Customer();
            customer.setName(nameField.getText());
            customer.setFamily(familyField.getText());
            customer.setPhone(phoneField.getText());
            customer.setAddress(addressField.getText());
            customerManager.addCustomer(customer);
            textArea.append("✅ مشتری افزوده شد:\n" + customer.toString() + "\n\n");
            clearFields();
        });

        // رویداد جستجو بر اساس شماره تلفن یا نام و نام‌خانوادگی
        searchButton.addActionListener(e -> {
            String phone = phoneField.getText();
            String name = nameField.getText();
            String family = familyField.getText();
            textArea.setText("");

            if (!phone.isEmpty()) {
                Customer c = customerManager.findCustomerByPhone(phone);
                if (c != null)
                    textArea.setText("📞 مشتری یافت شد:\n" + c.toString());
                else
                    textArea.setText("❌ مشتری با این شماره یافت نشد.");
            } else if (!name.isEmpty() && !family.isEmpty()) {
                Customer[] results = customerManager.findCustomerByNameAndFamily(name, family);
                if (results.length > 0) {
                    textArea.append("👥 نتایج جستجو:\n");
                    for (Customer c : results)
                        textArea.append(c.toString() + "\n");
                } else {
                    textArea.setText("❌ مشتری با این نام و نام‌خانوادگی یافت نشد.");
                }
            } else {
                textArea.setText("⚠ لطفاً شماره یا نام و نام‌خانوادگی را وارد کنید.");
            }
        });

        // رویداد ویرایش مشتری بر اساس شماره تلفن
        updateButton.addActionListener(e -> {
            String phone = phoneField.getText();
            if (phone.isEmpty()) {
                textArea.setText("⚠ لطفاً شماره تلفن مشتری فعلی را برای ویرایش وارد کنید.");
                return;
            }

            Customer existing = customerManager.findCustomerByPhone(phone);
            if (existing == null) {
                textArea.setText("❌ مشتری با این شماره یافت نشد.");
                return;
            }

            Customer newCustomer = new Customer();
            newCustomer.setName(nameField.getText());
            newCustomer.setFamily(familyField.getText());
            newCustomer.setPhone(phoneField.getText()); // شماره جدید می‌تواند یکسان باشد یا تغییر کرده باشد
            newCustomer.setAddress(addressField.getText());

            customerManager.updateCustomerByPhone(phone, newCustomer);
            textArea.setText("🔄 مشتری ویرایش شد:\n" + newCustomer.toString());
        });

        // رویداد حذف مشتری بر اساس شماره تلفن
        deleteButton.addActionListener(e -> {
            String phone = phoneField.getText();
            if (phone.isEmpty()) {
                textArea.setText("⚠ لطفاً شماره تلفن برای حذف وارد کنید.");
                return;
            }

            Customer found = customerManager.findCustomerByPhone(phone);
            if (found != null) {
                customerManager.removeCustomerByPhone(phone);
                textArea.setText("🗑 مشتری حذف شد:\n" + found.toString());
                clearFields();
            } else {
                textArea.setText("❌ مشتری با این شماره یافت نشد.");
            }
        });

        // رویداد نمایش همه مشتری‌ها
        displayButton.addActionListener(e -> {
            Customer[] customers = customerManager.getAllCustomers();
            textArea.setText("📋 لیست همه مشتری‌ها:\n");
            for (Customer c : customers) {
                if (c != null)
                    textArea.append(c.toString() + "\n");
            }
        });
    }

    // پاک‌کردن فیلدهای ورودی
    private void clearFields() {
        nameField.setText("");
        familyField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }
}
 