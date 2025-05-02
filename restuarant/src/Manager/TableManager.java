package Manager;

import Common.Table;
import Common.Customer;
import FileManager.txtFileManager;

public class TableManager {

  private txtFileManager fileManager;

  public TableManager(String fileName) {
    fileManager = new txtFileManager(fileName);
  }

  public void addTable(Table table) {
	  // Ø§ÙˆÙ„ Ø¨Ø±Ø±Ø³ÛŒ Ù…ÛŒâ€ŒÚ©Ù†ÛŒÙ… Ú©Ù‡ ØªÚ©Ø±Ø§Ø±ÛŒ Ù†Ø¨Ø§Ø´Ù‡
	  Table existing = findTableByNumber(table.getTableNumber());
	  if (existing != null) {
	    System.out.println("Ù…ÛŒØ²ÛŒ Ø¨Ø§ Ø§ÛŒÙ† Ø´Ù…Ø§Ø±Ù‡ Ù‚Ø¨Ù„Ø§Ù‹ Ø«Ø¨Øª Ø´Ø¯Ù‡ Ø§Ø³Øª.");
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
	        line = tableToString(newTable); // Ø¬Ø§ÛŒÚ¯Ø²ÛŒÙ†
	      } else {
	        line = tableToString(all[i]);   // Ù‡Ù…ÙˆÙ† Ù‚Ø¨Ù„ÛŒ
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
	    System.out.println("Ù…ÛŒØ²ÛŒ Ø¨Ø§ Ø§ÛŒÙ† Ø´Ù…Ø§Ø±Ù‡ ÛŒØ§Ù�Øª Ù†Ø´Ø¯.");
	  }
	}

  public void setTableEmpty(String tableNumber) {
	  Table table = findTableByNumber(tableNumber);
	  if (table != null) {
	    table.setEmpty();
	    updateTableByNumber(tableNumber, table);
	  } else {
	    System.out.println("Ù…ÛŒØ²ÛŒ Ø¨Ø§ Ø§ÛŒÙ† Ø´Ù…Ø§Ø±Ù‡ ÛŒØ§Ù�Øª Ù†Ø´Ø¯.");
	  }
	}


  public void addSeats(String tableNumber, int extraSeats) {
	  Table table = findTableByNumber(tableNumber);
	  if (table != null) {
	    int newSeatCount = table.getSeatCount() + extraSeats;
	    Table updatedTable = new Table(table.getTableNumber(), newSeatCount);

	    // Ø­Ù�Ø¸ ÙˆØ¶Ø¹ÛŒØª Ù‚Ø¨Ù„ÛŒ (Ø§Ø´ØºØ§Ù„ ÛŒØ§ Ø®Ø§Ù„ÛŒ)
	    if (table.isOccupied())
	      updatedTable.setOccupied();
	    else
	      updatedTable.setEmpty();

	    updateTableByNumber(tableNumber, updatedTable);
	  } else {
	    System.out.println("Ù…ÛŒØ²ÛŒ Ø¨Ø§ Ø§ÛŒÙ† Ø´Ù…Ø§Ø±Ù‡ ÛŒØ§Ù�Øª Ù†Ø´Ø¯.");
	  }
	}


