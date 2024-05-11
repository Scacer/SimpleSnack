/******************************************************************************************************************
 * public class Food:
 *  The Food class is a sub-class of the abstract Snack class which provides additonal functionality for specifying
 *  (and charging appropriately for) heated Food snacks.
 *******************************************************************************************************************/
public class Food extends Snack {

// Class Variables
    public final double surchagePercentage = 1.1;

// Attributes
    private boolean heated;

// Constructor
    // This constructor takes arguments for Food attributes - snackID, name, basePrice and heated. Heated's value determines
    //  if the Food snack is subject to the surchage percentage.
    public Food(String snackID, String name, int basePrice, boolean heated) throws InvalidSnackException{
        super(snackID, name, basePrice);

        if (isFoodItem(snackID)){
            this.heated = heated;
        }
    }

// Accessor Methods
    public String toString(){
        return super.toString() + ", heated: " + heated + "}";
    }

    public boolean getHeated(){
        return heated;
    }

// Service Methods
    // calculatePrice() is required by the superclass Snack - returns the surchage percentage if the Food is heated or simply the
    //  basePrice if not.
    public int calculatePrice(){
        if (heated == true){
            return (int)Math.ceil( (float)(basePrice * surchagePercentage) );
        }
        return basePrice;
    }

// Support Methods
    // isFoodItem(String) verifies if a provided snackID is a Food snack according to the provided snackID format.
    private boolean isFoodItem(String snackID) throws InvalidSnackException{
        String firstChar = snackID.substring(0, 1);

        if (firstChar.equals("F")){
            return true;
        }
        else {
            throw new InvalidSnackException("Attempted to create Food Object where SnackID does not indicate a Food item!");
        }
    }

// Test Harness
    public static void main(String[] args) {
        // The following try block should execute without issue as all attribute inputs are expected.
        try{
            Food testFood = new Food("F/1234567", "TestFood", 200, true);

            // The following line should test the toString method specified in the Snack superclass and print information about the testFood Object.
            System.out.println(testFood);

            // The following line should output the calculated price of the testFood item.
            // A value of 220 is expected as the basePrice was specified as 200, and the specified surchage percentage was 10%.
            System.out.println(testFood.calculatePrice());
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        // The following try block should throw an InvalidSnackException and print a string specifying the ID does not indicate a food item.
        try{
            Food invalidFood = new Food("D/1234567", "InvalidFood", 500, false);
            System.out.println(invalidFood);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        // The following try block should throw an InvalidSnackException as the entered SnackID does not match the format specified in the superclass "Snack".
        try{
            Food invalidSnackIDFood = new Food("F/123456", "invalidSnackIDFood", 300, false);
            System.out.println(invalidSnackIDFood);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }

        // The following try block should throw an InvalidSnackException as the entered basePrice is less than 0.
        try{
            Food invalidBasePriceFood = new Food("F/1234567", "invalidBasePriceFood", -1, true);
            System.out.println(invalidBasePriceFood);
        }
        catch (InvalidSnackException errString){
            System.err.println(errString);
        }
    }

}

    

