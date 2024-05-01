import java.util.regex.Pattern;

public class Snack{

    public static void main(String[] args){
        Snack newSnack = new Snack("F/1234567", "TestSnack", 200);
        System.out.println(newSnack.toString());
    }
    
    private String snackID, name;
    private int basePrice;

    public Snack(String snackID, String name, int basePrice){
        if (isValidSnackID(snackID) && isValidBasePrice(basePrice)){
            this.snackID = snackID;
            this.name = name;
            this.basePrice = basePrice;
        }
    }

    // Accessor Methods
    public String toString(){
        return ("SnackID: " + snackID + "\n Name: " + name + "\n Base Price: " + basePrice);
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


    // Support Methods

    //
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
    
}