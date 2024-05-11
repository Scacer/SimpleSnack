/*******************************************************************************************************************
 * abstract class Snack:
 *  The Snack class serves as a base class for storing information and basic functionality about and for generic
 *  snacks ready for specialisation (extension) into snack sub-categories.
 *******************************************************************************************************************/
import java.util.regex.Pattern;

abstract class Snack{
 
    protected String snackID, name;
    protected int basePrice;

// Constructor
    // This Constructor takes arguments for all Snack attributes - snackID, name, and basePrice. basePrice determines
    // the Snack's price in pennies.
    public Snack (String snackID, String name, int basePrice) throws InvalidSnackException{
        if (isValidSnackID(snackID) && isValidBasePrice(basePrice)){
            this.snackID = snackID;
            this.name = name;
            this.basePrice = basePrice;
        }
    }

// Accessor Methods
    public String toString(){
        return ("Snack{snackID: " + snackID + ", name: " + name + ", basePrice: " + basePrice);
    }

    public String getSnackID(){
        return snackID;
    }

    public String getName(){
        return name;
    }

    public int getBasePrice(){
        return basePrice;
    }


// Service Methods
    // calculatePrice() is an abstract method, specifying that it is a requirement for all sub-classes.
    public abstract int calculatePrice();

// Support Methods
    // isValidSnackID(String) verifies whether the format of a provided String matches the snackID format.
    private boolean isValidSnackID(String snackID) throws InvalidSnackException{
        if (Pattern.matches("[FD][/][0-9]{7}", snackID)) {
            return true;
        }
        else {
            throw new InvalidSnackException("Invalid snackID!");
        }
    }

    // isValidBasePrice(int) verifies whether a provided integer is within the expected range for base prices.
    private boolean isValidBasePrice(int basePrice) throws InvalidSnackException{
        if (basePrice > -1){
            return true;
        }
        else{
            throw new InvalidSnackException("Invalid basePrice!");
        }
    }
    
}