package Manager;

import Common.Customer;
import Common.Takeaway;
import fileManager.txtFileManager;

public class TakeawayManager {
    private txtFileManager fileManager;

    public TakeawayManager(String fileName) {
        this.fileManager = new txtFileManager(fileName);
    }

    public void addTakeaway(Takeaway takeaway) {
        String line = takeawayToString(takeaway);
        fileManager.AppendRow(line);
    }

    public void markAsDelivered(int takeawayId) {
        Takeaway[] all = getAllTakeaways();
        String newContent = "";

        for (Takeaway t : all) {
            if (t != null) {
                if (t.getTakeawayId() == takeawayId) {
                    t.markDelivered();
                }
                if (!newContent.equals("")) newContent += "\n";
                newContent += takeawayToString(t);
            }
        }

        fileManager.setIntoFile(newContent);
    }

    public Takeaway[] getAllTakeaways() {
        String[] lines = fileManager.getArrayFromFile();
        Takeaway[] list = new Takeaway[lines.length];
        for (int i = 0; i < lines.length; i++) {
            list[i] = parseTakeaway(lines[i]);
        }
        return list;
    }

    public Takeaway findTakeawayById(int takeawayId) {
        Takeaway[] all = getAllTakeaways();
        for (Takeaway t : all) {
            if (t != null && t.getTakeawayId() == takeawayId)
                return t;
        }
        return null;
    }

    private Takeaway parseTakeaway(String line) {
        String[] parts = line.trim().split("   ");
        if (parts.length != 8) return null;

        Customer c = new Customer();
        Takeaway t = new Takeaway(
            Integer.parseInt(parts[0]),       // takeawayId
            Integer.parseInt(parts[1]),       // orderId
            c,
            parts[6],                         // deliveryTime
            Double.parseDouble(parts[7].replace("*", "")) // deliveryCost
        );
        if (parts[7].endsWith("*")) t.markDelivered(); // if marked as delivered
        return t;
    }

    private String takeawayToString(Takeaway t) {
        String base = t.getTakeawayId() + "   " +
                      t.getOrderId() + "   " +
                      t.getCustomer().getName() + "   " +
                      t.getCustomer().getFamily() + "   " +
                      t.getCustomer().getAddress() + "   " +
                      t.getCustomer().getPhone() + "   " +
                      t.getDeliveryTime() + "   " +
                      t.getDeliveryCost();

        return t.isDelivered() ? base + "*" : base;
    }
}