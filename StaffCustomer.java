public class StaffCustomer extends Customer{

// Enumerated type for school
    private enum Schools{CMP, BIO, MTH, OTHER};

// Attributes
    private Schools school;

// Constructors

    public StaffCustomer(String customerID, String name, String school) throws InvalidCustomerException{
        super(customerID, name);
        this.school = validateSchool(school);
    }

    public StaffCustomer(String customerID, String name, int balance, String school)throws InvalidCustomerException{
        super(customerID, name, balance);
        this.school = validateSchool(school);
    }

// Accessor Methods
    public String toString(){
        return "StaffCustomer{customerID: " + customerID + ", name: " + name + ", balance: " + balance + ", school: " + school + "}";
    }

    public Schools getSchool(){
        return school;
    }

// Service Methods

    public int chargeAccount(int snackPrice){
        double discountAmount = getDiscountAmount();
        snackPrice = Math.round( (float)(snackPrice * discountAmount) );

        int dummyBalance = balance;
        try{
            if ( (dummyBalance - snackPrice) > -1){
                balance = balance - snackPrice;
                return snackPrice;
            }
            else{
                throw new InsufficientBalanceException("Customer's balance is insufficient for this purchase - Staff do not have access to negative balance!");
            }
        }
        catch (InsufficientBalanceException errString){
            System.err.println(errString);
            return 0;
        }
    }

// Support Methods
    private Schools validateSchool(String school){
        Schools customerSchool;
        try{
            customerSchool = Schools.valueOf(school.toUpperCase());
            return customerSchool;
        }
        catch (IllegalArgumentException errString){
            customerSchool = Schools.OTHER;
            return customerSchool;
        }
    }
    
    private double getDiscountAmount(){
        double discountAmount = 1;
        if (school == Schools.CMP){
            discountAmount = 0.9;
        }
        else if (school == Schools.MTH || school == Schools.BIO){
            discountAmount = 0.98;
        }
        return discountAmount;
    }

// Test Harness
    public static void main(String args[]){
        System.out.println("Test 1\n");

        // The following try block should not throw an InvalidCustomerException, but should create 5 Different StaffCustomer objects to demonstrate the functionality
        //  of the validateSchool method.
        try{
            // The below objects' constructors are provided with school values consistent with the Schools enum within the StaffCustomer class.
            //  therefore, each object's "school" enum value should be identical to the input string.
            StaffCustomer testCustomerCMP = new StaffCustomer("ABCDE6", "testCustomerCMP", 500, "CMP");
            // The following line prints the object using the toString method, which we can see distinguishes this object as StaffCustomer rather than just a Customer.
            System.out.println(testCustomerCMP);
            StaffCustomer testCustomerMTH = new StaffCustomer("A2CDEF", "testCustomerMTH", 500, "MTH");
            System.out.println(testCustomerMTH);
            StaffCustomer testCustomerBIO = new StaffCustomer("AB3DEF", "testCustomerBIO", 500, "BIO");
            System.out.println(testCustomerBIO);
            StaffCustomer testCustomerOTHER = new StaffCustomer("ABC4EF", "testCustomerOTHER", 500, "OTHER");
            System.out.println(testCustomerOTHER);

            System.out.println("\nTest 2\n");
            // The below object is provided with a school value that does not exist within the Schools enum, therefore the school attribute should
            //  be set to OTHER.
            StaffCustomer testCustomerMisc = new StaffCustomer("ABCD56", "testCustomerMisc", "MISC");
            System.out.println(testCustomerMisc);

            System.out.println("\nTest 3\n");
            // The following statements will utilise an object created with school CMP to test the chargeAccount method for the appropriate discount.
            //  CMP gets a 10% discount so we can expect a snackPrice of 200 to charge 180, this is verified by printing the balance before and after.
            System.out.println(testCustomerCMP.getBalance());
            System.out.println(testCustomerCMP.chargeAccount(200));
            System.out.println(testCustomerCMP.getBalance());
            
            System.out.println("\nTest 4\n");
            // The following statements will utilise an object created with school BIO and MTH to test the chargeAccount method for the appropriate discount.
            //  These schools get a 2% discount, so we can expect a snackPrice of 200 to charge 196, this is verified by printing the balances before and after.
            System.out.println(testCustomerBIO.getBalance());
            System.out.println(testCustomerBIO.chargeAccount(200));
            System.out.println(testCustomerBIO.getBalance());

            System.out.println(testCustomerMTH.getBalance());
            System.out.println(testCustomerMTH.chargeAccount(200));
            System.out.println(testCustomerMTH.getBalance());

            System.out.println("\nTest 5\n");
            // The following statements will utilise an object created with school OTHER to test the chargeAccount method for the appropriate discount.
            //  OTHER schools do not get a discount, so we can expect the charge to be identical to the provided snackPrice, this is verified by printing the
            //  balances before and after.
            System.out.println(testCustomerOTHER.getBalance());
            System.out.println(testCustomerOTHER.chargeAccount(200));
            System.out.println(testCustomerOTHER.getBalance());
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }
}
