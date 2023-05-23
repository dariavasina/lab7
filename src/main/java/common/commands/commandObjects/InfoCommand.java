package common.commands.commandObjects;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public class InfoCommand extends CommandWithResponse {
    public InfoCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }
    private StringBuilder output;

    public InfoCommand() {
    }

    @Override
    public void execute() {
        try {
            getCollection().getReadLock().lock();
            output = getCollection().info();
        } finally {
            getCollection().getReadLock().unlock();
        }
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse(output.toString());
    }
}
