/******************************************************************************************************************
 * public class StudentCustomer:
 *  The StudentCustomer class extends Customer by adding extra functionality for a student discount as well as
 *  allowing a negative balance of up to -500, providing Students with a £5 credit.
 ******************************************************************************************************************/
public class StudentCustomer extends Customer {

// CLass Variables
    // studentDiscount is consistent across all StudentCustomer objects
    private static double studentDiscount = 0.95;

// Constructors
    // This constructor takes arguments for the attributes customerID and name, utilising the superclass constructor, setting balance to 0;
    public StudentCustomer(String customerID, String name) throws InvalidCustomerException{
        super(customerID, name);
    }

    // This constructor takes arguments for all attributes - customerID, name, balance and utilises the superclass constructor, setting all
    //  three attributes to the values provided.
    public StudentCustomer(String customerID, String name, int balance) throws InvalidCustomerException{
        super(customerID, name, balance);
    }

// Accessor Methods
    public String toString(){
        return "StudentCustomer{customerID: " + customerID + ", name: " + name + ", balance: " + balance + "}"; 
    }

// Service Methods
    // chargeAccount(int) overrides the method provided by Customer. This method applies the student discount before attempting
    //  to charge the customer, which is done by checking if subtraction of the discounted price from the balance will decrease the balance
    //  below -500.
    public int chargeAccount(int snackPrice) throws InsufficientBalanceException{
        snackPrice = (int)Math.ceil( (float)(snackPrice * getDiscountAmount()) );

        int dummyBalance = balance;
        if ( (dummyBalance - snackPrice) > -501){
            balance = balance - snackPrice;
            return snackPrice;
        }
        else{
            throw new InsufficientBalanceException("Customer's balance is insufficient for this purchase - Students can have a negative balance of up to £5.00!");
        }
        
    }
    
// Support Methods
    // getDiscountAmount() returns the private static attribute studentDiscount.
    private static double getDiscountAmount(){
        return studentDiscount;
    }

// Test Harness

    public static void main(String args[]){

        System.out.println("Test 1\n");

        // The following code should retrieve the discount amount for students
        System.out.println(StudentCustomer.getDiscountAmount());

        System.out.println("\nTest 2\n");

        // The following try block should not throw an InvalidCustomerException as all values entered are within expected ranges, however
        //  the code should demonstrate the operation of the unique methods implented in the StudentCustomer Class.
        try{
            StudentCustomer testCustomer = new StudentCustomer("A1B2C3", "testCustomer", 1500);

            // The following line should print information about the StudentCustomer, demonstrating that it distinguishes the studentCustomer object from
            //  a regular Customer object.
            System.out.println(testCustomer);

            System.out.println("\nTest 3\n");

            // The following code should charge the customer for a snack, reducing the snackPrice by 5% and returning the amount charged. There are two
            //  print statements either side of the method call so that the reduction can be validated.
            System.out.println(testCustomer.getBalance());
            System.out.println(testCustomer.chargeAccount(500));
            System.out.println(testCustomer.getBalance());

            System.out.println("\nTest 4\n");

            // The following code should create a new StudentCustomer and test the funcitonality of the £5 credit provided by the class. The balance will be
            //  set to 0 initially, and will then be charged for an amount less than 500p (£5).
            StudentCustomer testCustomer2 = new StudentCustomer("B1C2D3", "testCustomer2");

            System.out.println(testCustomer2.getBalance());
            System.out.println(testCustomer2.chargeAccount(500));
            System.out.println(testCustomer2.getBalance());

            System.out.println("\nTest 5\n");

            // The following code should attempt to charge the account a further amount which would be large enough to cause the customer's balance to
            //  decrease below -500, therefore the method should throw an InsufficientBalanceException and should not alter the balance.
            System.out.println(testCustomer2.getBalance());
            System.out.println(testCustomer2.chargeAccount(200));
            System.out.println(testCustomer2.getBalance());
            
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
        catch (InsufficientBalanceException errString){
            System.err.println(errString);
        }
    }

}
