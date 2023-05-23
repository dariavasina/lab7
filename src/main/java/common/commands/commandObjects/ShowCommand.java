package common.commands.commandObjects;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.exceptions.EmptyCollectionException;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

import java.util.Map;

public class ShowCommand extends CommandWithResponse {
    private StringBuilder output;
    public ShowCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public ShowCommand() {
    }

    @Override
    public void execute() throws EmptyCollectionException {
        try {
            getCollection().getReadLock().lock();
            output = getCollection().show();
        } catch (EmptyCollectionException e) {
            output = new StringBuilder(e.getMessage());
        } finally {
            getCollection().getReadLock().unlock();
        }
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse(output.toString());
    }
}
