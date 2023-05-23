package common.commands.commandObjects;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.exceptions.InvalidArgumentsException;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public class FilterByShouldBeExpelledCommand extends CommandWithResponse {
    StringBuilder output;
    public FilterByShouldBeExpelledCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public FilterByShouldBeExpelledCommand() {
    }

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        try {
            Integer shouldBeExpelled = Integer.parseInt(args[0]);
            super.setArgs(new String[]{String.valueOf(shouldBeExpelled)});
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("shouldBeExpelled must be a number! Please try to enter a command again");
        }
    }

    @Override
    public void execute() {
        Integer shouldBeExpelled = Integer.parseInt(getArgs()[0]);
        try {
            getCollection().getReadLock().lock();
            output = getCollection().filterByShouldBeExpelled(shouldBeExpelled);
        } finally {
            getCollection().getReadLock().unlock();
        }
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse(output.toString());
    }
}
