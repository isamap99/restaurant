package Main;

import Gui.TrainingCoursePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // تنظیمات اولیه برای اجرای Swing
        SwingUtilities.invokeLater(() -> {
            // ایجاد پنل مدیریت دوره‌های آموزشی
            TrainingCoursePanel trainingCoursePanel = new TrainingCoursePanel();
            
            // ایجاد پنجره اصلی
            JFrame frame = new JFrame("Training Course Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400); // تنظیم اندازه پنجره
            frame.add(trainingCoursePanel); // اضافه کردن پنل به پنجره
            
            // نمایش پنجره
            frame.setVisible(true);
        });
    }
}
