public class InsufficientBalanceException extends Exception{

    public InsufficientBalanceException(String errorString){
        super(errorString);
    }
}
