package Manager;

import Common.Table;
import Common.Customer;
import fileManager.txtFileManager;

public class TableManager {

  private txtFileManager fileManager;

  public TableManager(String fileName) {
    fileManager = new txtFileManager(fileName);
  }

  public void addTable(Table table) {
	  // اول بررسی می‌کنیم که تکراری نباشه
	  Table existing = findTableByNumber(table.getTableNumber());
	  if (existing != null) {
	    System.out.println("میزی با این شماره قبلاً ثبت شده است.");
	    return;
	  }

	  String line = tableToString(table);
	  fileManager.AppendRow(line);
	}


  public void removeTableByNumber(String tableNumber) {
	  Table[] all = getAllTables();
	  String newContent = "";

	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && !all[i].getTableNumber().equals(tableNumber)) {
	      if (!newContent.equals(""))
	        newContent += "\n";
	      newContent += tableToString(all[i]);
	    }
	  }

	  fileManager.setIntoFile(newContent);
	}


  public void updateTableByNumber(String tableNumber, Table newTable) {
	  Table[] all = getAllTables();
	  String newContent = "";

	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null) {
	      String line;
	      if (all[i].getTableNumber().equals(tableNumber)) {
	        line = tableToString(newTable); // جایگزین
	      } else {
	        line = tableToString(all[i]);   // همون قبلی
	      }

	      if (!newContent.equals(""))
	        newContent += "\n";
	      newContent += line;
	    }
	  }

	  fileManager.setIntoFile(newContent);
	}


  public Table findTableByNumber(String tableNumber) {
	  Table[] all = getAllTables();
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].getTableNumber().equals(tableNumber)) {
	      return all[i];
	    }
	  }
	  return null;
	}


  public Table[] getAllTables() {
	  String[] lines = fileManager.getArrayFromFile();
	  Table[] tables = new Table[lines.length];

	  for (int i = 0; i < lines.length; i++) {
	    tables[i] = parseTable(lines[i]);
	  }

	  return tables;
	}


  public void setTableOccupied(String tableNumber) {
	  Table table = findTableByNumber(tableNumber);
	  if (table != null) {
	    table.setOccupied();
	    updateTableByNumber(tableNumber, table);
	  } else {
	    System.out.println("میزی با این شماره یافت نشد.");
	  }
	}

  public void setTableEmpty(String tableNumber) {
	  Table table = findTableByNumber(tableNumber);
	  if (table != null) {
	    table.setEmpty();
	    updateTableByNumber(tableNumber, table);
	  } else {
	    System.out.println("میزی با این شماره یافت نشد.");
	  }
	}


  public void addSeats(String tableNumber, int extraSeats) {
	  Table table = findTableByNumber(tableNumber);
	  if (table != null) {
	    int newSeatCount = table.getSeatCount() + extraSeats;
	    Table updatedTable = new Table(table.getTableNumber(), newSeatCount);

	    // حفظ وضعیت قبلی (اشغال یا خالی)
	    if (table.isOccupied())
	      updatedTable.setOccupied();
	    else
	      updatedTable.setEmpty();

	    updateTableByNumber(tableNumber, updatedTable);
	  } else {
	    System.out.println("میزی با این شماره یافت نشد.");
	  }
	}


  public Table[] getEmptyTables() {
	  Table[] all = getAllTables();

	  // شمارش اولیه برای اندازه آرایه نهایی
	  int count = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].isEmpty())
	      count++;
	  }

	  // ساخت آرایه فقط با میزهای خالی
	  Table[] result = new Table[count];
	  int index = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].isEmpty()) {
	      result[index++] = all[i];
	    }
	  }

	  return result;
	}


  public Table[] getOccupiedTables() {
	  Table[] all = getAllTables();

	  // شمارش میزهای اشغال‌شده
	  int count = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].isOccupied())
	      count++;
	  }

	  // ساخت آرایه فقط با میزهای اشغال
	  Table[] result = new Table[count];
	  int index = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].isOccupied()) {
	      result[index++] = all[i];
	    }
	  }

	  return result;
	}


  private String tableToString(Table table) {
	  return table.getTableNumber() + "   " +
	         table.getSeatCount() + "   " +
	         table.getStatus();
	}


  private Table parseTable(String line) {
	  String[] parts = line.trim().split("   ");
	  if (parts.length != 3)
	    return null;

	  String tableNumber = parts[0];
	  int seatCount;
	  try {
	    seatCount = Integer.parseInt(parts[1]);
	  } catch (NumberFormatException e) {
	    return null;
	  }

	  Table table = new Table(tableNumber, seatCount);

	  if (parts[2].equals("اشغال"))
	    table.setOccupied();
	  else
	    table.setEmpty();

	  return table;
	}
  public void reserveTable(String tableNumber, Customer customer) {
	  // بررسی اینکه میز وجود داره و خالیه
	  Table table = findTableByNumber(tableNumber);
	  if (table == null) {
	    System.out.println("میز مورد نظر وجود ندارد.");
	    return;
	  }
	  if (table.isOccupied()) {
	    System.out.println("میز مورد نظر قبلاً اشغال شده است.");
	    return;
	  }

	  // بررسی اینکه رزرو قبلاً برای این میز ثبت نشده
	  txtFileManager resFile = new txtFileManager("reservations.txt");
	  String[] reservations = resFile.getArrayFromFile();
	  for (String line : reservations) {
	    if (line != null && line.startsWith(tableNumber + "   ")) {
	      System.out.println("برای این میز قبلاً رزرو ثبت شده است.");
	      return;
	    }
	  }

	  // ساخت خط جدید رزرو و افزودن به فایل
	  String resLine = tableNumber + "   " +
	                   customer.getPhone() + "   " +
	                   customer.getName() + "   " +
	                   customer.getFamily();
	  resFile.AppendRow(resLine);

	  // تغییر وضعیت میز به اشغال
	  setTableOccupied(tableNumber);

	  System.out.println("رزرو با موفقیت انجام شد.");
	}
  
  public void cancelReservation(String tableNumber) {
	  txtFileManager resFile = new txtFileManager("reservations.txt");
	  String[] reservations = resFile.getArrayFromFile();
	  String newContent = "";

	  for (int i = 0; i < reservations.length; i++) {
	    if (reservations[i] != null && !reservations[i].startsWith(tableNumber + "   ")) {
	      if (!newContent.equals(""))
	        newContent += "\n";
	      newContent += reservations[i].trim();
	    }
	  }

	  resFile.setIntoFile(newContent);

	  // تغییر وضعیت میز به "خالی"
	  setTableEmpty(tableNumber);

	  System.out.println("رزرو میز " + tableNumber + " لغو شد.");
	}

  public void getCustomerOfTable(String tableNumber) {
	    txtFileManager resFile = new txtFileManager("reservations.txt");
	    String[] reservations = resFile.getArrayFromFile();

	    for (String line : reservations) {
	        if (line != null && line.startsWith(tableNumber + "   ")) {
	            // استفاده از Regular Expression برای تقسیم رشته با هر تعداد فاصله
	            String[] parts = line.trim().split("\\s{3,}"); // هر تعداد فاصله بیشتر از ۳
	            if (parts.length >= 4) {
	                System.out.println("مشتری روی میز " + tableNumber + ":");
	                System.out.println("نام: " + parts[2]);
	                System.out.println("نام خانوادگی: " + parts[3]);
	                System.out.println("شماره تماس: " + parts[1]);
	                return;
	            }
	        }
	    }

	    System.out.println("برای این میز هیچ رزروی ثبت نشده است.");
	}

  public void getTableOfCustomer(String phone) {
	  txtFileManager resFile = new txtFileManager("reservations.txt");
	  String[] reservations = resFile.getArrayFromFile();

	  for (String line : reservations) {
	    if (line != null) {
	      String[] parts = line.trim().split("   ");
	      if (parts.length >= 2 && parts[1].equals(phone)) {
	        System.out.println("مشتری با شماره " + phone + " میز " + parts[0] + " را رزرو کرده است.");
	        return;
	      }
	    }
	  }

	  System.out.println("هیچ رزروی برای مشتری با این شماره تماس ثبت نشده است.");
	}
  public void freeTable(String tableNumber) {
	    Table table = findTableByNumber(tableNumber);
	    if (table != null) {
	        table.setEmpty();
	        updateTableByNumber(tableNumber, table); // آپدیت وضعیت میز به "خالی"
	        System.out.println("میز " + tableNumber + " آزاد شد.");
	    } else {
	        System.out.println("میزی با این شماره یافت نشد.");
	    }
	}

  	
}