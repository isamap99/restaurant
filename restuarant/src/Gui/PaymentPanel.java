package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Manager.PaymentManager;
import Common.Order;
import Common.Payment;

public class PaymentPanel extends JPanel {

    private PaymentManager paymentManager;
    private JTextArea textArea;
    private JTextField orderIdField;

    public PaymentPanel(PaymentManager manager) {
        this.paymentManager = manager;
        setLayout(new BorderLayout());

        // قسمت ورودی داده‌ها
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("شناسه سفارش:"));
        orderIdField = new JTextField();
        inputPanel.add(orderIdField);

        // دکمه‌ها
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton processButton = new JButton("پرداخت سفارش");
        JButton removeButton = new JButton("حذف پرداخت");
        JButton displayButton = new JButton("نمایش همه پرداخت‌ها");
        JButton updateButton = new JButton("به روز رسانی وضعیت");

        buttonPanel.add(processButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(updateButton);

        // ناحیه نمایش نتایج
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // اضافه کردن قسمت‌های مختلف به پنل
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // مدیریت رویدادها (ActionListeners)
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = Integer.parseInt(orderIdField.getText());
                Order order = new Order(orderId); // فرض می‌کنیم که یک شیء Order قبلاً ایجاد شده است
                paymentManager.processPayment(order);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = Integer.parseInt(orderIdField.getText());
                paymentManager.removePayment(orderId);
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentManager.displayAllPayments();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = Integer.parseInt(orderIdField.getText());
                boolean isSuccessful = JOptionPane.showConfirmDialog(null, "آیا پرداخت موفق بود؟", "وضعیت پرداخت", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                paymentManager.updatePaymentStatus(orderId, isSuccessful);
            }
        });
    }
}
