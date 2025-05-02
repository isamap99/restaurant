package Manager;

import Common.Complaint;
import Common.FileUtil;

import java.io.*;
import java.util.*;

public class ComplaintManager {
    private final String FILE_PATH = "complaints.txt";

    public ComplaintManager() {
        FileUtil.createFileIfNotExists(FILE_PATH);
    }

    // افزودن شکایت
    public void addComplaint(Complaint complaint) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(complaint.toFileFormat());
            writer.newLine();
            System.out.println("✅ شکایت اضافه شد.");
        } catch (IOException e) {
            System.out.println("❌ خطا در افزودن شکایت.");
            e.printStackTrace();
        }
    }

    // جستجوی شکایت‌ها با شماره میز
    public List<Complaint> searchComplaintsByTable(String tableNumber) {
        List<Complaint> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Complaint complaint = Complaint.fromFileFormat(line);
                if (complaint != null && complaint.getTableNumber().equals(tableNumber)) {
                    results.add(complaint);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ خطا در جستجو.");
            e.printStackTrace();
        }
        return results;
    }

 // ویرایش شکایت‌ها فقط بر اساس شماره میز
    public void editComplaintByTable(String tableNumber, String newComplaintText) {
        List<Complaint> complaints = getAllComplaints();  // تمام شکایت‌ها را می‌گیریم
        boolean found = false;

        for (Complaint complaint : complaints) {
            if (complaint.getTableNumber().equals(tableNumber)) {
                complaint.setComplaintDetails(newComplaintText);  // تغییر متن شکایت
                found = true;
            }
        }

        // اگر شکایت‌ها پیدا شدند، آن‌ها را ذخیره می‌کنیم
        if (found) {
            saveAllComplaints(complaints);
            System.out.println("✅ شکایت‌های شماره میز " + tableNumber + " ویرایش شد.");
        } else {
            System.out.println("❗ هیچ شکایتی برای شماره میز " + tableNumber + " پیدا نشد.");
        }
    }
    
    // حذف شکایت
    public void deleteComplaint(String tableNumber, String complaintText) {
        List<Complaint> complaints = getAllComplaints();
        boolean found = false;

        Iterator<Complaint> it = complaints.iterator();
        while (it.hasNext()) {
            Complaint c = it.next();
            if (c.getTableNumber().equals(tableNumber) && c.getComplaintDetails().equals(complaintText)) {
                it.remove();
                found = true;
            }
        }

        saveAllComplaints(complaints);
        if (found)
            System.out.println("✅ شکایت حذف شد.");
        else
            System.out.println("❗ شکایت مورد نظر پیدا نشد.");
    }

    // گرفتن تمام شکایت‌ها
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Complaint complaint = Complaint.fromFileFormat(line);
                if (complaint != null)
                    complaints.add(complaint);
            }
        } catch (IOException e) {
            System.out.println("❌ خطا در خواندن شکایت‌ها.");
            e.printStackTrace();
        }
        return complaints;
    }

    // ذخیره همه شکایت‌ها در فایل (بازنویسی کل فایل)
    public void saveAllComplaints(List<Complaint> complaints) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Complaint c : complaints) {
                writer.write(c.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ خطا در ذخیره شکایت‌ها.");
            e.printStackTrace();
        }
    }
 // متد برای به روز رسانی وضعیت شکایت در فایل
    public void updateComplaintStatus(String tableNumber, String complaintDetails, String newStatus) {
        // تمام شکایت‌ها را می‌گیریم
        List<Complaint> complaints = getAllComplaints();  
        
        for (Complaint complaint : complaints) {
            // بررسی برای پیدا کردن شکایت با شماره میز و متن شکایت مشخص
            if (complaint.getTableNumber().equals(tableNumber) && complaint.getComplaintDetails().equals(complaintDetails)) {
                // تغییر وضعیت شکایت
                complaint.setStatus(newStatus);  
                saveAllComplaints(complaints);  // ذخیره تغییرات در فایل
                System.out.println("✅ وضعیت شکایت به حل شد تغییر یافت.");
                break;
            }
        }
    }

}
