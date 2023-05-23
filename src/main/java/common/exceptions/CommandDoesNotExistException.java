package common.exceptions;

public class CommandDoesNotExistException extends Exception{
    public CommandDoesNotExistException(String command) {
        super("Command " + command + " does not exist, please enter a valid command name\n");
    }
}
