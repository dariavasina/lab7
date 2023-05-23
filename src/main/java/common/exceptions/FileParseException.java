package common.exceptions;

public class FileParseException extends Exception{
    public FileParseException(Throwable cause) {
        System.out.println("Error parsing JSON file: " + cause.getMessage());
    }
}
