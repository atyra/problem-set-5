/**
 * Just like last time, the User class is responsible for retrieving
 * (i.e., getting), and updating (i.e., setting) user information.
 * This time, though, you'll need to add the ability to update user
 * information and display that information in a formatted manner.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */


public class User {
	
	private long accountNumber;
	private int pin;
	private String firstName;
	private String lastName;
	private int dob;
	private String address;
	private long phoneNumber;
	private String city;
	private String state;
	private int zipcode;
	private char accountStatus;
	
	public User(int pin, int dob, long phoneNumber, String firstName, String lastName, String address, String city, String state, int zipcode) {
		this.pin = pin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	public long getAccountNumber() {
		return accountNumber;
	}
	
	public int getPIN() {
		return pin;
	}
	
	 public String getFirstName() {
	    return firstName;
	}
	  
    public String getLastName() {
	    return lastName;
	}
	
	public String getName() {
		return lastName + ", " + firstName;
	}
	
	public long getDOB() {
		return dob;
	}
	
	public String getAddress() {
		return address;
	}
	
	public long getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public int getZipcode() {
		return zipcode;
	}
	
	public char getAccountStatus() {
		return accountStatus;
	}
	
	public void setPIN(int current, int pin) {
	    if ((pin < 0) || (pin > 9999)) {
	    	return;
	    }
	    if (this.pin != current) {
	    	return;
	    }
	    this.pin = pin;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setZipcode(int zipcode) {
	    this.zipcode = zipcode;
	}
	
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String formatPhoneNumber() {
	    return "(" + Integer.parseInt(String.valueOf(phoneNumber).substring(0, 3)) + ") " + Integer.parseInt(String.valueOf(phoneNumber).substring(3, 6)) + "-" + Integer.parseInt(String.valueOf(phoneNumber).substring(6, 10));
	}
	
	public String formatDOB() {
	    int year = Integer.parseInt(String.valueOf(dob).substring(0, 4));
	    int month = Integer.parseInt(String.valueOf(dob).substring(4, 6));
	    int day = Integer.parseInt(String.valueOf(dob).substring(6, 8));
	    
	    return month + " " + day + ", " + year;
	}
	
	public String formatAddress() {
	    return city + ", " + state + " " + zipcode;
	}
}