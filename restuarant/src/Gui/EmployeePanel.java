package Gui;

import Manager.EmployeeManager;
import javax.swing.*;
import java.awt.*;

public class EmployeePanel extends JPanel {
    private EmployeeManager employeeManager;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField genderField;
    private JTextField phoneNumberField;
    private JTextField employeeIdField;
    private JTextField roleField;
    private JTextField salaryField;
    private JTextField searchField;
    private JTextArea employeeTextArea;

    public EmployeePanel() {
        this.employeeManager = new EmployeeManager();
        setLayout(new BorderLayout());

        // پنل ورودی‌ها
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 5, 5));

        inputPanel.add(new JLabel("نام:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("نام خانوادگی:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("جنسیت (مرد/زن):"));
        genderField = new JTextField();
        inputPanel.add(genderField);

        inputPanel.add(new JLabel("شماره تماس:"));
        phoneNumberField = new JTextField();
        inputPanel.add(phoneNumberField);

        inputPanel.add(new JLabel("شماره کارمندی:"));
        employeeIdField = new JTextField();
        inputPanel.add(employeeIdField);

        inputPanel.add(new JLabel("نقش (مثلاً گارسون/آشپز):"));
        roleField = new JTextField();
        inputPanel.add(roleField);

        inputPanel.add(new JLabel("حقوق:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        inputPanel.add(new JLabel("شماره کارمندی برای جستجو:"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        // دکمه‌ها
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        JButton addButton = new JButton("اضافه کردن کارمند");
        JButton removeButton = new JButton("حذف کارمند");
        JButton updateButton = new JButton("ویرایش کارمند");
        JButton updateSalaryButton = new JButton("تغییر حقوق");
        JButton searchButton = new JButton("جستجوی کارمند");
        JButton displayButton = new JButton("نمایش همه کارمندها");
        JButton saveButton = new JButton("ذخیره کارمندها");
        JButton loadButton = new JButton("لود کارمندها");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(updateSalaryButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        // JTextArea برای نمایش کارمندها
        employeeTextArea = new JTextArea(20, 40);
        employeeTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(employeeTextArea);

        // اضافه کردن کامپوننت‌ها به پنل
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // نمایش اولیه کارمندها
        loadEmployeesDisplay();

        // اکشن‌ها
        addButton.addActionListener(e -> addEmployee());
        removeButton.addActionListener(e -> removeEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        updateSalaryButton.addActionListener(e -> updateSalary());
        searchButton.addActionListener(e -> searchEmployee());
        displayButton.addActionListener(e -> loadEmployeesDisplay());
        saveButton.addActionListener(e -> saveEmployees());
        loadButton.addActionListener(e -> loadEmployees());
    }

    private void addEmployee() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderField.getText();
        String phoneNumber = phoneNumberField.getText();
        String employeeIdStr = employeeIdField.getText();
        String role = roleField.getText();
        String salaryStr = salaryField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty() ||
                employeeIdStr.isEmpty() || role.isEmpty() || salaryStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً تمام فیلدها را پر کنید.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            double salary = Double.parseDouble(salaryStr);
            employeeManager.addEmployee(firstName, lastName, gender, phoneNumber, employeeId, role, salary);
            loadEmployeesDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی و حقوق را به صورت عدد وارد کنید.");
        }
    }

    private void removeEmployee() {
        String employeeIdStr = employeeIdField.getText();
        if (employeeIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی را وارد کنید.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            employeeManager.removeEmployee(employeeId);
            loadEmployeesDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی را به صورت عدد وارد کنید.");
        }
    }

    private void updateEmployee() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderField.getText();
        String phoneNumber = phoneNumberField.getText();
        String employeeIdStr = employeeIdField.getText();
        String role = roleField.getText();
        String salaryStr = salaryField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty() ||
                employeeIdStr.isEmpty() || role.isEmpty() || salaryStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً تمام فیلدها را پر کنید.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            double salary = Double.parseDouble(salaryStr);
            employeeManager.updateEmployee(employeeId, firstName, lastName, gender, phoneNumber, role, salary);
            loadEmployeesDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی و حقوق را به صورت عدد وارد کنید.");
        }
    }

    private void updateSalary() {
        String employeeIdStr = employeeIdField.getText();
        String salaryStr = salaryField.getText();

        if (employeeIdStr.isEmpty() || salaryStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی و حقوق جدید را وارد کنید.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            double salary = Double.parseDouble(salaryStr);
            employeeManager.updateSalary(employeeId, salary);
            loadEmployeesDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی و حقوق را به صورت عدد وارد کنید.");
        }
    }

    private void searchEmployee() {
        String searchTerm = searchField.getText();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "لطفاً شماره کارمندی را برای جستجو وارد کنید.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("نتیجه جستجو برای شماره کارمندی: ").append(searchTerm).append("\n");
        employeeManager.searchEmployee(searchTerm);
        // خروجی متد searchEmployee به کنسول می‌ره، ما اینجا خروجی رو شبیه‌سازی می‌کنیم
        boolean found = false;
        for (Common.Employee employee : employeeManager.getEmployeeList()) {
            if (String.valueOf(employee.getEmployeeId()).equals(searchTerm)) {
                sb.append("کارمند پیدا شد: ")
                        .append("نام: ").append(employee.getFirstName()).append(" ").append(employee.getLastName())
                        .append(" | جنسیت: ").append(employee.getGender())
                        .append(" | شماره تماس: ").append(employee.getPhoneNumber())
                        .append(" | شماره کارمندی: ").append(employee.getEmployeeId())
                        .append(" | نقش: ").append(employee.getRole())
                        .append(" | حقوق: ").append(employee.getSalary())
                        .append("\n");
                found = true;
                break;
            }
        }
        if (!found) {
            sb.append("کارمندی با این مشخصات پیدا نشد!\n");
        }
        employeeTextArea.setText(sb.toString());
    }

    private void loadEmployeesDisplay() {
        StringBuilder sb = new StringBuilder();
        if (employeeManager.getEmployeeList().isEmpty()) {
            sb.append("هیچ کارمندی تو لیست نیست!\n");
        } else {
            sb.append("لیست کارمندهای رستوران:\n");
            for (Common.Employee employee : employeeManager.getEmployeeList()) {
                sb.append("نام: ").append(employee.getFirstName()).append(" ").append(employee.getLastName())
                        .append(" | جنسیت: ").append(employee.getGender())
                        .append(" | شماره تماس: ").append(employee.getPhoneNumber())
                        .append(" | شماره کارمندی: ").append(employee.getEmployeeId())
                        .append(" | نقش: ").append(employee.getRole())
                        .append(" | حقوق: ").append(employee.getSalary())
                        .append("\n");
            }
        }
        employeeTextArea.setText(sb.toString());
    }

    private void saveEmployees() {
        employeeManager.saveToFile();
        JOptionPane.showMessageDialog(this, "کارمندها ذخیره شدند.");
    }

    private void loadEmployees() {
        employeeManager.loadFromFile();
        loadEmployeesDisplay();
        JOptionPane.showMessageDialog(this, "کارمندها لود شدند.");
    }
}