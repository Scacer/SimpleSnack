import java.util.regex.Pattern;

abstract class Snack{
 
    protected String snackID, name;
    protected int basePrice;

    public Snack(String snackID, String name, int basePrice){
        if (isValidSnackID(snackID) && isValidBasePrice(basePrice)){
            this.snackID = snackID;
            this.name = name;
            this.basePrice = basePrice;
        }
    }

// Accessor Methods
    public String toString(){
        return ("SnackID: " + snackID + "\n Name: " + name + "\n Base Price: " + getPriceString(basePrice));
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
    public abstract int calculatePrice();

// Support Methods
    private boolean isValidSnackID(String snackID){
        try{
            if (Pattern.matches("[FD][/][0-9]{7}", snackID)) {
                return true;
            }
            else {
                throw new InvalidSnackException("Invalid snackID!");
            }
        }
        catch (InvalidSnackException errorString) {
            System.err.println(errorString);
            return false;
        }
            
    }

    private boolean isValidBasePrice(int basePrice){
        try {
            if (basePrice > -1){
                return true;
            }
            else throw new InvalidSnackException("Invalid basePrice!");
        }
        catch (InvalidSnackException errorString){
            System.err.println(errorString);
            return false;
        }
    }

    private String getPriceString(int price){
        float decimalPrice = price / 100;
        return ("£" + decimalPrice);
    }
    
}