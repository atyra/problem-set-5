import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * This class has only one responsibility: start the ATM program!
 */

public class Tester {
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	
	  public Tester() {}

	  public static void main(String[] args)
	    throws InterruptedException {
	    try {
	      ATM atm = new ATM();
	      atm.run();
	    }
	    catch (InterruptedException e) {
	      System.out.println("Something has gone wrong. Shutting down.");
	    }
	    catch (FileNotFoundException e) {
	      System.out.println("Unable to locate databse file.");
	    }
	    catch (IOException e) {
	      System.out.println("Unable to access database file.");
	    }
	  }

}