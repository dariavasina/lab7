package common.exceptions;

public class IdDoesNotExistException extends Exception{
    public IdDoesNotExistException(String id) {
        super("ID " + id + " does not exist, please try again");
    }
}
