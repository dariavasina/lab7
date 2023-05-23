package common.exceptions;

public class KeyDoesNotExistException extends Exception{
    public KeyDoesNotExistException(String key) {
        super("Key " + key + " does not exist, please try again");
    }
}
