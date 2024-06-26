/******************************************************************************************************************
 * public class Customer:
 *  The Customer class provides a framework for storing basic information about and providing functionality for
 *  a customer's account - this includes manipulating a customer's balance by adding funds or charging the account.
 ******************************************************************************************************************/
import java.util.regex.Pattern;

public class Customer {

// Attributes
    protected String customerID, name;
    protected int balance;

// Constructors
    // This constructor takes two arguments for the Customer attributes customerID and name - balance is automatically set to 0.
    public Customer(String customerID, String name) throws InvalidCustomerException{
        if (isValidCustomerID(customerID)){
            this.customerID = customerID;
            this.name = name;
            this.balance = 0;
        }    
    }

    // This constructor takes arguments for all Customer attributes - customerID, name, and balance.
    public Customer(String customerID, String name, int balance) throws InvalidCustomerException{
        if (isValidCustomerID(customerID) && balance > -1){
            this.customerID = customerID;
            this.name = name;
            this.balance = balance;
        }
        else{
            throw new InvalidCustomerException("Invalid balance provided - balance must be 0 or more!");
        }
    }

// Accessor Methods
    public String toString(){
        return "Customer{customerID: " + customerID + ", name: " + name + ", balance:" + balance + "}";
    }

    public String getCustomerID(){
        return customerID;
    }

    public String getName(){
        return name;
    }

    public int getBalance(){
        return balance;
    }


// Service Methods
    // addFunds(int) takes an amount as an argument - if this amount is larger than zero it increases the balance attribute by the amount.
    public void addFunds(int amount){
        if (amount > 0){
            balance = balance + amount;
        }
    }

    // chargeAccount(int) takes a snack price as an argument, if the customer balance will not decrease below 0 when subtracting the snack price from balance, the
    //   customer is charged for it by decreasing the balance attribute by the snack price.
    public int chargeAccount(int snackPrice) throws InsufficientBalanceException{
        int dummyBalance = balance;

        
        if ( (dummyBalance - snackPrice) > -1){
            balance = balance - snackPrice;
            return snackPrice;
        }
        else {
            throw new InsufficientBalanceException("Customer's balance is insufficient for this purchase - snackPrice must be equal to or less than Customer balance!");
        }
    }

// Support Methods
    // isValidCustomer(String) takes a customer ID as an argument and validates it by ensuring it follows the format provided for customerID values.
    private boolean isValidCustomerID(String customerID) throws InvalidCustomerException{
        if (Pattern.matches("[A-Z0-9]{6}", customerID)){
            return true;
        }
        else{
            throw new InvalidCustomerException("Invalid customerID provided - customerID must be a String of 6 alphanumeric characters!");
        }
    }

// Test Harness
    public static void main(String args[]){

        System.out.println("Test 1\n");

        // The following try blocks should not throw an exception and should simply create a Customer object and then print information
        //  about the balance as all values entered are within the expected ranges.
        try{
            Customer testCustomer = new Customer("A1B2C3", "testCustomer");
            System.out.println(testCustomer.getBalance());
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }

        System.out.println("\nTest 2\n");

        try{
            Customer testCustomer2 = new Customer ("1A2B3C", "testCustomer2", 1500);
            System.out.println(testCustomer2.getBalance());

            // The following line should utilise the toString method to print information about the whole object.
            System.out.println(testCustomer2);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }

        System.out.println("\nTest 3\n");

        // The following try block should throw an InvalidCustomerException as the provided ID is not an alphanumeric sequence
        //  of 6 characters.
        try{
            Customer InvalidCustomerIDCustomer = new Customer("1234567", "InvalidCustomerIDCustomer");
            System.out.println(InvalidCustomerIDCustomer);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }

        // The following try block should throw an InvalidCustomerException as the balance provided is negative.
        try{
            Customer InvalidBalanceCustomer = new Customer("123ABC", "InvalidBalanceCustomer", -200);
            System.out.println(InvalidBalanceCustomer);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }

        System.out.println("\nTest 4\n");

        // The following try block should not throw an InvalidCustomerException, but should test the internal mutator methods related to
        //  altering the balance attribute.
        try{
            Customer fundTestCustomer = new Customer("456DEF", "fundTestCustomer", 200);

            // The following lines should print the balance, add a positive amount to the balance, and print it again to validate that this worked.
            System.out.println(fundTestCustomer.getBalance());
            fundTestCustomer.addFunds(300);
            System.out.println(fundTestCustomer.getBalance());

            System.out.println("\nTest 5\n");

            // The following lines should print the balance, attempt to add a negative amount to the balance, and print it again to ensure the balance was not altered.
            System.out.println(fundTestCustomer.getBalance());
            fundTestCustomer.addFunds(-50);
            System.out.println(fundTestCustomer.getBalance());

            System.out.println("\nTest 6\n");

            // The following lines should successfully attempt to charge the account for a specified price which is below the balance. This will be
            //  validated by printing the balance before and after. The chargeAccount method should also return the amount deducted.
            try{
            System.out.println(fundTestCustomer.getBalance());
            System.out.println(fundTestCustomer.chargeAccount(300));
            System.out.println(fundTestCustomer.getBalance());
            }
            catch (InsufficientBalanceException errString){
                System.err.println(errString);
            }

            System.out.println("\nTest 7\n");

            // The following lines should attempt to charge the account for a specified amount which is above the balance of the customer. This should
            //  throw an InsufficientBalanceException and not alter the customer balance, as validated by the print statements.
            try{
                System.out.println(fundTestCustomer.getBalance());
                fundTestCustomer.chargeAccount(500);
                System.out.println(fundTestCustomer.getBalance());
            }
            catch (InsufficientBalanceException errString){
                System.err.println(errString);
            }
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }

}
