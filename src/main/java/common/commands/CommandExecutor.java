package common.commands;

import common.collectionManagement.StudyGroupCollectionManager;
import common.networkStructures.CommandResponse;

public class CommandExecutor {
    private StudyGroupCollectionManager collection;
    private CommandResponse response;

    public CommandExecutor() {};

    public CommandExecutor(StudyGroupCollectionManager collection) {
        this.collection = collection;
    };


    public void execute(CommandWithResponse command) throws Exception {
        //command.setCollection(collection);
        command.execute();

        CommandWithResponse commandWithResponse = command;
        response = commandWithResponse.getCommandResponse();
    }

    public void setCollection(StudyGroupCollectionManager collection) {
        this.collection = collection;
    }

    public CommandResponse getCommandResponse() {
        return response;
    }
}
