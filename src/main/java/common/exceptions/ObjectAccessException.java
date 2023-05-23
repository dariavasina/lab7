package common.exceptions;

public class ObjectAccessException extends Exception{
    public ObjectAccessException() {
        super("You don't have access to this object");
    }

    public ObjectAccessException(String message) {
        super(message);
    }
}
