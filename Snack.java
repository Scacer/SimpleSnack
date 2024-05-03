import java.util.regex.Pattern;

abstract class Snack{
 
    protected String snackID, name;
    protected int basePrice;

    public Snack (String snackID, String name, int basePrice) throws InvalidSnackException{
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
    private boolean isValidSnackID(String snackID) throws InvalidSnackException{
        if (Pattern.matches("[FD][/][0-9]{7}", snackID)) {
            return true;
        }
        else {
            throw new InvalidSnackException("Invalid snackID!");
        }
    }

    private boolean isValidBasePrice(int basePrice) throws InvalidSnackException{
        if (basePrice > -1){
            return true;
        }
        else{
            throw new InvalidSnackException("Invalid basePrice!");
        }
    }

    private String getPriceString(int price){
        float decimalPrice = price / 100;
        return String.format("£ %,.2f", decimalPrice);
    }
    
}