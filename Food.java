public class Food extends Snack {

    // Class Variable
    public final double surchagePercentage = 1.1;

    private boolean heated;

// Constructor
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
    public int calculatePrice(){
        int price = Math.round( (float)(basePrice * surchagePercentage) );
        return price;
    }

// Support Methods
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

    

