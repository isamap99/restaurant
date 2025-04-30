package Common;

public class Complaint {
    private String complaintDetails;
    private String tableNumber;
    private String date;
    private String status;  // "در حال بررسی", "حل شده", ...

    public Complaint(String complaintDetails, String tableNumber, String date) {
        this.complaintDetails = complaintDetails;
        this.tableNumber = tableNumber;
        this.date = date;
        this.status = "در حال بررسی";
    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String newDetails) {
        this.complaintDetails = newDetails;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public void resolveComplaint() {
        this.status = "حل شده";
    }

    @Override
    public String toString() {
        return "میز " + tableNumber + " | تاریخ: " + date + " | شکایت: " + complaintDetails + " | وضعیت: " + status;
    }

    // ⬇ برای ذخیره در فایل
    public String toFileFormat() {
        return tableNumber + "|" + date + "|" + complaintDetails + "|" + status;
    }

    // ⬇ برای بازسازی از فایل
    public static Complaint fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 4) return null;
        Complaint complaint = new Complaint(parts[2], parts[0], parts[1]);
        complaint.setStatus(parts[3]);
        return complaint;
    }
}
