package common.commands.commandObjects;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;
import databaseManagement.DatabaseHandler;
import databaseManagement.DatabaseManager;

import java.sql.SQLException;


public class ClearCommand extends CommandWithResponse {
    public ClearCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public ClearCommand() {
    }

    @Override
    public void execute() throws SQLException {
        try {
            getCollection().getReadLock().lock();
            DatabaseManager databaseManager = getDatabaseManager();
            databaseManager.removeAll(getUsername());
            getCollection().clear();
        } finally {
            getCollection().getReadLock().unlock();
        }


    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse("The collection is now empty");
    }
}
