/******************************************************************************************************************
 * public class InvalidSnackException:
 *  The InvalidSnackException class extends Exception, providing a custom exception type to be used appropriately.
 ******************************************************************************************************************/
public class InvalidSnackException extends Exception {

    public InvalidSnackException(String errorString){
        super(errorString);
    }
    
}
