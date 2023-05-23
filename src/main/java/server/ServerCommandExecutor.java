package server;

import common.collectionManagement.StudyGroupCollectionManager;
import common.dataStructures.Pair;
import common.exceptions.CommandDoesNotExistException;
import common.exceptions.InvalidArgumentsException;

import common.io.consoleIO.ConfirmationReader;

import java.io.IOException;

public class ServerCommandExecutor {

    private StudyGroupCollectionManager collectionManager;
    private final ConfirmationReader confirmationReader;
    String clientsDataPath;
    {
        confirmationReader = new ConfirmationReader();
    }


    public ServerCommandExecutor(StudyGroupCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setCollectionManager(StudyGroupCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setClientsDataPath(String path) {
        this.clientsDataPath = path;
    }


    public void execute(Pair<String, String[]> command) throws InvalidArgumentsException, CommandDoesNotExistException, IOException {

        String commandName = command.getFirst();
        String[] commandArgs = command.getSecond();

        if (commandName.equals("exit")) {

            if (commandArgs.length != 0) {
                throw new InvalidArgumentsException("Command exit doesn't take any arguments!\n");
            }

            boolean confirmation = confirmationReader.readConfirmationFromConsole();
            if (confirmation) {
                System.out.println("Bye!");
                System.exit(1);
            }
        } else if (commandName.equals("save")) {

            if (commandArgs.length == 0) {
                collectionManager.save(clientsDataPath);
            } else if (commandArgs.length == 1) {
                collectionManager.save(commandArgs[0]);
            }
            else {
                throw new InvalidArgumentsException("Command save takes 0 or 1 arguments!\n");
            }

            System.out.println("Done!");
        }
        else {
            throw new CommandDoesNotExistException(commandName);
        }
    }
}