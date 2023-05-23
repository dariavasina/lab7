package common.exceptions;

public class KeyAlreadyExistsException extends Exception{
    public KeyAlreadyExistsException(String key) {
        System.out.print("Key " + key + " already exists, please try again\n");
    }
}
