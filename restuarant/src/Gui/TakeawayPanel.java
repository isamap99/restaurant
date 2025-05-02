package Gui;

import Common.Takeaway;
import Common.Customer;
import Manager.TakeawayManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TakeawayPanel extends JPanel {
    private TakeawayManager takeawayManager;
    private JTextArea displayArea;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton displayButton;
    private JButton markDeliveredButton;
    private JButton searchButton;
    private JButton saveButton;
    private JButton loadButton;
    private static final String FILE_PATH = "takeaways.txt";

    public TakeawayPanel() {
        ensureFileExists();
        takeawayManager = new TakeawayManager();
        setLayout(new BorderLayout());

        displayArea = new JTextArea(20, 40);
        displayArea.setEditable(false);
        displayArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("اضافه کردن سفارش");
        updateButton = new JButton("ویرایش سفارش");
        deleteButton = new JButton("حذف سفارش");
        displayButton = new JButton("نمایش همه سفارش‌ها");
        markDeliveredButton = new JButton("علامت‌گذاری تحویل‌شده");
        searchButton = new JButton("جستجوی سفارش");
        saveButton = new JButton("ذخیره");
        loadButton = new JButton("لود");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(markDeliveredButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.SOUTH);

        displayAllTakeaways();

        addButton.addActionListener(e -> addTakeaway());
        updateButton.addActionListener(e -> updateTakeaway());
        deleteButton.addActionListener(e -> deleteTakeaway());
        displayButton.addActionListener(e -> displayAllTakeaways());
        markDeliveredButton.addActionListener(e -> markAsDelivered());
        searchButton.addActionListener(e -> searchTakeaway());
        saveButton.addActionListener(e -> saveTakeaways());
        loadButton.addActionListener(e -> loadTakeaways());
    }

    private void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "خطا در ایجاد فایل takeaways.txt: " + e.getMessage());
            }
        }
    }

    private void displayAllTakeaways() {
        ArrayList<Takeaway> takeaways = takeawayManager.getAllTakeaways();
        displayArea.setText("");
        for (Takeaway t : takeaways) {
            displayArea.append(t.toString() + "\n");
        }
    }

    private void addTakeaway() {
        JTextField idField = new JTextField(5);
        JTextField orderIdField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField familyField = new JTextField(10);
        JTextField cityField = new JTextField("بناب", 20);
        cityField.setEditable(false); // شهر غیرقابل ویرایش
        JTextField homeAddressField = new JTextField(20);
        JTextField phoneField = new JTextField(15);
        JTextField timeField = new JTextField(10);
        JTextField feeField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panel.add(new JLabel("شناسه سفارش بیرون‌بر:"));
        panel.add(idField);
        panel.add(new JLabel("شناسه سفارش:"));
        panel.add(orderIdField);
        panel.add(new JLabel("نام مشتری:"));
        panel.add(nameField);
        panel.add(new JLabel("نام خانوادگی:"));
        panel.add(familyField);
        panel.add(new JLabel("شهر (فقط بناب):"));
        panel.add(cityField);
        panel.add(new JLabel("آدرس خانه:"));
        panel.add(homeAddressField);
        panel.add(new JLabel("شماره تماس (09xxxxxxxxx):"));
        panel.add(phoneField);
        panel.add(new JLabel("زمان تحویل:"));
        panel.add(timeField);
        panel.add(new JLabel("هزینه ارسال:"));
        panel.add(feeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "اضافه کردن سفارش جدید", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int takeawayId = Integer.parseInt(idField.getText().trim());
                int orderId = Integer.parseInt(orderIdField.getText().trim());
                String customerName = nameField.getText().trim();
                String customerFamilyName = familyField.getText().trim();
                String city = cityField.getText().trim();
                String homeAddress = homeAddressField.getText().trim();
                String phoneNumber = phoneField.getText().trim();
                String deliveryTime = timeField.getText().trim();
                double deliveryCost = Double.parseDouble(feeField.getText().trim());

                // اعتبارسنجی شهر
                if (!city.equals("بناب")) {
                    JOptionPane.showMessageDialog(this, "شهر فقط باید 'بناب' باشد.");
                    return;
                }

                // اعتبارسنجی شماره تلفن
                if (!phoneNumber.matches("^09\\d{9}$")) {
                    JOptionPane.showMessageDialog(this, "شماره تماس نامعتبر است. باید با 09 شروع شود و 11 رقم باشد.");
                    return;
                }

                Customer customer = new Customer(customerName, customerFamilyName, city, homeAddress, phoneNumber);
                Takeaway newTakeaway = new Takeaway(takeawayId, orderId, customer, deliveryTime, deliveryCost);
                takeawayManager.addTakeaway(newTakeaway);
                displayAllTakeaways();
                JOptionPane.showMessageDialog(this, "سفارش با موفقیت اضافه شد!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "لطفاً مقادیر عددی (شناسه، سفارش، هزینه) را درست وارد کنید!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "خطا در اضافه کردن سفارش: " + e.getMessage());
            }
        }
    }

    private void updateTakeaway() {
        String idStr = JOptionPane.showInputDialog(this, "شناسه سفارش برای ویرایش:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                ArrayList<Takeaway> takeaways = takeawayManager.getAllTakeaways();
                Takeaway toUpdate = null;
                for (Takeaway t : takeaways) {
                    if (t.getTakeawayId() == id) {
                        toUpdate = t;
                        break;
                    }
                }
                if (toUpdate == null) {
                    JOptionPane.showMessageDialog(this, "سفارش با این شناسه یافت نشد!");
                    return;
                }

                JTextField idField = new JTextField(String.valueOf(toUpdate.getTakeawayId()), 5);
                JTextField orderIdField = new JTextField(String.valueOf(toUpdate.getOrderId()), 5);
                JTextField nameField = new JTextField(toUpdate.getCustomer().getName(), 10);
                JTextField familyField = new JTextField(toUpdate.getCustomer().getFamily(), 10);
                JTextField cityField = new JTextField(toUpdate.getCustomer().getCity(), 20);
                cityField.setEditable(false); // شهر غیرقابل ویرایش
                JTextField homeAddressField = new JTextField(toUpdate.getCustomer().getHomeAddress(), 20);
                JTextField phoneField = new JTextField(toUpdate.getCustomer().getPhone(), 15);
                JTextField timeField = new JTextField(toUpdate.getDeliveryTime(), 10);
                JTextField feeField = new JTextField(String.valueOf(toUpdate.getDeliveryCost()), 5);

                JPanel panel = new JPanel(new GridLayout(0, 2));
                panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                panel.add(new JLabel("شناسه سفارش بیرون‌بر:"));
                panel.add(idField);
                panel.add(new JLabel("شناسه سفارش:"));
                panel.add(orderIdField);
                panel.add(new JLabel("نام مشتری:"));
                panel.add(nameField);
                panel.add(new JLabel("نام خانوادگی:"));
                panel.add(familyField);
                panel.add(new JLabel("شهر (فقط بناب):"));
                panel.add(cityField);
                panel.add(new JLabel("آدرس خانه:"));
                panel.add(homeAddressField);
                panel.add(new JLabel("شماره تماس (09xxxxxxxxx):"));
                panel.add(phoneField);
                panel.add(new JLabel("زمان تحویل:"));
                panel.add(timeField);
                panel.add(new JLabel("هزینه ارسال:"));
                panel.add(feeField);

                int result = JOptionPane.showConfirmDialog(this, panel, "ویرایش سفارش", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int takeawayId = Integer.parseInt(idField.getText().trim());
                        int orderId = Integer.parseInt(orderIdField.getText().trim());
                        String customerName = nameField.getText().trim();
                        String customerFamilyName = familyField.getText().trim();
                        String city = cityField.getText().trim();
                        String homeAddress = homeAddressField.getText().trim();
                        String phoneNumber = phoneField.getText().trim();
                        String deliveryTime = timeField.getText().trim();
                        double deliveryCost = Double.parseDouble(feeField.getText().trim());

                        // اعتبارسنجی شهر
                        if (!city.equals("بناب")) {
                            JOptionPane.showMessageDialog(this, "شهر فقط باید 'بناب' باشد.");
                            return;
                        }

                        // اعتبارسنجی شماره تلفن
                        if (!phoneNumber.matches("^09\\d{9}$")) {
                            JOptionPane.showMessageDialog(this, "شماره تماس نامعتبر است. باید با 09 شروع شود و 11 رقم باشد.");
                            return;
                        }

                        Customer customer = new Customer(customerName, customerFamilyName, city, homeAddress, phoneNumber);
                        Takeaway updatedTakeaway = new Takeaway(takeawayId, orderId, customer, deliveryTime, deliveryCost);
                        takeawayManager.updateTakeaway(id, updatedTakeaway);
                        displayAllTakeaways();
                        JOptionPane.showMessageDialog(this, "سفارش با موفقیت ویرایش شد!");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "لطفاً مقادیر عددی (شناسه، سفارش، هزینه) را درست وارد کنید!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "خطا در ویرایش سفارش: " + e.getMessage());
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "لطفاً شناسه را به‌صورت عدد وارد کنید!");
            }
        }
    }

    private void deleteTakeaway() {
        String idStr = JOptionPane.showInputDialog(this, "شناسه سفارش برای حذف:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                takeawayManager.deleteTakeaway(id);
                displayAllTakeaways();
                JOptionPane.showMessageDialog(this, "سفارش با موفقیت حذف شد!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "لطفاً یک عدد معتبر وارد کنید!");
            }
        }
    }

    private void markAsDelivered() {
        String idStr = JOptionPane.showInputDialog(this, "شناسه سفارش برای علامت‌گذاری به‌عنوان تحویل‌شده:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                ArrayList<Takeaway> takeaways = takeawayManager.getAllTakeaways();
                Takeaway toMark = null;
                for (Takeaway t : takeaways) {
                    if (t.getTakeawayId() == id) {
                        toMark = t;
                        break;
                    }
                }
                if (toMark == null) {
                    JOptionPane.showMessageDialog(this, "سفارش با این شناسه یافت نشد!");
                    return;
                }
                if (toMark.isDelivered()) {
                    JOptionPane.showMessageDialog(this, "این سفارش قبلاً تحویل‌شده است!");
                    return;
                }
                toMark.markDelivered();
                takeawayManager.updateTakeaway(id, toMark);
                displayAllTakeaways();
                JOptionPane.showMessageDialog(this, "سفارش با موفقیت به‌عنوان تحویل‌شده علامت‌گذاری شد!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "لطفاً شناسه را به‌صورت عدد وارد کنید!");
            }
        }
    }

    private void searchTakeaway() {
        String idStr = JOptionPane.showInputDialog(this, "شناسه سفارش برای جستجو:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                ArrayList<Takeaway> takeaways = takeawayManager.getAllTakeaways();
                Takeaway found = null;
                for (Takeaway t : takeaways) {
                    if (t.getTakeawayId() == id) {
                        found = t;
                        break;
                    }
                }
                if (found == null) {
                    JOptionPane.showMessageDialog(this, "سفارش با این شناسه یافت نشد!");
                    return;
                }
                displayArea.setText(found.toString());
                JOptionPane.showMessageDialog(this, "سفارش با موفقیت پیدا شد!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "لطفاً شناسه را به‌صورت عدد وارد کنید!");
            }
        }
    }

    private void saveTakeaways() {
        try {
            takeawayManager.saveAllTakeaways();
            JOptionPane.showMessageDialog(this, "سفارش‌ها با موفقیت ذخیره شدند!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در ذخیره سفارش‌ها: " + e.getMessage());
        }
    }

    private void loadTakeaways() {
        try {
            takeawayManager.loadTakeaways();
            displayAllTakeaways();
            JOptionPane.showMessageDialog(this, "سفارش‌ها با موفقیت لود شدند!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در لود سفارش‌ها: " + e.getMessage());
        }
    }
}