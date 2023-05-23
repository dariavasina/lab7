package common.commands.commandObjects;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.exceptions.InvalidArgumentsException;
import common.networkStructures.CommandResponse;

import java.sql.SQLException;
import java.util.Map;

public class InsertCommand extends CommandWithResponse {
    public InsertCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public InsertCommand() {
    }

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        try {
            Long key = Long.parseLong(args[0]);
            super.setArgs(new String[]{String.valueOf(key)});
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please try to enter a command again");
        }
    }

    @Override
    public void execute() throws SQLException {
        Long key = Long.parseLong(getArgs()[0]);
        StudyGroup studyGroup = getStudyGroup();

        try {
            getCollection().getWriteLock().lock();
            getDatabaseManager().insertStudyGroupWithoutId(studyGroup, getUsername(), key);

            Long id = getDatabaseManager().getStudyGroupIdByCollectionKey(key);
            studyGroup.setId(id);
            getCollection().insert(key, studyGroup);

            Map<Long, String> elementsOwners = getCollection().getElementsOwners();
            elementsOwners.put(studyGroup.getId(), getUsername());
            getCollection().setElementsOwners(elementsOwners);
        } finally {
            getCollection().getWriteLock().unlock();
        }

    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse("insert finished successfully");
    }
}
