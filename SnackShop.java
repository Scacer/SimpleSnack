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
            if (customerAccounts.get)
            customerAccounts.put(customerID, newCustomer);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }

    public void addSnack(String snackID, Snack newSnack){
        stockedSnacks.put(snackID, newSnack);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        Customer tempCustomer = customerAccounts.get(customerID);

        if (tempCustomer != null){
            return tempCustomer;
        }
        else{
            throw new InvalidCustomerException("There is no customer account associated with customerID: " + customerID);
        }
    }
        

// Support Methods

    
}
