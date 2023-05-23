package common.commands;

import common.collectionManagement.StudyGroupCollectionManager;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public abstract class CommandWithResponse extends Command{

    public CommandWithResponse(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public CommandWithResponse() {};

    public abstract CommandResponse getCommandResponse();
}
