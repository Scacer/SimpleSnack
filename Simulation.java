/********************************************************************************************************************
* SimpleSnack (Programming 1 - Summative Assessment 2)
*   Author - Sunny(Sonney) Ledger
*   Date: 10/05/2024
*   Description: SimpleSnack is a programming project dedicated to creating a prototype system for the simulation of
                    snack shops run by MakeMeSomeMoolah Inc. The primary function of this project is to simulate
                    transactions made by customers purchasing snacks, keeping track of the turnover for the shop.
                    However other functions such as applying discounts to snack prices based on type of customer
                    or snack and finding the median of all customer balances should be implemented where relevant.
********************************************************************************************************************/

/*******************************************************************************************************************
 * public class Simulation:
 *  The simulation class' function is to read through files containing data pertaining to the snack shop simulation and
 *  to then input this data into objects defined by classes written to model the key objects in the SimpleSnack system.
 *  The second (and only other) function of this class - after having set up the shop environment - is to simulate the
 *  operation of a snack shop by reading through another file which contains a series of transactions to be made.
*******************************************************************************************************************/
import java.io.*;

public class Simulation {

// Main Method
    public static void main(String args[]){
        File customerFile = new File("customers.txt");
        File snackFile = new File("snacks.txt");
        File transactionFile = new File("transactions.txt");

        SnackShop newShop = initialiseShop("The New Shop", snackFile, customerFile);
        simulateShopping(newShop, transactionFile);
    }

// Service Methods
    // The initialiseShop method reads through each line in provided files which should contain information about
    //  both Snacks and Customers. This method then loads the data into appropriate objects and adds them to
    //  collections held within a newly created SnackShop object which is then returned.
    public static SnackShop initialiseShop(String shopName, File snackFile, File customerFile){
        SnackShop newSnackShop = new SnackShop(shopName);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(snackFile));
            String currentLine;

            // This while loop reads through the snacks file and distinguishes between food and drink snacks, then adds them to the SnackShop 
            while ((currentLine = reader.readLine()) != null){
                String[] snackInfo = currentLine.split("@");

                String snackID = snackInfo[0];
                String name = snackInfo[1];
                String special = snackInfo[2];
                int basePrice = Integer.parseInt(snackInfo[3]);

                if (snackID.charAt(0) == 'F'){
                    boolean heated = false;
                    if (special.equals("hot")){
                        heated = true;
                    }
                    try{
                        newSnackShop.addSnack(new Food(snackID, name, basePrice, heated));
                    }
                    catch (InvalidSnackException errString){
                        System.err.println(errString);
                    }
                }
                else{
                    String sugarContent = special;
                    try{
                        newSnackShop.addSnack(new Drink(snackID, name, basePrice, sugarContent));
                    }
                    catch (InvalidSnackException errString){
                        System.err.println(errString);
                    }
                }
            }
            reader.close();
            
            reader = new BufferedReader(new FileReader(customerFile));
            // This while loop reads through the customers file, distinguishes between customer types and then adds them to the SnackShop
            while ((currentLine = reader.readLine()) != null){
                String[] customerInfo = currentLine.split("#");

                String customerID = customerInfo[0];
                String name = customerInfo[1];
                int balance = Integer.parseInt(customerInfo[2]);

                try{
                    if (customerInfo.length > 3){
                        String customerType = customerInfo[3];

                        if (customerType.equals("STUDENT")){
                            newSnackShop.addCustomer(new StudentCustomer(customerID, name, balance));
                        }
                        else if (customerType.equals("STAFF") && customerInfo.length < 5){
                            newSnackShop.addCustomer(new StaffCustomer(customerID, name, balance, "OTHER"));
                        }
                        else{
                            String school = customerInfo[4];
                            newSnackShop.addCustomer(new StaffCustomer(customerID, name, balance, school));
                        }
                
                    }
                    else{
                        newSnackShop.addCustomer(new Customer(customerID, name, balance));
                    }
                }
                catch (InvalidCustomerException errString){
                    System.err.println(errString);
                }
            }
            
            reader.close();

        }
        catch (IOException errString){
            System.err.println(errString);
        }

        return newSnackShop;
    }

    // The simulateShopping method takes both a SnackShop and File argument, this file should be a series of transactions which the
    //  method will then read through, identifying which actions to take and apply to the SnackShop object and printing out a summary
    //  line if successful and an appropriate Exception if not. Additionally this method calls the analytical functions to give a brief
    //  look at the state of the SnackShop object at the end of all transactions.
    public static void simulateShopping(SnackShop shop, File transactionFile){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
            String currentLine;

            // This while loop cycles through the provided file, providing each line as a String for manipulation
            while ((currentLine = reader.readLine()) != null){
                String[] transactionInfo = currentLine.split(",");

                // This block of code handles the NEW_CUSTOMER transactions
               if (transactionInfo[0].equals("NEW_CUSTOMER")){
                    try{
                        String customerID = transactionInfo[1];
                        String name = transactionInfo[2];
                        int balance = Integer.parseInt(transactionInfo[transactionInfo.length-1]);

                        if (transactionInfo.length > 4){
                            String customerType = transactionInfo[3];

                            if (customerType.equals("STUDENT")){
                                shop.addCustomer(new StudentCustomer(customerID, name, balance));
                            }
                            else if (customerType.equals("STAFF") && transactionInfo.length < 6){
                                shop.addCustomer(new StaffCustomer(customerID, name, balance, "OTHER"));
                            }
                            else{
                                String school = transactionInfo[4];
                                shop.addCustomer(new StaffCustomer(customerID, name, balance, school));
                            }

                        }
                        else{
                            shop.addCustomer(new Customer(customerID, name, balance));
                        }

                        System.out.println("Account successfully registered for customer with ID \"" + customerID + "\"");
                    }
                    catch (InvalidCustomerException errString){
                        System.out.println(errString);
                    }

                }
                // This block of code handles the PURCHASE transactions
                else if (transactionInfo[0].equals("PURCHASE")){
                    String customerID = transactionInfo[1];
                    String snackID = transactionInfo[2];

                    try{
                        shop.processPurchase(customerID, snackID);
                        System.out.println("Customer with ID \"" + customerID + "\" was successfully charged for Snack with ID \"" + snackID + "\"");
                    }
                    catch (InsufficientBalanceException errString){
                        System.err.println(errString);
                    }
                    catch (InvalidCustomerException errString){
                        System.err.println(errString);
                    }
                    catch (InvalidSnackException errString){
                        System.err.println(errString);
                    }
                }
                // This block of code handles the ADD_FUNDS transactions.
                else if (transactionInfo[0].equals("ADD_FUNDS")){
                    String customerID = transactionInfo[1];
                    int amount = Integer.parseInt(transactionInfo[2]);
                    
                    try{
                        shop.getCustomer(customerID).addFunds(amount);
                        System.out.println("Successfully added " + amount + " funds to Customer with ID \"" + customerID + "\"");
                    }
                    catch (InvalidCustomerException errString){
                        System.err.println(errString);
                    }
                }
            }

            reader.close();
        }
        catch (IOException errString){
            System.err.println(errString);
        }
        System.out.println("\nLargest basePrice of a stocked snack: " + shop.findLargestBasePrice());
        System.out.println("Total number of Customers with negative balance: " + shop.countNegativeAccounts());
        System.out.println("Median Customer Balance: " + shop.calculateMedianCustomerBalance());
        System.out.println("Total Shop Turnover: " + shop.getTurnover());



    }
}
