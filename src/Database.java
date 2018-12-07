import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Database {
	
	private String path;
	private String[] accounts;
	
	public Database(String path) throws FileNotFoundException, IOException {
		this.path = path;
		this.accounts = getAllAccounts();
	}
	
	/**
	 * Initializes the database with all accounts.
	 * 
	 * @return an array of all accounts
	 */
	
	public String[] getAllAccounts() throws FileNotFoundException, IOException {
		int count = 0;
		String[] accounts = new String[10];
		
		FileReader altered = null;
		InputStreamReader original = null;
		try {
			altered = new FileReader(System.getProperty("user.dir") + File.separator + path);			
		} catch (FileNotFoundException e) {
			original = new InputStreamReader(getClass().getResourceAsStream(path));
		}
		
		try (BufferedReader br = new BufferedReader(original != null ? original : altered)) {
			String line;
			
			while ((line = br.readLine()) != null) {
				if (count >= accounts.length) {
					accounts = Arrays.copyOf(accounts, accounts.length + 10);
				}
				accounts[count++] = line;
			}
		}
		
		return Arrays.copyOf(accounts, count);
	}
	
	/**
	 * Retrieves an account by account number.
	 * 
	 * @param accountNumber the acocunt number of the account to retrieve
	 * @return a BankAccount
	 */
	
	public BankAccount getAccount(long accountNumber) {
		for (String account : accounts) {
			if (account.startsWith(String.valueOf(accountNumber)) && account.endsWith("Y")) {
				return new BankAccount(account.charAt(148), Long.parseLong(account.substring(0, 8), Double.parseDouble(account.substring(13, 27))), new User(Integer.parseInt(account.substring(9, 12)), Integer.parseInt(account.substring(63, 70)), Long.parseLong(account.substring(71, 80)), account.substring(48, 62), account.substring(28, 47), account.substring(81, 110), account.substring(111, 140), account.substring(141, 142), Integer.parseInt(account.substring(143, 147))));
			}
		}
		
		return null;
	}
	
	/**
	 * Updates a BankAccount.
	 * 
	 * @param account the primary account being updated
	 * @param destination the secondary account being updated
	 * @throws IOException 
	 */
	
	public void updateAccount(BankAccount account, BankAccount destination) throws IOException {
		boolean newAccount = true;
		
		for (int i = 0; i < accounts.length; i++) {			
			if (accounts[i].startsWith(String.valueOf(account.getAccountNumber()))) {
				accounts[i] = account.toString();
				newAccount = false;
			}
			
			if (destination != null) {
				if (accounts[i].startsWith(String.valueOf(destination.getAccountNumber()))) {
					accounts[i] = destination.toString();
				}
			}
		}
		
		if (newAccount) {
			accounts = Arrays.copyOf(accounts, accounts.length + 1);
			accounts[accounts.length - 1] = account.toString();
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + File.separator + path))) {
			for (String acct : accounts) {
				bw.write(acct);
				bw.newLine();
			}
		}
	}
	
	/**
	 * Retrieves the largest account number in the database.
	 * 
	 * @return the largest account number
	 */
	
	public long getMaxAccountNumber() {
		long max = -1L;
		
		for (String account : accounts) {
			long accountNumber = Long.parseLong(account.substring(0, 9));
			
			if (accountNumber > max) {
				max = accountNumber;
			}
		}
		
		return max;
	}
}