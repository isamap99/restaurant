package Common;

public class Customer
{
	
  private String Name;
  private String Family;
  private String Address;
  private String Phone;

  public String getName()
  {
    return Name;
  }

  public void setName(String name)
  {
    Name = name;
  }

  public String getFamily()
  {
    return Family;
  }

  public void setFamily(String family)
  {
    Family = family;
  }

  public String getAddress()
  {
    return Address;
  }
  
  public void setAddress(String address)
  {
    if (address.trim().equals("بناب"))
      Address = address;
    else
      System.out.println("آدرس فقط باید 'بناب' باشد.");
  }

  public String getPhone()
  {
    return Phone;
  }

  public void setPhone(String phone)
  {
    if (phone.matches("^09\\d{9}$"))
      Phone = phone;
    else
      System.out.println("شماره تماس نامعتبر است.");
  }

  @Override
  public String toString()
  {
    return  Name + "   " + Family + "   " + Address + "   " + Phone;
  }
  
  public Customer(String name, String family, String address, String phone) {
	    this.Name = name;
	    this.Family = family;
	    this.Address = address;
	    this.Phone = phone;
	}

}
