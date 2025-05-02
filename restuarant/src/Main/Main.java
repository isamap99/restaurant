package grestaurant;

import Gui.*;
import Manager.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // ساخت مدیرهای مورد نیاز
        CustomerManager customerManager = new CustomerManager("C:\\Users\\ASUS\\git\\repository1\\restuarant\\customers.txt");
        TableManager tableManager = new TableManager("C:\\Users\\ASUS\\git\\repository1\\restuarant\\tables.txt");
        ComplaintManager complaintManager = new ComplaintManager();
        PaymentManager paymentManager = new PaymentManager(tableManager);
        TrainingCourseManager trainingCourseManager = new TrainingCourseManager();

        // لیست پنل‌ها برای انتخاب
        String[] options = {
            "پنل مشتری‌ها",
            "پنل میزها",
            "پنل شکایات",
            "پنل پرداخت‌ها",
            "پنل دوره‌های آموزشی"
        };

        // نمایش پنجره انتخاب
        String selected = (String) JOptionPane.showInputDialog(
            null,
            "کدام پنل را می‌خواهید باز کنید؟",
            "انتخاب پنل",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (selected == null) return; // اگر کاربر Cancel را زد

        // باز کردن پنل انتخاب‌شده
        JFrame frame = new JFrame(selected);
        frame.setSize(900, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        switch (selected) {
            case "پنل مشتری‌ها":
                frame.setContentPane(new CustomerPanel(customerManager));
                break;
            case "پنل میزها":
                frame.setContentPane(new TablePanel(tableManager));
                break;
            case "پنل شکایات":
                frame.setContentPane(new ComplaintPanel());
                break;
            case "پنل پرداخت‌ها":
                frame.setContentPane(new PaymentPanel(paymentManager));
                break;
            case "پنل دوره‌های آموزشی":
                frame.setContentPane(new TrainingCoursePanel());
                break;
            default:
                JOptionPane.showMessageDialog(null, "پنل نامعتبر انتخاب شد.");
                return;
        }

        frame.setVisible(true);
    }
}
