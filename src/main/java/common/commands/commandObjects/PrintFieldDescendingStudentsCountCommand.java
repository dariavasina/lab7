package common.commands.commandObjects;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public class PrintFieldDescendingStudentsCountCommand extends CommandWithResponse {
    StringBuilder output = new StringBuilder();
    public PrintFieldDescendingStudentsCountCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public PrintFieldDescendingStudentsCountCommand() {
    }

    @Override
    public void execute() {
        try {
            getCollection().getReadLock().lock();
            output = getCollection().printFieldDescendingStudentsCount();
        } finally {
            getCollection().getReadLock().unlock();
        }
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse(output.toString());
    }
}
