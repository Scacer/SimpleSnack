/******************************************************************************************************************
 * public class InvalidCustomerException:
 *  The InvalidCustomerException class extends Exception, providing a custom exception type to be used
 *  appropriately.
 ******************************************************************************************************************/
public class InvalidCustomerException extends Exception {

    public InvalidCustomerException(String errorString){
        super(errorString);
    }
    
}
