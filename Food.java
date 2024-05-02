public class Food extends Snack {

    // Class Variable
    public final double surchagePercentage = 1.1;

    private boolean heated;

    public Food(String snackID, String name, int basePrice, boolean heated){
        super(snackID, name, basePrice);

        if (isFoodItem(snackID)){
            this.heated = heated;
        }
    }

// Accessor Methods
    public boolean getHeated(){
        return heated;
    }

// Service Methods
    public int calculatePrice(){
        int price = Math.round( (float)(basePrice * surchagePercentage) );
        return price;
    }

// Support Methods
    private boolean isFoodItem(String snackID){
        String firstChar = snackID.substring(0); 

        try{
            if (firstChar.equals("F")){
                return true;
            }
            else {
                throw new InvalidSnackException("Attempted to create Food Object where SnackID does not indicate a Food item!");
            }
        }
        catch (InvalidSnackException errorString) {
            System.err.println(errorString);
            return false;
        }
    }

}

    