  public Table[] getEmptyTables() {
	  Table[] all = getAllTables();

	  // Ø´Ù…Ø§Ø±Ø´ Ø§ÙˆÙ„ÛŒÙ‡ Ø¨Ø±Ø§ÛŒ Ø§Ù†Ø¯Ø§Ø²Ù‡ Ø¢Ø±Ø§ÛŒÙ‡ Ù†Ù‡Ø§ÛŒÛŒ
	  int count = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].isEmpty())
	      count++;
	  }

	  // Ø³Ø§Ø®Øª Ø¢Ø±Ø§ÛŒÙ‡ Ù�Ù‚Ø· Ø¨Ø§ Ù…ÛŒØ²Ù‡Ø§ÛŒ Ø®Ø§Ù„ÛŒ
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

	  // Ø´Ù…Ø§Ø±Ø´ Ù…ÛŒØ²Ù‡Ø§ÛŒ Ø§Ø´ØºØ§Ù„â€ŒØ´Ø¯Ù‡
	  int count = 0;
	  for (int i = 0; i < all.length; i++) {
	    if (all[i] != null && all[i].isOccupied())
	      count++;
	  }

	  // Ø³Ø§Ø®Øª Ø¢Ø±Ø§ÛŒÙ‡ Ù�Ù‚Ø· Ø¨Ø§ Ù…ÛŒØ²Ù‡Ø§ÛŒ Ø§Ø´ØºØ§Ù„
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

	  if (parts[2].equals("Ø§Ø´ØºØ§Ù„"))
	    table.setOccupied();
	  else
	    table.setEmpty();

	  return table;
	}
  public void reserveTable(String tableNumber, Customer customer) {
	  // Ø¨Ø±Ø±Ø³ÛŒ Ø§ÛŒÙ†Ú©Ù‡ Ù…ÛŒØ² ÙˆØ¬ÙˆØ¯ Ø¯Ø§Ø±Ù‡ Ùˆ Ø®Ø§Ù„ÛŒÙ‡
	  Table table = findTableByNumber(tableNumber);
	  if (table == null) {
	    System.out.println("Ù…ÛŒØ² Ù…ÙˆØ±Ø¯ Ù†Ø¸Ø± ÙˆØ¬ÙˆØ¯ Ù†Ø¯Ø§Ø±Ø¯.");
	    return;
	  }
	  if (table.isOccupied()) {
	    System.out.println("Ù…ÛŒØ² Ù…ÙˆØ±Ø¯ Ù†Ø¸Ø± Ù‚Ø¨Ù„Ø§Ù‹ Ø§Ø´ØºØ§Ù„ Ø´Ø¯Ù‡ Ø§Ø³Øª.");
	    return;
	  }

	  // Ø¨Ø±Ø±Ø³ÛŒ Ø§ÛŒÙ†Ú©Ù‡ Ø±Ø²Ø±Ùˆ Ù‚Ø¨Ù„Ø§Ù‹ Ø¨Ø±Ø§ÛŒ Ø§ÛŒÙ† Ù…ÛŒØ² Ø«Ø¨Øª Ù†Ø´Ø¯Ù‡
	  txtFileManager resFile = new txtFileManager("reservations.txt");
	  String[] reservations = resFile.getArrayFromFile();
	  for (String line : reservations) {
	    if (line != null && line.startsWith(tableNumber + "   ")) {
	      System.out.println("Ø¨Ø±Ø§ÛŒ Ø§ÛŒÙ† Ù…ÛŒØ² Ù‚Ø¨Ù„Ø§Ù‹ Ø±Ø²Ø±Ùˆ Ø«Ø¨Øª Ø´Ø¯Ù‡ Ø§Ø³Øª.");
	      return;
	    }
	  }

	  // Ø³Ø§Ø®Øª Ø®Ø· Ø¬Ø¯ÛŒØ¯ Ø±Ø²Ø±Ùˆ Ùˆ Ø§Ù�Ø²ÙˆØ¯Ù† Ø¨Ù‡ Ù�Ø§ÛŒÙ„
	  String resLine = tableNumber + "   " +
	                   customer.getPhone() + "   " +
	                   customer.getName() + "   " +
	                   customer.getFamily();
	  resFile.AppendRow(resLine);

	  // ØªØºÛŒÛŒØ± ÙˆØ¶Ø¹ÛŒØª Ù…ÛŒØ² Ø¨Ù‡ Ø§Ø´ØºØ§Ù„
	  setTableOccupied(tableNumber);

	  System.out.println("Ø±Ø²Ø±Ùˆ Ø¨Ø§ Ù…ÙˆÙ�Ù‚ÛŒØª Ø§Ù†Ø¬Ø§Ù… Ø´Ø¯.");
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

	  // ØªØºÛŒÛŒØ± ÙˆØ¶Ø¹ÛŒØª Ù…ÛŒØ² Ø¨Ù‡ "Ø®Ø§Ù„ÛŒ"
	  setTableEmpty(tableNumber);

	  System.out.println("Ø±Ø²Ø±Ùˆ Ù…ÛŒØ² " + tableNumber + " Ù„ØºÙˆ Ø´Ø¯.");
	}

  public void getCustomerOfTable(String tableNumber) {
	    txtFileManager resFile = new txtFileManager("reservations.txt");
	    String[] reservations = resFile.getArrayFromFile();

	    for (String line : reservations) {
	        if (line != null && line.startsWith(tableNumber + "   ")) {
	            // Ø§Ø³ØªÙ�Ø§Ø¯Ù‡ Ø§Ø² Regular Expression Ø¨Ø±Ø§ÛŒ ØªÙ‚Ø³ÛŒÙ… Ø±Ø´ØªÙ‡ Ø¨Ø§ Ù‡Ø± ØªØ¹Ø¯Ø§Ø¯ Ù�Ø§ØµÙ„Ù‡
	            String[] parts = line.trim().split("\\s{3,}"); // Ù‡Ø± ØªØ¹Ø¯Ø§Ø¯ Ù�Ø§ØµÙ„Ù‡ Ø¨ÛŒØ´ØªØ± Ø§Ø² Û³
	            if (parts.length >= 4) {
	                System.out.println("Ù…Ø´ØªØ±ÛŒ Ø±ÙˆÛŒ Ù…ÛŒØ² " + tableNumber + ":");
	                System.out.println("Ù†Ø§Ù…: " + parts[2]);
	                System.out.println("Ù†Ø§Ù… Ø®Ø§Ù†ÙˆØ§Ø¯Ú¯ÛŒ: " + parts[3]);
	                System.out.println("Ø´Ù…Ø§Ø±Ù‡ ØªÙ…Ø§Ø³: " + parts[1]);
	                return;
	            }
	        }
	    }

	    System.out.println("Ø¨Ø±Ø§ÛŒ Ø§ÛŒÙ† Ù…ÛŒØ² Ù‡ÛŒÚ† Ø±Ø²Ø±ÙˆÛŒ Ø«Ø¨Øª Ù†Ø´Ø¯Ù‡ Ø§Ø³Øª.");
	}

  public void getTableOfCustomer(String phone) {
	  txtFileManager resFile = new txtFileManager("reservations.txt");
	  String[] reservations = resFile.getArrayFromFile();

	  for (String line : reservations) {
	    if (line != null) {
	      String[] parts = line.trim().split("   ");
	      if (parts.length >= 2 && parts[1].equals(phone)) {
	        System.out.println("Ù…Ø´ØªØ±ÛŒ Ø¨Ø§ Ø´Ù…Ø§Ø±Ù‡ " + phone + " Ù…ÛŒØ² " + parts[0] + " Ø±Ø§ Ø±Ø²Ø±Ùˆ Ú©Ø±Ø¯Ù‡ Ø§Ø³Øª.");
	        return;
	      }
	    }
	  }

	  System.out.println("Ù‡ÛŒÚ† Ø±Ø²Ø±ÙˆÛŒ Ø¨Ø±Ø§ÛŒ Ù…Ø´ØªØ±ÛŒ Ø¨Ø§ Ø§ÛŒÙ† Ø´Ù…Ø§Ø±Ù‡ ØªÙ…Ø§Ø³ Ø«Ø¨Øª Ù†Ø´Ø¯Ù‡ Ø§Ø³Øª.");
	}
  public void freeTable(String tableNumber) {
	    Table table = findTableByNumber(tableNumber);
	    if (table != null) {
	        table.setEmpty();
	        updateTableByNumber(tableNumber, table); // Ø¢Ù¾Ø¯ÛŒØª ÙˆØ¶Ø¹ÛŒØª Ù…ÛŒØ² Ø¨Ù‡ "Ø®Ø§Ù„ÛŒ"
	        System.out.println("Ù…ÛŒØ² " + tableNumber + " Ø¢Ø²Ø§Ø¯ Ø´Ø¯.");
	    } else {
	        System.out.println("Ù…ÛŒØ²ÛŒ Ø¨Ø§ Ø§ÛŒÙ† Ø´Ù…Ø§Ø±Ù‡ ÛŒØ§Ù�Øª Ù†Ø´Ø¯.");
	    }
	}

  	
}
