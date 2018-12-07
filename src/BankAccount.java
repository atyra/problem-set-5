public class BankAccount {
	
	private static long generatedAccountNumber = 100000001L;
	
	private long accountNumber;
	private double balance;
	private User user;
	private char status;
	
	public BankAccount(char status, long accountNumber, double balance, User user) {
		this.accountNumber = BankAccount.generatedAccountNumber++;
		this.balance = balance;
		this.user = user;
		this.status = status;
	}
	
	/////////////////////////////////// GETTERS AND SETTERS ///////////////////////////////////
	
	public long getAccountNumber() {
		return accountNumber;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public User getUser() {
		return user;
	}
	
	public char getStatus() {
	    return status;
	}
	
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setStatus(char status) {
	    this.status = status;
	}
	
	/////////////////////////////////// INSTANCE METHODS ///////////////////////////////////
	
	public int deposit(double amount) {
		if (amount <= 0) {
			return 0;
		} else {
			balance = balance + amount;
			
			return 1;
		}
	}
	
	public int withdraw(double amount) {
		if (amount > balance) {
			return 0;
		} else if (amount <= 0) {
			return 1;
		} else {
			balance = balance - amount;
			
			return 2;
		}
	}
	
	public int transfer(BankAccount destination, double amount) {
	    if (destination == null) {
	        return 0;
        }
        int status = withdraw(amount);
        if (status == 2) {
            status = destination.deposit(amount);
        }
        return status;
    }
    
    public String toString() {
    return String.format("%09d%04d%-15.2f%-20s%-15s%8d%10d%-30s%-30s%2s%5s%s", new Object[] {
        Long.valueOf(accountNumber), 
        Integer.valueOf(user.getPIN()), 
        Double.valueOf(balance), 
        user.getLastName(), 
        user.getFirstName(), 
        Long.valueOf(user.getDOB()), 
        Long.valueOf(user.getPhoneNumber()), 
        user.getAddress(),
        user.getCity(), 
        user.getState(), 
        user.getZipcode(), 
        Character.valueOf(status) });
    }
}