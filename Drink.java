/******************************************************************************************************************
 * public class Drink:
 *  The Drink class is a sub-class of the abstract Snack class which provides additional functionality for specifying 
 *  (and charging appropriately for) the sugar content of a Drink snack.
 *******************************************************************************************************************/
public class Drink extends Snack {

// Enumerated Type for SugarContent
    // SugarContents enum is used to ensure that the sugarContent attribute value lies within the accepted range of values
    private enum SugarContents{HIGH, LOW, NONE}

// Attributes
    private SugarContents sugarContent;

// Constructors

    // This constructor takes arguments for all attributes except for sugarContent (snackID, name, basePrice). sugarContent defaults to NONE.
    public Drink(String snackID, String name, int basePrice) throws InvalidSnackException{
        super(snackID, name, basePrice);

        if (isDrinkItem(snackID)){
            sugarContent = SugarContents.NONE;
        }
    }

    // This constructor takes arguments for all attributes - snackID, name, basePrice, sugarContent. sugarContent's value determines the amount
    //  of sugar tax added to the price;
    public Drink(String snackID, String name, int basePrice, String sugarContent) throws InvalidSnackException{
        super(snackID, name, basePrice);

        if (isDrinkItem(snackID)){
            validateSugarContent(sugarContent);
        }
    }

// Accessor Methods
    public String toString(){
        return super.toString() + ", sugarContent: " + sugarContent + "}";
    }

    public String getSugarContent(){
        return sugarContent.toString();
    }

// Service Methods
    // calculatePrice() is required by the Snack superclass - returns the basePrice + the amount of sugar tax appropriate for the
    //  value of sugarContent.
    public int calculatePrice(){
        if (sugarContent == SugarContents.HIGH){
            return basePrice + 24;
        }
        else if (sugarContent == SugarContents.LOW){
            return basePrice + 18;
        }
        else {
            return basePrice;
        }
    }

// Support Methods
    // isDrinkItem(String) verifies if a provided snackID is a Drink snack according to the provided snackID format.
    private boolean isDrinkItem(String snackID) throws InvalidSnackException{
        String firstChar = snackID.substring(0, 1);

        if (firstChar.equals("D")){
            return true;
        }
        else {
            throw new InvalidSnackException("Attempted to create Drink Object where SnackID does not indicate a Drink item!");
        }
    }      

    // validateSugarContent(String) takes the String input for sugarContent from the constructor and converts it into a value within the
    //  SugarContents enum.
    private void validateSugarContent(String sugarString) throws InvalidSnackException{
        try{
            sugarContent = SugarContents.valueOf(sugarString.toUpperCase());
        }
        catch (IllegalArgumentException errString){
            throw new InvalidSnackException("Invalid sugarContent value - sugarContent should be entered as high, low, or none!");
        }
    }

// Test Harness
    public static void main(String[] args) {
        // The following try block creates a valid Drink object using the first constructor, which sets the sugarContent automatically to none.
        try{
            Drink testDrink = new Drink("D/1234567", "testDrink", 300);
            System.out.println(testDrink);

            // The sugarContent is automatically set to none, thus the calculatePrice method should return simply the basePrice of 300.
            System.out.println(testDrink.calculatePrice());
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        // The following try block creates a valid Drink object using the second constructor, which allows us to set a sugarContent value.
        try{
            Drink testDrink2 = new Drink("D/1234568", "testDrink2", 500, "low");
            Drink testDrink3 = new Drink("D/1234569", "testDrink3", 500, "high");

            // testDrink2 had sugarContent as low, thus the calculatePrice method should return the basePrice with the added 18p tax.
            System.out.println(testDrink2.calculatePrice());
            
            // testDrink2 had sugarContent as high, thus the calculatePrice method should return the basePrice with the added 24p tax.
            System.out.println(testDrink3.calculatePrice());
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        // The following try block attempts to create a Drink object with an invalid snackID, this should throw an InvalidSnackException.
        try{
            Drink invalidDrinkID = new Drink("F/1234567", "invalidDrinkID", 100);
            System.out.println(invalidDrinkID);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        // The following try block attempts to create a Drink object with an invalid sugarContent value, this should throw an InvalidSnackException.
        try{
            Drink invalidSugarContentDrink = new Drink("D/1234570", "invalidSUgarContentDrink", 200, "lots of sugar");
            System.out.println(invalidSugarContentDrink);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }
    }

}
