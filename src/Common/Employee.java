package Common;

// این کلاس برای نگه داشتن اطلاعات کارمندهای رستوران ساخته شده 
public class Employee { // فیلدهای کلاس (اطلاعات کارمند)
	private String firstName; // نام کارمند (مثلا علی)
	private String lastName; // نام خانوادگی کارمند (مثلا احمدی)
	private String gender; // جنسیت کارمند (مثلا مرد یا زن) 
	private String phoneNumber; // شماره تماس کارمند (مثلا 09123456789)
	private int employeeId; // شماره کارمندی (مثلا 101) 
	private String role; // نقش کارمند (مثلا گارسون یا آشپز)
	private double salary; // حقوق کارمند (مثلا 3000000)// سازنده (برای وقتی که می‌خوایم یه کارمند جدید بسازیم)

public Employee(String firstName, String lastName, String gender, String phoneNumber, int employeeId, String role, double salary) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.employeeId = employeeId;
    this.role = role;
    this.salary = salary;
}

// متدهای گتر (برای گرفتن اطلاعات کارمند)
public String getFirstName() {
    return firstName;
}

public String getLastName() {
    return lastName;
}

public String getGender() {
    return gender;
}

public String getPhoneNumber() {
    return phoneNumber;
}

public int getEmployeeId() {
    return employeeId;
}

public String getRole() {
    return role;
}

public double getSalary() {
    return salary;
}

// متدهای ستر (برای تغییر اطلاعات کارمند)
public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public void setGender(String gender) {
    this.gender = gender;
}

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}

public void setRole(String role) {
    this.role = role;
}

public void setSalary(double salary) {
    this.salary = salary;
}

// این متد برای تبدیل اطلاعات کارمند به یه خط متنیه (برای ذخیره تو فایل)
@Override
public String toString() {
    return firstName + "," + lastName + "," + gender + "," + phoneNumber + "," + employeeId + "," + role + "," + salary;
}
}