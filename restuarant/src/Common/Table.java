package Common;

public class Table {
	private String tableNumber;  // تغییر از int به String
    private int seatCount;
    private String status; // "خالی" یا "اشغال"

    public Table(String tableNumber, int seatCount) {
        this.tableNumber = tableNumber;
        this.seatCount = seatCount;
        this.status = "خالی";
    }


    public String getTableNumber() {
        return tableNumber;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public String getStatus() {
        return status;
    }

    public void setOccupied() {
        this.status = "اشغال";
    }

    public void setEmpty() {
        this.status = "خالی";
    }

    public boolean isOccupied() {
        return status.equals("اشغال");
    }

    public boolean isEmpty() {
        return status.equals("خالی");
    }

    @Override
    public String toString() {
        return "میز شماره " + tableNumber +
               " | تعداد صندلی: " + seatCount +
               " | وضعیت: " + status;
    }
}
