package Gui;

import Manager.TableManager;
import Common.Customer;
import Common.Table;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablePanel extends JPanel {
    private TableManager tableManager;
    private JTextField tableNumberField, seatCountField;
    private JTextArea outputArea;

    public TablePanel(TableManager tableManager) {
        this.tableManager = tableManager;
        setLayout(new BorderLayout());

        // Header Panel (for inputs)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        // Table Number Field
        tableNumberField = new JTextField(10);
        inputPanel.add(new JLabel("میز شماره:"));
        inputPanel.add(tableNumberField);

        // Seat Count Field
        seatCountField = new JTextField(10);
        inputPanel.add(new JLabel("تعداد صندلی:"));
        inputPanel.add(seatCountField);

        // Adding inputPanel to the top part of the layout
        add(inputPanel, BorderLayout.NORTH);

        // Action Panel (for buttons)
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        // Buttons for actions
        JButton addButton = new JButton("افزودن میز");
        JButton removeButton = new JButton("حذف میز");
        JButton updateButton = new JButton("ویرایش میز");
        JButton showButton = new JButton("نمایش همه میزها");
        JButton reserveButton = new JButton("رزرو میز");
        JButton cancelReservationButton = new JButton("لغو رزرو");
        JButton getCustomerButton = new JButton("دریافت مشتری میز");
        JButton getTableButton = new JButton("دریافت میز مشتری");
        JButton freeTableButton = new JButton("آزاد کردن میز");

        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(updateButton);
        actionPanel.add(showButton);
        actionPanel.add(reserveButton);
        actionPanel.add(cancelReservationButton);
        actionPanel.add(getCustomerButton);
        actionPanel.add(getTableButton);
        actionPanel.add(freeTableButton);

        // Output Area
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setLineWrap(true);

        // Adding components to main panel
        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER); // Place actionPanel at the bottom
        add(scrollPane, BorderLayout.SOUTH);
        // Action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTable();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllTables();
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveTable();
            }
        });

        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelReservation();
            }
        });

        getCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCustomerOfTable();
            }
        });

        getTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTableOfCustomer();
            }
        });

        freeTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                freeTable();
            }
        });
    }

    private void addTable() {
        String tableNumber = tableNumberField.getText();
        String seatCountStr = seatCountField.getText();
        if (tableNumber.isEmpty() || seatCountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا تمامی فیلدها را پر کنید.");
            return;
        }

        try {
            int seatCount = Integer.parseInt(seatCountStr);
            Table table = new Table(tableNumber, seatCount);
            tableManager.addTable(table);
            outputArea.append("میز " + tableNumber + " با تعداد صندلی " + seatCount + " اضافه شد.\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "تعداد صندلی باید عدد صحیح باشد.");
        }
    }

    private void removeTable() {
        String tableNumber = tableNumberField.getText();
        if (tableNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا شماره میز را وارد کنید.");
            return;
        }
        tableManager.removeTableByNumber(tableNumber);
        outputArea.append("میز " + tableNumber + " حذف شد.\n");
    }

    private void updateTable() {
        String tableNumber = tableNumberField.getText();
        String seatCountStr = seatCountField.getText();
        if (tableNumber.isEmpty() || seatCountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا تمامی فیلدها را پر کنید.");
            return;
        }

        try {
            int seatCount = Integer.parseInt(seatCountStr);
            Table updatedTable = new Table(tableNumber, seatCount);
            tableManager.updateTableByNumber(tableNumber, updatedTable);
            outputArea.append("میز " + tableNumber + " با تعداد صندلی " + seatCount + " به روز شد.\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "تعداد صندلی باید عدد صحیح باشد.");
        }
    }

    private void showAllTables() {
        Table[] allTables = tableManager.getAllTables();
        outputArea.setText(""); // Clear previous output

        for (Table table : allTables) {
            outputArea.append(table.toString() + "\n");
        }
    }

    private void reserveTable() {
        String tableNumber = tableNumberField.getText();
        String customerName = JOptionPane.showInputDialog("نام مشتری:");
        String customerFamily = JOptionPane.showInputDialog("نام خانوادگی مشتری:");
        String customerPhone = JOptionPane.showInputDialog("شماره تماس مشتری:");

        if (tableNumber.isEmpty() || customerName == null || customerFamily == null || customerPhone == null) {
            JOptionPane.showMessageDialog(this, "لطفا تمامی اطلاعات را وارد کنید.");
            return;
        }

        Customer customer = new Customer();
        tableManager.reserveTable(tableNumber, customer);
        outputArea.append("رزرو برای میز " + tableNumber + " با مشتری " + customerName + " " + customerFamily + " انجام شد.\n");
    }

    private void cancelReservation() {
        String tableNumber = tableNumberField.getText();
        if (tableNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا شماره میز را وارد کنید.");
            return;
        }
        tableManager.cancelReservation(tableNumber);
        outputArea.append("رزرو برای میز " + tableNumber + " لغو شد.\n");
    }

    private void getCustomerOfTable() {
        String tableNumber = tableNumberField.getText();
        if (tableNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا شماره میز را وارد کنید.");
            return;
        }
        tableManager.getCustomerOfTable(tableNumber);
    }

    private void getTableOfCustomer() {
        String customerPhone = JOptionPane.showInputDialog("شماره تماس مشتری:");
        if (customerPhone == null) {
            JOptionPane.showMessageDialog(this, "لطفا شماره تماس مشتری را وارد کنید.");
            return;
        }
        tableManager.getTableOfCustomer(customerPhone);
    }

    private void freeTable() {
        String tableNumber = tableNumberField.getText();
        if (tableNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفا شماره میز را وارد کنید.");
            return;
        }
        tableManager.freeTable(tableNumber);
        outputArea.append("میز " + tableNumber + " آزاد شد.\n");
    }
}
