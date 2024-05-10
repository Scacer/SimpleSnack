import java.io.*;

public class Simulation {
    
// Attributes
    

// Main Method
    public static void main(String args[]){
        File customerFile = new File("customers.txt");
        File snackFile = new File("snacks.txt");
        File transactionFile = new File("transactions.txt");

        SnackShop newShop = initialiseShop("The New Shop", snackFile, customerFile);
        simulateShopping(newShop, transactionFile);

        System.out.println(newShop.getTurnover());
        System.out.println(newShop.countNegativeAccounts());


    }

// Service Methods

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
                        newSnackShop.addSnack(snackID, new Food(snackID, name, basePrice, heated));
                    }
                    catch (InvalidSnackException errString){
                        System.err.println(errString);
                    }
                }
                else{
                    String sugarContent = special;
                    try{
                        newSnackShop.addSnack(snackID, new Drink(snackID, name, basePrice, sugarContent));
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
                            newSnackShop.addCustomer(customerID, new StudentCustomer(customerID, name, balance));
                        }
                        else if (customerType.equals("STAFF") && customerInfo.length < 5){
                            newSnackShop.addCustomer(customerID, new StaffCustomer(customerID, name, balance, "OTHER"));
                        }
                        else{
                            String school = customerInfo[4];
                            newSnackShop.addCustomer(customerID, new StaffCustomer(customerID, name, balance, school));
                        }
                
                    }
                    else{
                        newSnackShop.addCustomer(customerID, new Customer(customerID, name, balance));
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

    public static void simulateShopping(SnackShop shop, File transactionFile){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null){
                String[] transactionInfo = currentLine.split(",");

               if (transactionInfo[0].equals("NEW_CUSTOMER")){
                    try{
                        String customerID = transactionInfo[1];
                        String name = transactionInfo[2];
                        int balance = Integer.parseInt(transactionInfo[transactionInfo.length-1]);

                        if (transactionInfo.length > 4){
                            String customerType = transactionInfo[3];

                            if (customerType.equals("STUDENT")){
                                shop.addCustomer(customerID, new StudentCustomer(customerID, name, balance));
                            }
                            else if (customerType.equals("STAFF") && transactionInfo.length < 6){
                                shop.addCustomer(customerID, new StaffCustomer(customerID, name, balance, "OTHER"));
                            }
                            else{
                                String school = transactionInfo[4];
                                shop.addCustomer(customerID, new StaffCustomer(customerID, name, balance, school));
                            }

                        }
                        else{
                            shop.addCustomer(customerID, new Customer(customerID, name, balance));
                        }

                        System.out.println("Account successfully registered for customer with ID \"" + customerID + "\"");
                    }
                    catch (InvalidCustomerException errString){
                        System.out.println(errString);
                    }

                }
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



    }
}
