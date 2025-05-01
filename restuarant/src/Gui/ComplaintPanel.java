package Gui;

import Common.Complaint;
import Manager.ComplaintManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComplaintPanel extends JPanel {
    private JTextArea complaintTextArea;
    private ComplaintManager complaintManager;
    private JTextField tableNumberField;
    private JTextField dateField;
    private JTextField complaintDetailsField;

    public ComplaintPanel() {
        // ایجاد مدیریت شکایات
        complaintManager = new ComplaintManager();

        // تنظیم Layout برای پنل
        setLayout(new BorderLayout());

        // ایجاد فیلدهای ورودی برای شماره میز، تاریخ و جزئیات شکایت
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        tableNumberField = new JTextField();
        dateField = new JTextField();
        complaintDetailsField = new JTextField();

        inputPanel.add(new JLabel("شماره میز:"));
        inputPanel.add(tableNumberField);
        inputPanel.add(new JLabel("تاریخ شکایت:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("جزئیات شکایت:"));
        inputPanel.add(complaintDetailsField);

        // دکمه‌های اضافی برای افزودن، ویرایش، حذف، جستجو و نمایش همه شکایت‌ها
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("افزودن شکایت");
        JButton editButton = new JButton("ویرایش شکایت");
        JButton deleteButton = new JButton("حذف شکایت");
        JButton searchButton = new JButton("جستجوی شکایت");
        JButton showAllButton = new JButton("نمایش همه شکایت‌ها");
        JButton changeStatusButton = new JButton("تغییر وضعیت به حل شد");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(changeStatusButton);

        // استفاده از JTextArea به جای JTable برای نمایش شکایات
        complaintTextArea = new JTextArea(15, 50);
        complaintTextArea.setEditable(false);  // غیرقابل ویرایش بودن
        JScrollPane scrollPane = new JScrollPane(complaintTextArea);

        // اضافه کردن اجزا به پنل اصلی
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // بارگذاری همه شکایات از فایل
        loadComplaints();

        // رویدادها
        addButton.addActionListener(e -> addComplaint());
        editButton.addActionListener(e -> editComplaint());
        deleteButton.addActionListener(e -> deleteComplaint());
        searchButton.addActionListener(e -> searchComplaint());
        showAllButton.addActionListener(e -> loadComplaints());
        changeStatusButton.addActionListener(e -> changeComplaintStatus());

    }

    // بارگذاری شکایت‌ها از فایل و نمایش آن‌ها در JTextArea
    private void loadComplaints() {
        List<Complaint> complaints = complaintManager.getAllComplaints();
        StringBuilder sb = new StringBuilder();

        for (Complaint complaint : complaints) {
            sb.append("شماره میز: ").append(complaint.getTableNumber())
              .append("\nتاریخ: ").append(complaint.getDate())
              .append("\nشکایت: ").append(complaint.getComplaintDetails())
              .append("\nوضعیت: ").append(complaint.getStatus())
              .append("\n\n------------------------------\n\n");
        }

        complaintTextArea.setText(sb.toString());
    }

    // افزودن شکایت جدید
    private void addComplaint() {
        String tableNumber = tableNumberField.getText();
        String date = dateField.getText();
        String complaintDetails = complaintDetailsField.getText();

        if (tableNumber != null && date != null && complaintDetails != null) {
            Complaint newComplaint = new Complaint(complaintDetails, tableNumber, date);
            complaintManager.addComplaint(newComplaint);
            loadComplaints(); // بارگذاری مجدد شکایت‌ها
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً تمام فیلدها را پر کنید.");
        }
    }
    //ویرایش شکایت
    public void editComplaint() {
        String tableNumber = tableNumberField.getText();  // دریافت شماره میز
        String newComplaintText = complaintDetailsField.getText();  // دریافت متن جدید شکایت

        if (tableNumber != null && !tableNumber.isEmpty() && newComplaintText != null && !newComplaintText.isEmpty()) {
            // ویرایش همه شکایت‌ها برای شماره میز مشخص
            complaintManager.editComplaintByTable(tableNumber, newComplaintText);  // فراخوانی متد ویرایش بر اساس شماره میز
            loadComplaints();  // بارگذاری مجدد شکایت‌ها بعد از ویرایش
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً شماره میز و متن جدید شکایت را وارد کنید.");
        }
    }


    // حذف شکایت انتخاب شده
    private void deleteComplaint() {
        int selectedRow = complaintTextArea.getCaretPosition(); // مشابه برای انتخاب
        if (selectedRow != -1) {
            String complaintText = complaintTextArea.getText();  // باید با انتخاب مطابقت داشته باشد
            complaintManager.deleteComplaint(tableNumberField.getText(), complaintText);
            loadComplaints(); // بارگذاری مجدد شکایت‌ها
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً یک شکایت را انتخاب کنید.");
        }
    }

    // جستجوی شکایت‌ها با شماره میز
    private void searchComplaint() {
        String tableNumber = tableNumberField.getText();
        if (tableNumber != null && !tableNumber.isEmpty()) {
            List<Complaint> complaints = complaintManager.searchComplaintsByTable(tableNumber);
            StringBuilder sb = new StringBuilder();

            for (Complaint complaint : complaints) {
                sb.append("شماره میز: ").append(complaint.getTableNumber())
                  .append("\nتاریخ: ").append(complaint.getDate())
                  .append("\nشکایت: ").append(complaint.getComplaintDetails())
                  .append("\nوضعیت: ").append(complaint.getStatus())
                  .append("\n\n------------------------------\n\n");
            }

            complaintTextArea.setText(sb.toString());
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً شماره میز را وارد کنید.");
        }
    }
 // تغییر وضعیت شکایت به "حل شد"
    private void changeComplaintStatus() {
        String tableNumber = tableNumberField.getText();  // شماره میز از فیلد ورودی
        String complaintText = complaintDetailsField.getText();  // متن شکایت از فیلد ورودی

        if (tableNumber != null && !tableNumber.isEmpty() && complaintText != null && !complaintText.isEmpty()) {
            complaintManager.updateComplaintStatus(tableNumber, complaintText, "حل شد");
            loadComplaints();  // بارگذاری مجدد شکایت‌ها
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً شماره میز و متن شکایت را وارد کنید.");
        }
    }

}
