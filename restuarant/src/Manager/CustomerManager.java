package Manager;

import java.util.ArrayList;

import Common.Customer;
import Common.FileUtil;
import fileManager.txtFileManager;

public class CustomerManager {

  private txtFileManager fileManager;
  private final String FILE_PATH = "customers.txt";

  public CustomerManager(String fileName) {
    fileManager = new txtFileManager(fileName);
  }

  public CustomerManager() {
	    FileUtil.createFileIfNotExists(FILE_PATH);
	    fileManager = new txtFileManager(FILE_PATH); // ← این خط اضافه شود
	}


  public void addCustomer(Customer customer) {
	  String line = customerToString(customer);
	  fileManager.AppendRow(line);
	}

  public void updateCustomerByPhone(String phone, Customer newCustomer) {
	  Customer[] all = getAllCustomers();
	  String newContent = "";

	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null) {
	      String line;
	      if (all[i].getPhone().equals(phone)) {
	        // اگر شماره برابر بود، مشتری جدید رو جایگزین کن
	        line = customerToString(newCustomer);
	      } else {
	        // در غیر این صورت، همون مشتری قبلی رو نگه‌دار
	        line = customerToString(all[i]);
	      }

	      if (!newContent.equals(""))
	        newContent += "\n";
	      newContent += line;
	    }
	  }

	  fileManager.setIntoFile(newContent);
	}


  public void removeCustomerByPhone(String phone) {
	  Customer[] all = getAllCustomers();
	  String newContent = "";

	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && !all[i].getPhone().equals(phone)) {
	      if (!newContent.equals(""))
	        newContent += "\n";
	      newContent += customerToString(all[i]);
	    }
	  }

	  fileManager.setIntoFile(newContent);
	}


  public void removeCustomerByRow(int row) {
	  fileManager.DeleteRow(row);
	}


  public Customer findCustomerByPhone(String phone) {
	    Customer[] customers = getAllCustomers();
	    for (Customer c : customers) {
	        if (c.getPhone() != null && c.getPhone().equals(phone)) {
	            return c;
	        }
	    }
	    return null;
	}



  public Customer[] findCustomerByNameAndFamily(String name, String family) {
	  Customer[] all = getAllCustomers();

	  // برای شمردن تعداد نتایج
	  int count = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null &&
	        all[i].getName().equals(name) &&
	        all[i].getFamily().equals(family)) {
	      count++;
	    }
	  }

	  // ایجاد آرایه نتایج با اندازه مناسب
	  Customer[] result = new Customer[count];
	  int index = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null &&
	        all[i].getName().equals(name) &&
	        all[i].getFamily().equals(family)) {
	      result[index++] = all[i];
	    }
	  }

	  return result;
	}


  public Customer[] getAllCustomers() {
	    String[] lines = fileManager.getArrayFromFile();
	    ArrayList<Customer> customerList = new ArrayList<>();

	    for (String line : lines) {
	        String[] parts = line.trim().split("   ");
	        if (parts.length != 4) continue; // خط ناقص

	        Customer customer = new Customer();
	        customer.setName(parts[0].trim());
	        customer.setFamily(parts[1].trim());
	        customer.setAddress(parts[2].trim());
	        customer.setPhone(parts[3].trim());
	        customerList.add(customer);
	    }

	    return customerList.toArray(new Customer[0]);
	}



  private Customer parseCustomer(String line) {
	  String[] parts = line.trim().split("   ");
	  if (parts.length != 4)
	    return null;

	  Customer customer = new Customer();
	  customer.setName(parts[0]);
	  customer.setFamily(parts[1]);
	  customer.setAddress(parts[2]);
	  customer.setPhone(parts[3]);

	  return customer;
	}


  private String customerToString(Customer customer) {
	  return customer.getName() + "   " +
	         customer.getFamily() + "   " +
	         customer.getAddress() + "   " +
	         customer.getPhone();
	}

  
}
