import java.util.ArrayList;

public class SnackShop {

// Attributes
    private String name;
    private int turnover;
    private ArrayList<Snack> stockedSnacks;
    private ArrayList<Customer> customerAccounts;

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
    public void addCustomer(Customer newCustomer){
        customerAccounts.add(newCustomer);
    }

    public void addSnack(Snack newSnack){
        stockedSnacks.add(newSnack);
    }
        

// Support Methods

    
}
