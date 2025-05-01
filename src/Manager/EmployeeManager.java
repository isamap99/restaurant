package Manager;

import Common.Employee;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// این کلاس برای مدیریت کارمندهای رستورانه (مثل اضافه کردن، حذف و نمایش کارمندها)
public class EmployeeManager {
    private List<Employee> employeeList; // فقط یه متغیر با نوع جنریک درست
    private final String fileName = "employees.txt";

    public EmployeeManager() {
        employeeList = new ArrayList<>(); // مقداردهی داخل سازنده
        loadFromFile(); // صدا کردن متد loadFromFile
    }

    // متد برای اضافه کردن کارمند جدید
    public void addEmployee(String firstName, String lastName, String gender, String phoneNumber, int employeeId, String role, double salary) {
        for (Employee emp : employeeList) {
            if (emp.getEmployeeId() == employeeId) {
                System.out.println("کارمند با شماره " + employeeId + " قبلاً ثبت شده!");
                return;
            }
        }
        Employee newEmployee = new Employee(firstName, lastName, gender, phoneNumber, employeeId, role, salary);
        employeeList.add(newEmployee);
        saveToFile();
        System.out.println("کارمند " + firstName + " " + lastName + " با شماره " + employeeId + " با موفقیت اضافه شد!");
    }

    // متد برای حذف کارمند
    public void removeEmployee(int employeeId) {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (employee.getEmployeeId() == employeeId) {
                String name = employee.getFirstName() + " " + employee.getLastName();
                employeeList.remove(i);
                saveToFile();
                System.out.println("کارمند " + name + " با شماره " + employeeId + " با موفقیت حذف شد!");
                return;
            }
        }
        System.out.println("کارمند با شماره " + employeeId + " پیدا نشد!");
    }

    // متد برای آپدیت اطلاعات کارمند
    public void updateEmployee(int employeeId, String newFirstName, String newLastName, String newGender, String newPhoneNumber, String newRole, double newSalary) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeId() == employeeId) {
                employee.setFirstName(newFirstName);
                employee.setLastName(newLastName);
                employee.setGender(newGender);
                employee.setPhoneNumber(newPhoneNumber);
                employee.setRole(newRole);
                employee.setSalary(newSalary);
                saveToFile();
                System.out.println("اطلاعات کارمند با شماره " + employeeId + " با موفقیت به‌روزرسانی شد!");
                return;
            }
        }
        System.out.println("کارمند با شماره " + employeeId + " پیدا نشد!");
    }

    // متد برای تغییر فقط حقوق کارمند
    public void updateSalary(int employeeId, double newSalary) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeId() == employeeId) {
                employee.setSalary(newSalary);
                saveToFile();
                System.out.println("حقوق کارمند با شماره " + employeeId + " به " + newSalary + " با موفقیت به‌روزرسانی شد!");
                return;
            }
        }
        System.out.println("کارمند با شماره " + employeeId + " پیدا نشد!");
    }

    // متد برای نمایش همه کارمندها
    public void displayEmployees() {
        if (employeeList.isEmpty()) {
            System.out.println("هیچ کارمندی تو لیست نیست!");
        } else {
            System.out.println("لیست کارمندهای رستوران:");
            for (Employee employee : employeeList) {
                System.out.println("نام: " + employee.getFirstName() + " " + employee.getLastName() + 
                                   " | جنسیت: " + employee.getGender() + 
                                   " | شماره تماس: " + employee.getPhoneNumber() + 
                                   " | شماره کارمندی: " + employee.getEmployeeId() + 
                                   " | نقش: " + employee.getRole() + 
                                   " | حقوق: " + employee.getSalary());
            }
        }
    }

    // متد برای جستجوی کارمند
    public void searchEmployee(String searchTerm) {
        boolean found = false;
        System.out.println("نتیجه جستجو برای: " + searchTerm);
        for (Employee employee : employeeList) {
            if (String.valueOf(employee.getEmployeeId()).equals(searchTerm)){
                System.out.println("کارمند پیدا شد: " +
                                   "نام: " + employee.getFirstName() + " " + employee.getLastName() + 
                                   " | جنسیت: " + employee.getGender() + 
                                   " | شماره تماس: " + employee.getPhoneNumber() + 
                                   " | شماره کارمندی: " + employee.getEmployeeId() + 
                                   " | نقش: " + employee.getRole() + 
                                   " | حقوق: " + employee.getSalary());
                found = true;
            }
        }
        if (!found) {
            System.out.println("کارمندی با این مشخصات پیدا نشد!");
        }
    }

    // متد برای ذخیره اطلاعات تو فایل
    private void saveToFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Employee employee : employeeList) {
                writer.write(employee.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("خطا تو ذخیره کردن تو فایل: " + e.getMessage());
        }
    }

    // متد برای خوندن اطلاعات از فایل
    private void loadFromFile() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String gender = parts[2];
                    String phoneNumber = parts[3];
                    int employeeId = Integer.parseInt(parts[4]);
                    String role = parts[5];
                    double salary = Double.parseDouble(parts[6]);
                    Employee employee = new Employee(firstName, lastName, gender, phoneNumber, employeeId, role, salary);
                    employeeList.add(employee);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("خطا تو خوندن فایل: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("خطا تو تبدیل داده‌های فایل: " + e.getMessage());
        }
    }
}