public class StaffCustomer extends Customer{

// Attributes
    private String school;

// Constructors

    public StaffCustomer(String customerID, String name, String school) throws InvalidCustomerException{
        super(customerID, name);
        this.school = school;
    }

    public StaffCustomer(String customerID, String name, int balance, String school)throws InvalidCustomerException{
        super(customerID, name, balance);
        this.school = school;
    }
    
}
