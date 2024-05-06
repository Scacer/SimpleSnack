import java.util.regex.Pattern;

public class Customer {

// Attributes
    protected String customerID, name;
    protected int balance;

// Constructors
    public Customer(String customerID, String name) throws InvalidCustomerException{
        if (isValidCustomerID(customerID)){
            this.customerID = customerID;
            this.name = name;
            this.balance = 0;
        }    
    }

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
        return "\ncustomerID: " + customerID + "\nName: " + name + "\nBalance: " + getBalanceString();
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
    public void addFunds(int amount){
        if (amount > 0){
            balance = balance + amount;
        }
    }

    public int chargeAccount(int snackPrice){
        int dummyBalance = balance;

        try{
            if ( (dummyBalance - snackPrice) > -1){
                balance = balance - snackPrice;
                return snackPrice;
            }
            else {
                throw new InsufficientBalanceException("Customer's balance is insufficient for this purchase - snackPrice must be equal to or less than Customer balance!");
            }
        }
        catch (InsufficientBalanceException errString){
            System.err.println(errString);
            return 0;
        }
    }

// Support Methods
    private boolean isValidCustomerID(String customerID) throws InvalidCustomerException{
        if (Pattern.matches("[A-Z0-9]{6}", customerID)){
            return true;
        }
        else{
            throw new InvalidCustomerException("Invalid customerID provided - customerID must be a String of 6 alphanumeric characters!");
        }
    }

    private String getBalanceString(){
        float decimalBalance = balance / 100;
        return String.format("£ %,.2f", decimalBalance);
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
            System.out.println(fundTestCustomer.getBalance());
            System.out.println(fundTestCustomer.chargeAccount(300));
            System.out.println(fundTestCustomer.getBalance());

            System.out.println("\nTest 7\n");

            // The following lines should attempt to charge the account for a specified amount which is above the balance of the customer. This should
            //  throw (and catch) an internal InsufficientBalanceException and not alter the customer balance, as validated by the print statements.
            System.out.println(fundTestCustomer.getBalance());
            fundTestCustomer.chargeAccount(500);
            System.out.println(fundTestCustomer.getBalance());
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }

}
