/******************************************************************************************************************
 * public class SnackShop:
 *  The SnackShop class models a Snack Shop, utilising collections of Customer and Snack objects in order to make
 *  transactions such as addition of new snacks and customers, as well as purchase of snacks by customers.
 ******************************************************************************************************************/
import java.util.ArrayList;
import java.util.HashMap;

public class SnackShop{

// Attributes
    private String name;
    private int turnover;

    // HashMaps
    //  In HashMaps keys are hashed to determine where the value will be stored. In this project
    //  Customers and Snacks are referenced by unique IDs - if we provide these IDs as the key
    //  and the Object as the value, we can find the location in memory of any Snack or Customer by 
    //  simply hashing the ID associated with it - this will allow for efficient retrievals in
    //  comparison to utilising other data structures where we may have to instead search for IDs.
    private HashMap<String, Customer> customerAccounts;
    private HashMap<String, Snack> stockedSnacks;

// Constructor
    // This constructor takes a single argument - name - which determines the name attribute, turnover is set automatically
    //  to 0, customerAccounts and stockedSnacks are set as empty HashMaps.
    public SnackShop(String name){
        this.name = name;
        this.turnover = 0;
        customerAccounts = new HashMap<String, Customer>();
        stockedSnacks = new HashMap<String, Snack>();
    }

// Accessor Methods
    public String toString(){
        return "SnackShop{name: " + name + ", turnover: " + turnover +"}";
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
    // addCustomer(String, Customer) takes a Customer object as an argument and registers this customer to this shop
    //  if it has a unique ID - if the customerID is duplicate an InvalidCustomerException will be thrown.
    public void addCustomer(Customer newCustomer){
        String customerID = newCustomer.getCustomerID();
        try{
            if ( customerHasAccount(customerID) ){
                throw new InvalidCustomerException("A duplicate customerID \"" + customerID + "\" was entered - customerID values should be unique!");
            }
            customerAccounts.put(customerID, newCustomer);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }

    // addSnack(String, Snack) takes a Snack object as an argument, adding it to this shop's stock if its ID
    //  is unique - if the snackID is duplicate an InvalidSnackException will be thrown.
    public void addSnack(Snack newSnack){
        String snackID = newSnack.getSnackID();
        try{
            if ( snackInStock(snackID) ){
                throw new InvalidSnackException("A duplicate snackID \"" + snackID + "\" was entered - snackID values should be unique!"); 
            }
            stockedSnacks.put(snackID, newSnack);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }
    }

    // getCustomer(String) takes a customerID as an argument and returns the Customer registered with this ID at this
    //  shop - if there is no Customer registered with this ID an InvalidCustomerException is thrown.
    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        if ( customerHasAccount(customerID) ){
            return customerAccounts.get(customerID);
        }
        throw new InvalidCustomerException("There is no customer account associated with customerID: \"" + customerID + "\"");   
    }

    // getSnack(String) takes a snackID as an argument and returns the Snack stocked by this shop - if there is no Snack in stock
    //  that is associated with this ID, an appropriate Exception is thrown.
    public Snack getSnack(String snackID) throws InvalidSnackException{
        if ( snackInStock(snackID) ){
            return stockedSnacks.get(snackID);
        }
        throw new InvalidSnackException("There is no snack in stock associated with snackID: \"" + snackID + "\"");
    }

    // processPurchase(String, String) takes a customerID and snackID as its arguments and charges the Customer registered with
    //  this customerID for the Snack in stock with this snackID - if either ID is not registered or the Customer cannot afford
    //  this Snack an appropriate Exception is thrown.
    public boolean processPurchase(String customerID, String snackID) throws InvalidCustomerException, InvalidSnackException, InsufficientBalanceException{
        Customer transactionCustomer =  getCustomer(customerID);
        Snack transactionSnack = getSnack(snackID);

        turnover = turnover + transactionCustomer.chargeAccount(transactionSnack.calculatePrice());
        return true;
    }

    // findLargestBasePrice() returns the largest basePrice value from the Snacks sold by this shop.
    public int findLargestBasePrice(){
        ArrayList<Integer> basePrices = getSnackBasePrices();
        basePrices.sort(null);
        return basePrices.get(basePrices.size()-1);
    }

    // countNegativeAccounts() counts and returns the number of registered Customers with a negative balance.
    public int countNegativeAccounts(){
        ArrayList<Integer> balances = getCustomerBalances();
        
        int count = 0;
        for (int i = 0; i < balances.size() ;i++){
            if (balances.get(i) < 0){
                count = count + 1;
            }
        }
        return count;
    }

    // calculateMedianCustomerBalance() calculates and returns the median balance of all registered Customers.
    public double calculateMedianCustomerBalance(){
        ArrayList<Integer> balances = getCustomerBalances();
        balances.sort(null);

        if (balances.size() % 2 == 0){
            return (balances.get(balances.size() / 2 - 1) + balances.get(balances.size() / 2)) / 2;
        }
        else{
            return (balances.get(balances.size() / 2 + 1));
        }
    }
        

// Support Methods
    // customerHasAccount(String) takes a customerID as an argument and returns true if there is already a Customer registered
    //  with this ID - otherwise this method returns false.
    private boolean customerHasAccount(String customerID){
        if ( customerAccounts.get(customerID) != null){
            return true;
        }
        return false; 
    }

    // snackInStock(String) takes a snackID as an argument and returns true if there is already a Snack sold by this shop associated
    //  with this ID - otherwise this method returns false.
    private boolean snackInStock(String snackID){
        if ( stockedSnacks.get(snackID) != null){
            return true;
        }
        return false;
    }
    
    // getSnackBasePrices() retrieves and returns an ArrayList of all basePrice values of Snacks sold by this shop.
    private ArrayList<Integer> getSnackBasePrices(){
        ArrayList<Integer> basePrices = new ArrayList<>();
        Snack[] snacks = stockedSnacks.values().toArray(new Snack[0]);

        for (Snack snack : snacks){
            basePrices.add(snack.getBasePrice());
        }
        return basePrices;
    }

    // getCustomerBalances() retrieves and returns an ArrayList of all balances of Customers registered with this shop.
    private ArrayList<Integer> getCustomerBalances(){
        ArrayList<Integer> balances = new ArrayList<>();
        Customer[] customers = customerAccounts.values().toArray(new Customer[0]);

        for (Customer customer : customers){
            balances.add(customer.getBalance());
        }
        return balances;
    }

// Test Harness
    public static void main(String args[]){

        System.out.println("Test 1\n");
        // The following code should create a new Shop object. It should then create a new Customer object and use the addCustomer method to test the loading
        //  of customer objects into the HashMap data structure.
        try{
            SnackShop testShop = new SnackShop("testShop");
            Customer testCustomer = new Customer("1A2B3C", "testCustomer");

            testShop.addCustomer(testCustomer);

            // We can now test the getCustomer method to ensure that it was successfully added into the HashMap.
            System.out.println(testShop.getCustomer(testCustomer.getCustomerID()));

            System.out.println("\nTest 2\n");
            // The following code should attempt to add a duplicate Customer object to the customerAccounts collection, this should throw an internal exception which
            // prevents the Customer from being added and informs the user that each Customer should have a unique customerID.
            testShop.addCustomer(testCustomer);

            System.out.println("\nTest 3\n");
            // The following code should attempt to retrieve a Customer object from the customerAccounts collection that does not exist, this should throw an InvalidCustomerException
            //  which will be caught by the try...catch block and thus print a line explaining the Customer does not exist within the collection.
            System.out.println(testShop.getCustomer("ABCDE6"));

        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }

        try{
            System.out.println("\nTest 4\n");
            // The following code should create a new SnackShop object. It should then create a new Food object and a new Drink object. These should then be loaded 
            //  into the stockedSnacks collection via the addSnack method.
            SnackShop testShop = new SnackShop("testShop");
            Food testFood = new Food("F/1234567", "testFood", 200, true);
            Drink testDrink = new Drink("D/1234567", "testDrink", 300, "high");

            testShop.addSnack(testFood);
            testShop.addSnack(testDrink);

            // We can now test the getSnack method to ensure the snacks were successfully loaded into the collection.
            System.out.println(testShop.getSnack(testFood.getSnackID()));
            System.out.println(testShop.getSnack(testDrink.getSnackID()));

            System.out.println("\nTest 5\n");
            // The following code should attempt to add a duplicate Snack object to the stockedSnacks collection, this should throw an internal Exception which will
            //  prevent the Snack from being added too stockedSnacks and inform the user that each Snack should be associated with a unique snackID.
            testShop.addSnack(testDrink);

            System.out.println("\nTest 6\n");
            // The following code should attempt to retreive a Snack object from the stockedSnacks collection that does not exist. This should throw an InvalidSnackException
            //  which will be caught by the try...catch block and thus print a line explaining the Snack does not exist within the collection.
            System.out.println(testShop.getSnack("111111"));
            
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        System.out.println("\nTest 7\n");
        // The following code should create a snackShop object then Food object and Customer object which will be loaded into the SnackShop. It will then run the
        //  processPurchase method using the two collections;
        try{
            SnackShop testShop = new SnackShop("testShop");
            Food testFood = new Food("F/1234567", "testFood", 250, true);
            StudentCustomer testCustomer = new StudentCustomer("ABCDE6", "testCustomer", 100);

            testShop.addCustomer(testCustomer);
            testShop.addSnack(testFood);

            // The following code will also demonstrate the functionality of the getTurnover method.
            System.out.println(testShop.processPurchase(testCustomer.getCustomerID(), testFood.getSnackID()));
            System.out.println(testShop.getTurnover());
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }
        catch (InsufficientBalanceException errString){
            System.err.println(errString);
        }

        System.out.println("\nTest 8\n");
        // The following code will create a new SnackShop, and then load 4 new snacks into the stockedSnacks collection with differing base prices. We will use
        //  this data to test the findLargestBasePrice method which should return 375 as this is the highest base price given.
        try{
            SnackShop testShop = new SnackShop("testShop");
            
            testShop.addSnack(new Food("F/1234567", "testFood1", 375, true));
            testShop.addSnack(new Food("F/1234568", "testFood2", 200, true));
            testShop.addSnack(new Drink("D/1234567", "testFood3", 325));
            testShop.addSnack(new Drink("D/1234568", "testFood4", 300, "high"));

            System.out.println(testShop.findLargestBasePrice());

            System.out.println("\nTest 9\n");
            // The following code will load 6 new Customer objects into the customerAccounts collection. It will then use this data (and data from the previous test)
            //  to take the two StudentCustomers into negative balance and test the countNegativeBalances method to verify it works.
            testShop.addCustomer(new Customer("A12345", "testCustomer1", 500));
            testShop.addCustomer(new Customer("A12345", "testCustomer2", 200));
            testShop.addCustomer(new Customer("A12345", "testCustomer3"));
            testShop.addCustomer(new StudentCustomer("A12345", "testCustomer4", 0));
            testShop.addCustomer(new StaffCustomer("A12345", "testCustomer5", 1500, "CMP"));
            testShop.addCustomer(new StudentCustomer("A12345", "testCustomer6", 0));

            testShop.processPurchase("A12348", "D/1234567");
            testShop.processPurchase("A12350", "F/1234567");

            System.out.println(testShop.countNegativeAccounts());

            System.out.println("\nTest 10\n");
            // The following lines will use the previously input data to test the calculateMedianCustomerBalance method, this should be 200
            System.out.println(testShop.calculateMedianCustomerBalance());

            System.out.println("\nTest 11\n");
            // The following lines will add an extra customer, making the total number of customers in customerAccount odd, this should change the median to
            //  the 4th value (250 in this case)
            testShop.addCustomer(new Customer("A123B4", "testCustomer 7", 250));

            System.out.println(testShop.calculateMedianCustomerBalance());



        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
        catch (InsufficientBalanceException errString){
            System.err.println(errString);
        }
        
        
    }

    
}
