import java.util.ArrayList;
import java.util.HashMap;

public class SnackShop{

// Attributes
    private String name;
    private int turnover;
    private HashMap<String, Customer> customerAccounts;
    private HashMap<String, Snack> stockedSnacks;

// Constructor
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
    public void addCustomer(String customerID, Customer newCustomer){
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

    public void addSnack(String snackID, Snack newSnack){
        try{
            if ( snackInStock(snackID) ){
                throw new InvalidCustomerException("A duplicate snackID \"" + snackID + "\" was entered - snackID values should be unique!"); 
            }
            stockedSnacks.put(snackID, newSnack);
        }
        catch (InvalidCustomerException errString){
            System.err.println(errString);
        }
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        if ( customerHasAccount(customerID) ){
            return customerAccounts.get(customerID);
        }
        throw new InvalidCustomerException("There is no customer account associated with customerID: \"" + customerID + "\"");   
    }

    public Snack getSnack(String snackID) throws InvalidSnackException{
        if ( snackInStock(snackID) ){
            return stockedSnacks.get(snackID);
        }
        throw new InvalidSnackException("There is no snack in stock associated with snackID: \"" + snackID + "\"");
    }

    public boolean processPurchase(String customerID, String snackID) throws InvalidCustomerException, InvalidSnackException, InsufficientBalanceException{
        Customer transactionCustomer =  getCustomer(customerID);
        Snack transactionSnack = getSnack(snackID);

        turnover = turnover + transactionCustomer.chargeAccount(transactionSnack.calculatePrice());
        return true;
    }

    public int findLargestBasePrice(){
        ArrayList<Integer> basePrices = getSnackBasePrices();
        basePrices.sort(null);
        return basePrices.get(basePrices.size()-1);
    }

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
    private boolean customerHasAccount(String customerID){
        if ( customerAccounts.get(customerID) != null){
            return true;
        }
        return false; 
    }

    private boolean snackInStock(String snackID){
        if ( stockedSnacks.get(snackID) != null){
            return true;
        }
        return false;
    }
    
    private ArrayList<Integer> getSnackBasePrices(){
        ArrayList<Integer> basePrices = new ArrayList<>();
        Snack[] snacks = stockedSnacks.values().toArray(new Snack[0]);

        for (Snack snack : snacks){
            basePrices.add(snack.getBasePrice());
        }
        return basePrices;
    }

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

            testShop.addCustomer(testCustomer.getCustomerID(), testCustomer);

            // We can now test the getCustomer method to ensure that it was successfully added into the HashMap.
            System.out.println(testShop.getCustomer(testCustomer.getCustomerID()));

            System.out.println("\nTest 2\n");
            // The following code should attempt to add a duplicate Customer object to the customerAccounts collection, this should throw an internal exception which
            // prevents the Customer from being added and informs the user that each Customer should have a unique customerID.
            testShop.addCustomer(testCustomer.getCustomerID(), testCustomer);

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

            testShop.addSnack(testFood.getSnackID(), testFood);
            testShop.addSnack(testDrink.getSnackID(), testDrink);

            // We can now test the getSnack method to ensure the snacks were successfully loaded into the collection.
            System.out.println(testShop.getSnack(testFood.getSnackID()));
            System.out.println(testShop.getSnack(testDrink.getSnackID()));

            System.out.println("\nTest 5\n");
            // The following code should attempt to add a duplicate Snack object to the stockedSnacks collection, this should throw an internal Exception which will
            //  prevent the Snack from being added too stockedSnacks and inform the user that each Snack should be associated with a unique snackID.
            testShop.addSnack(testDrink.getSnackID(), testDrink);

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

            testShop.addCustomer(testCustomer.getCustomerID(), testCustomer);
            testShop.addSnack(testFood.getSnackID(), testFood);

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
            
            testShop.addSnack("F/1234567", new Food("F/1234567", "testFood1", 375, true));
            testShop.addSnack("F/1234568", new Food("F/1234568", "testFood2", 200, true));
            testShop.addSnack("D/1234567", new Drink("D/1234567", "testFood3", 325));
            testShop.addSnack("D/1234568", new Drink("D/1234568", "testFood4", 300, "high"));

            System.out.println(testShop.findLargestBasePrice());

            System.out.println("\nTest 9\n");
            // The following code will load 6 new Customer objects into the customerAccounts collection. It will then use this data (and data from the previous test)
            //  to take the two StudentCustomers into negative balance and test the countNegativeBalances method to verify it works.
            testShop.addCustomer("A12345", new Customer("A12345", "testCustomer1", 500));
            testShop.addCustomer("A12346", new Customer("A12345", "testCustomer2", 200));
            testShop.addCustomer("A12347", new Customer("A12345", "testCustomer3"));
            testShop.addCustomer("A12348", new StudentCustomer("A12345", "testCustomer4", 0));
            testShop.addCustomer("A12349", new StaffCustomer("A12345", "testCustomer5", 1500, "CMP"));
            testShop.addCustomer("A12350", new StudentCustomer("A12345", "testCustomer6", 0));

            testShop.processPurchase("A12348", "D/1234567");
            testShop.processPurchase("A12350", "F/1234567");

            System.out.println(testShop.countNegativeAccounts());

            System.out.println("\nTest 10\n");
            // The following lines will use the previously input data to test the calculateMedianCustomerBalance method, this should be 200
            System.out.println(testShop.calculateMedianCustomerBalance());

            System.out.println("\nTest 11\n");
            // The following lines will add an extra customer, making the total number of customers in customerAccount odd, this should change the median to
            //  the 4th value (250 in this case)
            testShop.addCustomer("A123B4", new Customer("A123B4", "testCustomer 7", 250));

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
