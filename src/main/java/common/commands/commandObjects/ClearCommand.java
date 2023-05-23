package common.commands.commandObjects;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;
import databaseManagement.DataLoader;
import databaseManagement.DatabaseHandler;
import databaseManagement.DatabaseManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


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
            DataLoader dataLoader = new DataLoader(getDatabaseHandler());

            getCollection().clear();

            LinkedHashMap<Long, StudyGroup> newCollection = dataLoader.loadCollection();
            getCollection().setCollection(newCollection);

            Map<Long, String> elementsOwners = dataLoader.loadElementsOwners();
            getCollection().setElementsOwners(elementsOwners);
        } finally {
            getCollection().getReadLock().unlock();
        }
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse("Clear finished successfully");
    }
}
