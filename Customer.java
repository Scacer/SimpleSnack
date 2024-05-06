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
        return "\ncustomerID: " + customerID + "\nName: " + name + "\nBalance: " + balance;
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
                throw new InsufficientBalanceException("Customer's balance is insufficient for this purchase - ");
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

// Test Harness

}
