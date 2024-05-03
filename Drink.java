public class Drink extends Snack {

    // Test Harness
    public static void main(String[] args) {
        
    }

// Enumerated Type for SugarContent
    private enum SugarContents{HIGH, LOW, NONE}

// Attributes
    private SugarContents sugarContent;

// Constructors

    // Default Constructor - Takes all arguments except for sugarContent, in this case sugarContent defaults to NONE.
    public Drink(String SnackID, String name, int basePrice) throws InvalidSnackException{
        super(SnackID, name, basePrice);

        sugarContent = SugarContents.NONE;
    }

    // Full Constructor - Takes all arguments including sugarContent.
    public Drink(String SnackID, String name, int basePrice, String sugarContent) throws InvalidSnackException{
        super(SnackID, name, basePrice);

    }

// Accessor Methods

// Service Methods
    public int calculatePrice(){
        return 23;
    }

// Support Methods

}
