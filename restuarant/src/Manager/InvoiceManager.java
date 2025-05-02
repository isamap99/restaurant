package Manager;

import Common.Invoice;
import Common.Order;
import Common.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceManager {
    private List<Invoice> invoices;
    private int nextInvoiceId;

    public InvoiceManager() {
        this.invoices = new ArrayList<>();
        this.nextInvoiceId = 1; // Starting with Invoice ID 1
    }

    public Invoice createInvoice(Order order) {
        Invoice invoice = new Invoice(nextInvoiceId++, order);
        invoices.add(invoice);
        return invoice;
    }

    public Invoice getInvoiceById(int invoiceId) {
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceId() == invoiceId) {
                return invoice;
            }
        }
        return null;
    }

    public void setInvoicePaid(int invoiceId, boolean isPaid) {
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice != null) {
            invoice.setPaid(isPaid);
            System.out.println("Invoice #" + invoiceId + " marked as " + (isPaid ? "Paid" : "Unpaid"));
        } else {
            System.out.println("Invoice not found.");
        }
    }

    public void displayAllInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("No invoices available.");
            return;
        }
        for (Invoice invoice : invoices) {
            invoice.printInvoiceDetails();
            System.out.println("----------------------------");
        }
    }

    public void saveInvoicesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Invoice invoice : invoices) {
                writer.println("InvoiceId:" + invoice.getInvoiceId());
                writer.println("OrderId:" + invoice.getOrder().getOrderId());
                writer.println("TotalAmount:" + invoice.getTotalAmount());
                writer.println("PaidStatus:" + invoice.isPaid());
                for (Order.OrderItem item : invoice.getOrder().getItems()) {
                    writer.printf("Item:%s;%d;%.0f%n", item.getName(), item.getQuantity(), item.getUnitPrice());
                }
                writer.println("END_INVOICE");
            }
            System.out.println("Invoices saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving invoices: " + e.getMessage());
        }
    }

    public void loadInvoicesFromFile(String filename) {
        FileUtil.createFileIfNotExists(filename); // Ensure the file exists
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            invoices.clear();
            String line;
            Invoice currentInvoice = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("InvoiceId:")) {
                    int invoiceId = Integer.parseInt(line.substring(10));
                    currentInvoice = new Invoice(invoiceId, null); // Temporarily create with null Order
                } else if (line.startsWith("OrderId:") && currentInvoice != null) {
                    int orderId = Integer.parseInt(line.substring(8));
                    // Load the order by orderId (assuming you have a method to retrieve orders)
                    Order order = new Order(0); // You need to implement this method
                    currentInvoice = new Invoice(currentInvoice.getInvoiceId(), order);
                } else if (line.startsWith("TotalAmount:") && currentInvoice != null) {
                    currentInvoice = new Invoice(currentInvoice.getInvoiceId(), currentInvoice.getOrder());
                    currentInvoice.getTotalAmount(); // Set the total amount from file
                } else if (line.equals("END_INVOICE")) {
                    invoices.add(currentInvoice);
                }
            }
            System.out.println("Invoices loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading invoices: " + e.getMessage());
        }
    }
}
