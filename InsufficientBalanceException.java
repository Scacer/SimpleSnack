/******************************************************************************************************************
 * public class InsufficientBalanceException:
 *  The InsufficientBalanceException class extends Exception, providing a custom exception type to be used
 *  appropriately.
 ******************************************************************************************************************/

public class InsufficientBalanceException extends Exception{
    
    public InsufficientBalanceException(String errorString){
        super(errorString);
    }
}
