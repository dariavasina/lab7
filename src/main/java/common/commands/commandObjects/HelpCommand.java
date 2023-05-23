package common.commands.commandObjects;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandWithResponse;
import common.networkStructures.CommandResponse;
import common.networkStructures.Response;

public class HelpCommand extends CommandWithResponse {
    StringBuilder output = new StringBuilder();
    public HelpCommand(StudyGroupCollectionManager collection) {
        super(collection);
    }

    public HelpCommand() {
    }

    @Override
    public void execute() {
        output.append("help: output help for available commands\n");
        output.append("info: output to the standard output stream information about the collection (type, initialization date, number of elements, etc.)\n");
        output.append("show: output to the standard output stream all elements of the collection in the string representation\n");
        output.append("insert <id> {element}: add a new element with the specified key\n");
        output.append("update <id> {element}: update the value of a collection element whose id is equal to the specified\n");
        output.append("remove_key <id>: delete an element from the collection by its key\n");
        output.append("clear: clear the collection\n");
        output.append("save: save the collection to a file\n");
        output.append("execute_script <file_name>: read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode\n");
        output.append("exit: terminate the program (without saving to a file)\n");
        output.append("replace_if_greater <key> {element}: replace the value by key if the new value is greater than the old one\n");
        output.append("replace_if_lower <id> {element}: replace the value by key if the new value is less than the old one.");
        output.append("remove_greater_key <key>: remove all items from the collection whose key exceeds the specified one\n");
        output.append("count_by_students_count <studentsCount> : print the number of elements whose studentsCount field value is equal to the specified\n");
        output.append("filter_by_should_be_expelled <shouldBeExpelled> : output elements whose value of the field shouldBeExpelled is equal to the specified\n");
        output.append("print_field_descending_students_count: print the values of the studentsCount field of all elements in descending order");
    }

    @Override
    public CommandResponse getCommandResponse() {
        return new CommandResponse(output.toString());
    }
}
