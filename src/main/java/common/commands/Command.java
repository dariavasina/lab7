package common.commands;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.exceptions.InvalidArgumentsException;
import databaseManagement.DatabaseHandler;
import databaseManagement.DatabaseManager;

import java.io.Serializable;
import java.util.Map;

public abstract class Command implements Serializable {
    private DatabaseHandler databaseHandler;
    private DatabaseManager databaseManager;
    private String username;
    private StudyGroupCollectionManager collection;

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StudyGroupCollectionManager getCollection() {
        return collection;
    }

    public void setCollection(StudyGroupCollectionManager collection) {
        this.collection = collection;
        Map<Long, StudyGroup> c = collection.getCollection();
    }

    public Command() {};

    public Command(StudyGroupCollectionManager collection) {
        this.collection = collection;
    }

    private String[] args;
    private StudyGroup studyGroup;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) throws InvalidArgumentsException {
        this.args = args;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public abstract void execute() throws Exception;

}
