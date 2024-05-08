import java.util.HashMap;

public class SnackShop {

// Attributes
    private String name;
    private int turnover;
    private HashMap<String, Snack> stockedSnacks;
    private HashMap<String, Customer> customerAccounts;

// Constructor
    public SnackShop(String name){
        this.name = name;
        this.turnover = 0;
    }

// Accessor Methods
    public String toString(){
        return "SnackShop{name: " + name + ", turnover: " + turnover;
    }

    public String getName(){
        return name;
    }

    public int getTurnover(){
        return turnover;
    }

// Mutator Methods
    public void setName(String name){
        this.name = name;
    }

// Service Methods
    public void addCustomer(String customerID, Customer newCustomer){
        try{
            if ( customerExists(customerID) ){
                customerAccounts.put(customerID, newCustomer);
            }
            else {
                throw new InvalidCustomerException("A duplicate customerID was entered - customerID values should be unique!");
            }
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }

    public void addSnack(String snackID, Snack newSnack){
        stockedSnacks.put(snackID, newSnack);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        if ( customerExists(customerID) ){
            return customerAccounts.get(customerID);
        }
        throw new InvalidCustomerException("There is no customer account associated with customerID: " + customerID);   
    }
        

// Support Methods
    private boolean customerExists(String customerID){
        if ( customerAccounts.get(customerID) != null){
            return true;
        }
        return false; 
    }

    
}
