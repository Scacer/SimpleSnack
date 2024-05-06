public class StudentCustomer extends Customer {

// Constructors
    public StudentCustomer(String customerID, String name) throws InvalidCustomerException{
        super(customerID, name);
    }

    public StudentCustomer(String customerID, String name, int balance) throws InvalidCustomerException{
        super(customerID, name, balance);
    }

// Accessor Methods
    public String toString(){
        return "StudentCustomer{customerID: " + customerID + ", name: " + name + ", balance: " + balance + "}"; 
    }

    public

// Service Methods
    public int chargeAccount(int snackPrice){
        snackPrice = Math.round( (float)(snackPrice * getDiscountAmount()) );

        int dummyBalance = balance;
        try{
            if ( (dummyBalance - snackPrice) > -501){
                balance = balance - snackPrice;
                return snackPrice;
            }
            else{
                throw new InsufficientBalanceException("Customer's balance is insufficient for this purchase - Students can have a negative balance of up to £5.00!");
            }
        }
        catch (InsufficientBalanceException errString){
            System.err.println(errString);
            return 0;
        }
        
    }

    public static double getDiscountAmount(){
        return 0.95;
    }

// Support Methods

// Test Harness


}
