/**
 * Just like last time, the ATM class is responsible for managing all
 * of the user interaction. This means login procedures, displaying the
 * menu, and responding to menu selections. In the enhanced version, the
 * ATM class will have the added responsibility of interfacing with the
 * Database class to write and read information to and from the database.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ATM {
  private Scanner in;
  private BankAccount account;
  private BankAccount destination;
  private Database db;
  
  public ATM() throws FileNotFoundException, IOException
  {
    account = null;
    destination = null;
    db = new Database("accounts-db.txt");
  }

  public BankAccount getAccount() {
    return account;
  }

  public void setAccount(BankAccount account) {
    this.account = account;
  }

  public void run() throws InterruptedException, FileNotFoundException, IOException {
    in = new Scanner(System.in);
    boolean running = true;
    boolean mainMenu = true;
    boolean accountCheck = false;
    System.out.println("This is a more advanced ATM. Choose what you wish to do.");
    while (running == true) {
        try {
            if (accountCheck == false) {
                int selection = in.nextInt();
                showMainMenu();
                if (selection == 1) {
                    accountCheck = createAccount();
                    break;
                }
                else if (selection == 2) {
                    accountCheck = login();
                    break;
                }
                else if (selection == 3) {
                    running = false;
                }
            }
            else {
                showMenu();
                int selection = in.nextInt();
                if (selection == 1) {
                    deposit();
                    break;
                }
    		    else if (selection == 2) {
    		      withdraw();
    		      break;
    		    }
    		    else if (selection == 3) {
    		      transfer();
    		      break;
    		    }
    		    else if (selection == 4) {
      		      checkBalance();
      		      break;
      		        }
    		    else if (selection == 5) {
      		      checkPersonal();
      		      break;
                }
    		    else if (selection == 6) {
      		      updatePersonal();
      		      break;
      		    }
      		    else if (selection == 7) {
      		      closeAccount();
      		      break;
      		    }
      		    else if (selection == 8) {
      		        mainMenu = true;
      		    }
            }
        }
            catch (InputMismatchException e) {
                in.nextLine();
            }
        }
        System.out.println("Thank you for using this ATM.");
    }
    
  public boolean createAccount() throws InterruptedException {
      System.out.println("Enter personal information as it is requested. Be sure to stay within character limits. Type \"-1\" to cancel.");
      in.nextLine();
      
      String firstName = null;
      String lastName = null;
      int dob = 0;
      long phoneNumber = 00000000000000000;
      String address = null;
      String city = null;
      String state = null;
      int zipcode = 0;
      int pin = 000000;
      
      while (firstName == null || firstName.length() == 0) {
          System.out.print("Enter first name (Max 15 characters): ");
          firstName = in.nextLine();
          if (firstName.equals("-1")) { 
              return false;
          }
      }
      while (lastName == null || lastName.length() == 0) {
          System.out.print("Enter last name (Max 20 characters): ");
          lastName = in.nextLine();
          if (lastName.equals("-1")) { 
              return false;
          }
      }
      while (dob == 0) {
          System.out.print("Enter date of birth (format of YYYYMMDD): ");
          dob = in.nextInt();
          if (dob == -1) {
              return false;
          }
      }
      while (phoneNumber == 00000000000000000) {
          System.out.print("Enter phone number (no formatting, just 10 digits): ");
          phoneNumber = in.nextLong();
          if (phoneNumber == -1) {
              return false;
          }
      }
      while (address == null || address.length() == 0) {
          System.out.print("Enter street address (Max 30 characters): ");
          address = in.nextLine();
          if (address.equals("-1")) { 
              return false;
          }
      }
      while (city == null || city.length() == 0) {
          System.out.print("Enter city (Max 30 characters): ");
          city = in.nextLine();
          if (city.equals("-1")) { 
              return false;
          }
      }
      while (state == null || state.length() != 2) {
          System.out.print("Enter state abbreviation (2 characters): ");
          state = in.nextLine();
          if (state.equals("-1")) { 
              return false;
          }
      }
      while (zipcode == 000000 || zipcode > 99999 || zipcode < 00000) {
          System.out.print("Enter zip or postal code (5 digits): ");
          zipcode = in.nextInt();
          if (zipcode == -1) { 
              return false;
          }
      }
      while (pin == 000000 || pin > 9999 || pin < 0000) {
          System.out.print("Enter PIN (4 digits): ");
          pin = in.nextInt();
          if (pin == -1) {
              return false;
          }
      }
      account = new BankAccount('Y', db.getMaxAccountNumber() + 1L, 0.0D, new User(pin, dob, phoneNumber, firstName, lastName, address, city, state, zipcode));
      
      System.out.println("Account successfully created.");
      System.out.println("Welcome to the enhanced ATM. What do you wish to do?");
      
      return true;
  }
  
  public boolean closeAccount() throws IOException, InterruptedException {
	    System.out.println("Are you sure you wish to close your account?");
	    boolean validated = false;
	    in.nextLine();
	    while (validated == false) {
	    	System.out.print("Confirm (Y/N): ");
	    	char response = in.next().charAt(0);
	      
	      if (response == 'y' || response == 'Y') {
	        account.setStatus('N');
	        db.updateAccount(account, destination);
	        account = null;
	        System.out.println("Account closed.");  
	        return false; 
	      }
	      if (response == 'n' || response == 'N') {
	        System.out.println("Account will remain open.");
	        return true;
	      }
	    }
	    return false;
	  }
  
  public boolean login() throws FileNotFoundException, IOException {
		    boolean validated = false;
		    long accountNumber = 00000000000;
		    System.out.println("Enter your account number and PIN below. To cancel. type -1.");
		    while (validated == false) {
		      try {
		        if (accountNumber == 00000000000) {
		          System.out.print("Account Number: ");
		          accountNumber = in.nextLong();
		        }
		        else { 
		        	if (accountNumber == -1) {
		            System.out.println("\nCanceling and returning to the previous menu.");
		            break;
		        }
		        System.out.print("PIN: ");
		        int pin = in.nextInt();
		        if (pin == -1) {
		            System.out.println("Login cancelled.");
		            break; }
		          if (isValidAccount(accountNumber, pin)) {
		            validated = true;
		            System.out.println("Successfully logged in. What do you wish to do?");
		          }
		        }
		      } 
		      catch (InputMismatchException e) {
		        in.nextLine();
		      }
		    }
		    return validated;
		  }
  
  public boolean logout() throws IOException {
		    System.out.println("Thank you for using this ATM.");
		    if (account != null) {
		    	db.updateAccount(account, destination);
		    }
		    if (destination != null) {
		    	db.updateAccount(account, account);
		    }
		    account = null;
		    destination = null;
		    return false;
		  }
  
  public boolean check(long accountNumber, int pin) {
    return (account.getAccountNumber() == accountNumber) && (account.getUser().getPIN() == pin);
  }

  public void showMainMenu() {
	  System.out.println("   [1] Login\n   [2] Open Account\n   [3] Quit");
	  System.out.print("What do you wish to do?: ");
  }
  
  public void showMenu() {
    System.out.println("   [1] Deposit\n   [2] Withdraw\n   [3] Transfer Funds\n   [4] View Balance\n   [5] View Personal Information\n   [6] Update Personal Information\n   [7] Close Account\n   [8] Logout");
    System.out.print("What do you wish to do?: ");
  }
  
  public void showUpdateMenu() {
	  System.out.println("   [1] PIN\n   [2] Phone Number\n   [3] Address");
	  System.out.print("What do you wish to do?: ");
  }

  public void deposit() {
    boolean valid = false;
    while (!valid) {
      System.out.print("Enter the amount you wish to add. To cancel, type -1: ");
      double amount = in.nextDouble();
      if (amount == -1) {
    	  System.out.println("Cancelled deposit.");
    	  break;
      }
      if (amount <= 0 && amount != -1) {
          System.out.println("Invalid. Value is either negative or zero.");
          break;
      }
      else if (account.deposit(amount) == 1) {
        System.out.println(format(amount) + " have been deposited into your account.");
        System.out.println("Your current balance is " + format(account.getBalance()) + ".");
        valid = true;
      }      
    }
  }

  public void withdraw() {
    if (account.getBalance() == 0) {
      System.out.println("There is no money in your account.");
    }
    else {
      boolean valid = false;
      while (valid == false) {
    	System.out.print("Enter the amount you wish to withdraw. To cancel, type -1: ");
        double amount = in.nextDouble();
        if (amount == -1) {
        	System.out.println("Canelled withdraw.");
        	break;
        }
        int result = account.withdraw(amount);
        if (result == 0) {
            System.out.println("You are attempting to withdraw more money than you have.");
            break;
        }
        else if (result == 1) {
            System.out.println("You are attempting to withdraw nothing.");
            break;
        }
        else if (result == 2) {
            System.out.println(format(amount) + " have been withdrawn from your account.");
            System.out.println("Your current balance is " + format(account.getBalance()) + ".");
            valid = true;
        }
      }
    }
  }
  
  public void transfer() throws FileNotFoundException, IOException {
		    if (account.getBalance() == 0) {
		      System.out.println("There is no money in your account.");
		    }
		    else {
			  boolean accountValid = false;
		      boolean amountValid = false;
		      long accountNumber = -1000000000;
		      for (int v = 0; !accountValid; v++) {
		      try {
		          System.out.print("Enter account number you wish to transfer to: ");
		          accountNumber = in.nextLong();
		          
		          if (accountNumber == -1) {
		            System.out.println("Cancelled transfer."); 
		            break;
		          }
		          destination = db.getAccount(accountNumber);
		        }
		        catch (InputMismatchException e) {
		          in.nextLine();
		        }
		        try {
		        	System.out.print("Enter amount you wish to transfer: ");
		        	double amount = in.nextDouble();
		            if (amount == -1) {
		            	System.out.println("Cancelled transfer.");
		            }
		            if (amount <= 0) {
		                throw new IllegalArgumentException();
		            }
		            
		            
		            if (account.transfer(destination, amount) == 0) {
		            	System.out.println("You are attempting to transfer nothing.");
		            	break;
		            }
		            else if (account.transfer(destination, amount) == 1) {
		            	System.out.println("You are attempting to transfer more money than you have in your account.");
		            	break;
		            }
		            else if (account.transfer(destination, amount) == 2) {
		            	System.out.println("Invalid account number entered.");
		            	accountValid = false;
		            	amountValid = true;
		            	break;
		            }
		            else if (account.transfer(destination, amount) == 3) {
		            	System.out.println("Successful transfer. Your current balance is " + format(account.getBalance()) + ".");
		            	accountValid = true;
		            	amountValid = false;
		            }
		        }
		        catch (InputMismatchException e) {
		          in.nextLine();
		          System.out.println("You entered a letter or other illegal character.");
		        } catch (IllegalArgumentException e) {
		          System.out.println("Zero is an invalid amount.");
		        }
		      }
		    }
  
  }
  		    
  public void checkPersonal()
  {
    System.out.println("\n     Account # : " + account.getAccountNumber());
    System.out.println("Account Holder : " + account.getUser().getName());
    System.out.println("       Address : " + account.getUser().getAddress());
    System.out.println("                 " + account.getUser().formatAddress());
    System.out.println(" Date of Birth : " + account.getUser().formatDOB());
    System.out.println("     Telephone : " + account.getUser().formatPhoneNumber());
  }

  public void updatePersonal() {
	boolean valid = false;
	System.out.println("Select which information you wish to update. To cancel, type -1.");
    
    while (valid == false) {
      try {
        showUpdateMenu();
        int selection = in.nextInt();
        if (selection == -1) {
        	valid = true;
        	break;
        }
        else if (selection == 1) {
        	valid = updatePIN();
        	break;
        }
        else if (selection == 2) {
        	valid = updatePhoneNumber();
        	break;
        }
        else if (selection == 3) {
        	valid = updateAddress();
        	break;
        }
      } 
      catch (InputMismatchException e) {
        in.nextLine();
      }
    }
  }
  
  private boolean updatePIN() {
    String current = null;
    boolean checkCurrent = false;
    boolean newPIN = false;
    while (checkCurrent == false) {
      try {
        System.out.print("Enter current PIN. To cancel, type -1: ");
        current = in.next();
        if (current.equals("-1")) {
          System.out.println("PIN update cancelled.");
          return false;
        }
        if (Integer.parseInt(current) == account.getUser().getPIN()) {
          checkCurrent = true;
        }
      }
      catch (NumberFormatException e) {
        in.nextLine();
      }
    }
    
    while (newPIN == false) {
      try {
        System.out.print("Enter new PIN (4 digits). To cancel, type -1: ");
        String pin = in.next();
        if (pin.equals("-1")) {
          System.out.println("PIN update cancelled.");
          return false;
        }
        if (pin.length() == 4) {
          account.getUser().setPIN(Integer.parseInt(current), Integer.parseInt(pin));
          if (Integer.parseInt(current) != Integer.parseInt(pin)) {
            System.out.println("PIN successfully changed.");
          }
          else {
            System.out.println("New PIN is the same as old PIN. You just wasted your time.");
          }
          return true;
        }
      }
      catch (InputMismatchException e) {
        in.nextLine();
      }
    }
    return false;
  }

private boolean updatePhoneNumber() {
    int phone = -1;
    boolean valid = false;
    
    while (valid == false) {
      try {
        System.out.print("Enter new phone number. Do not format. Just type 10 digits. To cancel, type -1: ");
        phone = in.nextInt();
        
        if (phone == -1) {
          System.out.println("Cancelled phone number update.");
          return false;
        }
        if (Long.toString(phone).length() != 10) {
          System.out.println("Invalid number. Phone numbers must be 10 digits long.");
        }
        else {
          String current = account.getUser().formatPhoneNumber();
          account.getUser().setPhoneNumber(phone);
          
          if (!(current.equals(account.getUser().formatPhoneNumber()))) {
            System.out.println("Successfully updated phone number.");
          }
          else {
            System.out.println("New phone number is the same as the previous. You just wasted your time.");
          }
          return true;
        }
      } catch (InputMismatchException e) {
        in.nextLine();
        System.out.println("Do not use characters.");
      }
    }
    return false;
  }

private boolean updateAddress() {
    String address = null;
    String city = null;
    String state = null;
    int zipcode = 0;
    boolean valid = false;
    in.nextLine();
    System.out.println();
    while (valid == false) {
      System.out.print("Enter new street address. Keep within 30 characters. To cancel, type -1: ");
      address = in.nextLine();
      if ((address == null) || (address.equals("-1"))) {
        System.out.println("Cancelled update address.");        
        return false;
      }
      if (address.length() == 0) {
        valid = false;
      }
      else {
        valid = true;
      }
    }
    valid = false;    
    while (valid == false) {
      System.out.print("Enter new city. Keep within 30 characters. To cancel, type -1: ");
      city = in.nextLine();
      if ((city == null) || (city.equals("-1"))) {
        System.out.println("Cancelled update city.");        
        return false;
      }
      if (city.length() == 0) {
        valid = false;
      }
      else {
        valid = true;
      }
    }
    valid = false;
    while (valid == false) {
      System.out.print("Enter two-letter abbreviation of new state. To cancel, type -1: ");
      state = in.nextLine();
      if ((state == null) || (state.equals("-1"))) {
        System.out.println("Cancelled state update.");
        return false;
      }
      if (state.length() != 2) {
        valid = false;
      }
      else {
        valid = true;
      }
    }
    valid = false;    
    while (valid == false) {
      System.out.print("Enter new zip or postal code. Must be 5 characters long. To cancel, type -1: ");
      zipcode = in.nextInt();
      if ((zipcode == 0) || (zipcode == -1)) {
        System.out.println("Cancelled update zipcode.");
        return false;
      }
      if (!(zipcode < 100000 && zipcode > 9999)) {
        valid = false;
      }
      else {
        valid = true;
      }
    }
    if (valid == true) {
      String first = account.getUser().getAddress() + "\n" + account.getUser().formatAddress();
      account.getUser().setAddress(address);
      account.getUser().setCity(city);
      account.getUser().setState(state);
      account.getUser().setZipcode(zipcode);
      String update = account.getUser().getAddress() + "\n" + account.getUser().formatAddress();
      if (!first.equals(update)) {
        System.out.println("Address successfully updated.");
      }
      else {
        System.out.println("The new address is the exact same as the old address. You just wasted your time.");
      }
      return true;
    }
    return false;
  }




  public void checkBalance() {
    System.out.println("Your current balance is " + format(account.getBalance()) + ".");
  }
  
  private boolean isValidAccount(long accountNumber, int pin) throws FileNotFoundException, IOException {
		    account = db.getAccount(accountNumber);
		    if ((account != null) && (account.getAccountNumber() == accountNumber) && (account.getUser().getPIN() == pin)) {
		      return true;
		    }
		    account = null;
		    return false;
		  }

  private static String format(double amount) {
    return "$" + String.format("%,.2f", amount);
  }

  private static int format(String dob)  {
    try {
      return Integer.valueOf(String.format("%4d%02d%02d", new Object[] {
        Integer.valueOf(Integer.parseInt(dob.substring(6, 10))), 
        Integer.valueOf(Integer.parseInt(dob.substring(0, 2))), 
        Integer.valueOf(Integer.parseInt(dob.substring(3, 5))) })).intValue();
    }
    catch (NumberFormatException e) {}
    return -1;
  }

}
