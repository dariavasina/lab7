package common.commands.commandObjects;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.exceptions.InvalidArgumentsException;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public class CountByStudentsCountCommand extends CommandWithResponse {
    int res;
    Integer studentsCount;
    public CountByStudentsCountCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public CountByStudentsCountCommand() {
    }

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        try {
            Integer studentsCount = Integer.parseInt(args[0]);
            super.setArgs(new String[]{String.valueOf(studentsCount)});
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The studentsCount must be a number! Please try to enter a command again");
        }
    }

    @Override
    public void execute() {
        studentsCount = Integer.parseInt(getArgs()[0]);
        try {
            getCollection().getReadLock().lock();
            res = getCollection().countByStudentsCount(studentsCount);
        } finally {
            getCollection().getReadLock().unlock();
        }


    }

    @Override
    public CommandResponse getCommandResponse() {
        StringBuilder output = new StringBuilder();
        output.append("There are ").append(res).append(" study groups in the collection with").append(studentsCount).append(" students");
        return new CommandResponse(output.toString());
    }
}
