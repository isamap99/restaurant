package Gui;

import Common.Order;
import Manager.OrderManager;
import Manager.menuManager;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    private OrderManager orderManager;
    private JTextField orderIdField;
    private JTextField itemTypeField;
    private JTextField itemNameField;
    private JTextField quantityField;
    private JTextField discountField;
    private JTextField searchOrderField;
    private JTextArea orderTextArea;

    public OrderPanel(menuManager menuManager) {
        this.orderManager = new OrderManager(menuManager);
        setLayout(new BorderLayout());

        // پنل ورودی‌ها
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        // شماره سفارش برای همه عملیات‌ها
        inputPanel.add(new JLabel("شماره سفارش:"));
        orderIdField = new JTextField();
        inputPanel.add(orderIdField);

        inputPanel.add(new JLabel("نوع آیتم (food, appetizer, dessert, drink):"));
        itemTypeField = new JTextField();
        inputPanel.add(itemTypeField);

        inputPanel.add(new JLabel("نام آیتم:"));
        itemNameField = new JTextField();
        inputPanel.add(itemNameField);

        inputPanel.add(new JLabel("تعداد:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("تخفیف (%):"));
        discountField = new JTextField();
        inputPanel.add(discountField);

        inputPanel.add(new JLabel("شماره سفارش برای جستجو:"));
        searchOrderField = new JTextField();
        inputPanel.add(searchOrderField);

        // دکمه‌ها
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        JButton createOrderButton = new JButton("ایجاد سفارش جدید");
        JButton addItemButton = new JButton("اضافه کردن آیتم به سفارش");
        JButton removeItemButton = new JButton("حذف آیتم از سفارش");
        JButton applyDiscountButton = new JButton("اعمال تخفیف");
        JButton removeOrderButton = new JButton("حذف سفارش");
        JButton searchOrderButton = new JButton("جستجوی سفارش");
        JButton displayOrdersButton = new JButton("نمایش همه سفارش‌ها");
        JButton saveOrdersButton = new JButton("ذخیره سفارش‌ها");
        JButton loadOrdersButton = new JButton("لود سفارش‌ها");

        buttonPanel.add(createOrderButton);
        buttonPanel.add(addItemButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(applyDiscountButton);
        buttonPanel.add(removeOrderButton);
        buttonPanel.add(searchOrderButton);
        buttonPanel.add(displayOrdersButton);
        buttonPanel.add(saveOrdersButton);
        buttonPanel.add(loadOrdersButton);

        // JTextArea برای نمایش سفارش‌ها
        orderTextArea = new JTextArea(20, 40);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        // اضافه کردن کامپوننت‌ها به پنل
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // نمایش اولیه سفارش‌ها
        loadOrdersDisplay();

        // اکشن‌ها
        createOrderButton.addActionListener(e -> createOrder());
        addItemButton.addActionListener(e -> addItemToOrder());
        removeItemButton.addActionListener(e -> removeItemFromOrder());
        applyDiscountButton.addActionListener(e -> applyDiscount());
        removeOrderButton.addActionListener(e -> removeOrder());
        searchOrderButton.addActionListener(e -> searchOrder());
        displayOrdersButton.addActionListener(e -> loadOrdersDisplay());
        saveOrdersButton.addActionListener(e -> saveOrders());
        loadOrdersButton.addActionListener(e -> loadOrders());
    }

    private void createOrder() {
        String orderIdStr = orderIdField.getText();
        if (orderIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش را وارد کنید.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            if (orderId <= 0) {
                JOptionPane.showMessageDialog(this, "شماره سفارش باید یک عدد مثبت باشد.");
                return;
            }

            // چک کردن اینکه شماره سفارش قبلاً وجود نداشته باشد
            if (orderManager.findOrder(orderId) != null) {
                JOptionPane.showMessageDialog(this, "سفارش با شماره " + orderId + " قبلاً وجود دارد.");
                return;
            }

            Order order = orderManager.createOrder(orderId);
            StringBuilder sb = new StringBuilder();
            sb.append("سفارش جدید با شماره ").append(order.getOrderId()).append(" ایجاد شد.\n");
            sb.append("آیتم‌ها:\n");
            if (order.getItems().isEmpty()) {
                sb.append("هیچ آیتمی ثبت نشده است.\n");
            } else {
                for (Order.OrderItem item : order.getItems()) {
                    sb.append("- ").append(item.getName()).append(": ").append(item.getQuantity()).append(" عدد\n");
                }
            }
            sb.append("-------------------\n");
            orderTextArea.setText(sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً یک شماره سفارش معتبر (عدد) وارد کنید.");
        }
    }

    private void addItemToOrder() {
        String orderIdStr = orderIdField.getText();
        String itemType = itemTypeField.getText();
        String itemName = itemNameField.getText();
        String quantityStr = quantityField.getText();

        if (orderIdStr.isEmpty() || itemType.isEmpty() || itemName.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً تمام فیلدها را پر کنید.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            int quantity = Integer.parseInt(quantityStr);
            boolean success = orderManager.addItemToOrder(orderId, itemName, itemType, quantity);
            if (success) {
                Order order = orderManager.findOrder(orderId);
                StringBuilder sb = new StringBuilder();
                sb.append("سفارش شماره: ").append(order.getOrderId()).append("\n");
                sb.append("آیتم‌ها:\n");
                if (order.getItems().isEmpty()) {
                    sb.append("هیچ آیتمی ثبت نشده است.\n");
                } else {
                    for (Order.OrderItem item : order.getItems()) {
                        sb.append("- ").append(item.getName()).append(": ").append(item.getQuantity()).append(" عدد\n");
                    }
                }
                sb.append("-------------------\n");
                orderTextArea.setText(sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "خطا در اضافه کردن آیتم. لطفاً اطلاعات را بررسی کنید.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش و تعداد را به صورت عدد وارد کنید.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "خطا در اضافه کردن آیتم: " + e.getMessage());
        }
    }

    private void removeItemFromOrder() {
        String orderIdStr = orderIdField.getText();
        String itemName = itemNameField.getText();

        if (orderIdStr.isEmpty() || itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش و نام آیتم را وارد کنید.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            boolean success = orderManager.removeItemFromOrder(orderId, itemName);
            if (success) {
                Order order = orderManager.findOrder(orderId);
                StringBuilder sb = new StringBuilder();
                sb.append("سفارش شماره: ").append(order.getOrderId()).append("\n");
                sb.append("آیتم‌ها:\n");
                if (order.getItems().isEmpty()) {
                    sb.append("هیچ آیتمی ثبت نشده است.\n");
                } else {
                    for (Order.OrderItem item : order.getItems()) {
                        sb.append("- ").append(item.getName()).append(": ").append(item.getQuantity()).append(" عدد\n");
                    }
                }
                sb.append("-------------------\n");
                orderTextArea.setText(sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "خطا در حذف آیتم. لطفاً اطلاعات را بررسی کنید.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش را به صورت عدد وارد کنید.");
        }
    }

    private void applyDiscount() {
        String orderIdStr = orderIdField.getText();
        String discountStr = discountField.getText();

        if (orderIdStr.isEmpty() || discountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش و درصد تخفیف را وارد کنید.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            double discount = Double.parseDouble(discountStr);
            Order order = orderManager.findOrder(orderId);
            if (order == null) {
                JOptionPane.showMessageDialog(this, "سفارش با شماره " + orderId + " پیدا نشد.");
                return;
            }
            order.applyDiscount(discount);
            StringBuilder sb = new StringBuilder();
            sb.append("سفارش شماره: ").append(order.getOrderId()).append("\n");
            sb.append("آیتم‌ها:\n");
            if (order.getItems().isEmpty()) {
                sb.append("هیچ آیتمی ثبت نشده است.\n");
            } else {
                for (Order.OrderItem item : order.getItems()) {
                    sb.append("- ").append(item.getName()).append(": ").append(item.getQuantity()).append(" عدد\n");
                }
            }
            sb.append("تخفیف: ").append(order.getDiscount()).append("%\n");
            sb.append("-------------------\n");
            orderTextArea.setText(sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش و درصد تخفیف را به صورت عدد وارد کنید.");
        }
    }

    private void removeOrder() {
        String orderIdStr = orderIdField.getText();
        if (orderIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش را وارد کنید.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            boolean success = orderManager.removeOrder(orderId);
            if (success) {
                loadOrdersDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "خطا در حذف سفارش. لطفاً شماره سفارش را بررسی کنید.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش را به صورت عدد وارد کنید.");
        }
    }

    private void searchOrder() {
        String orderIdStr = searchOrderField.getText();
        if (orderIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش را برای جستجو وارد کنید.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderManager.findOrder(orderId);
            if (order == null) {
                orderTextArea.setText("سفارش با شماره " + orderId + " پیدا نشد.\n");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=== سفارش شماره: ").append(order.getOrderId()).append(" ===\n");
            sb.append("آیتم‌ها:\n");
            if (order.getItems().isEmpty()) {
                sb.append("هیچ آیتمی ثبت نشده است.\n");
            } else {
                for (Order.OrderItem item : order.getItems()) {
                    sb.append("- ").append(item.getName()).append(": ").append(item.getQuantity()).append(" عدد\n");
                }
            }
            sb.append("تخفیف: ").append(order.getDiscount()).append("%\n");
            sb.append("-------------------\n");
            orderTextArea.setText(sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره سفارش را به صورت عدد وارد کنید.");
        }
    }

    private void loadOrdersDisplay() {
        StringBuilder sb = new StringBuilder();
        if (orderManager.getOrders().isEmpty()) {
            sb.append("هیچ سفارشی ثبت نشده است.\n");
        } else {
            sb.append("=== لیست سفارش‌ها ===\n");
            for (Order order : orderManager.getOrders()) {
                sb.append("سفارش شماره: ").append(order.getOrderId()).append("\n");
                sb.append("آیتم‌ها:\n");
                if (order.getItems().isEmpty()) {
                    sb.append("هیچ آیتمی ثبت نشده است.\n");
                } else {
                    for (Order.OrderItem item : order.getItems()) {
                        sb.append("- ").append(item.getName()).append(": ").append(item.getQuantity()).append(" عدد\n");
                    }
                }
                sb.append("تخفیف: ").append(order.getDiscount()).append("%\n");
                sb.append("-------------------\n");
            }
        }
        orderTextArea.setText(sb.toString());
    }

    private void saveOrders() {
        orderManager.saveOrders();
        JOptionPane.showMessageDialog(this, "سفارش‌ها ذخیره شدند.");
    }

    private void loadOrders() {
        orderManager.loadOrders();
        loadOrdersDisplay();
        JOptionPane.showMessageDialog(this, "سفارش‌ها لود شدند.");
    }
}