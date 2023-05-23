package common.commands.commandObjects;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.exceptions.InvalidArgumentsException;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public class ReplaceIfLowerCommand extends CommandWithResponse {
    public ReplaceIfLowerCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public ReplaceIfLowerCommand() {
    }

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        try {
            Long key = Long.parseLong(args[0]);
            super.setArgs(new String[]{ String.valueOf(key) });
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please try to enter a command again");
        }
    }

    @Override
    public void execute() {
        Long key = Long.parseLong(getArgs()[0]);
        StudyGroup studyGroup = getStudyGroup();
        try {
            getCollection().getWriteLock().lock();
            getCollection().replaceIfLower(key, studyGroup);
        } finally {
            getCollection().getReadLock().unlock();
        }
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse("replace_if_lower finished successfully");
    }
}
