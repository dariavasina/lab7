package common.exceptions;

public class EmptyCollectionException extends Exception {
    public EmptyCollectionException() {
        super("The collection is empty");
    }
}
