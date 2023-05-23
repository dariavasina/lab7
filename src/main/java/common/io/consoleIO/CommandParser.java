package common.io.consoleIO;

import common.collectionClasses.StudyGroup;
import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandExecutor;
import common.commands.CommandWithResponse;
import common.commands.commandObjects.*;
import common.dataStructures.Pair;
import common.dataStructures.Triplet;
import common.exceptions.*;
import common.io.fileIO.StudyGroupReader;
import common.json.FileManager;

import java.util.*;

public class CommandParser {
    //private final StudyGroupCollectionManager collection;
    private String filename;
    private FileManager fileManager;


    public CommandParser(StudyGroupCollectionManager collection) {
        //this.collection = collection;
    }

    public CommandParser() {};

    public CommandParser(StudyGroupCollectionManager collection, FileManager fileManager, String fileName) {
        //this(collection);
        this.filename = fileName;
        this.fileManager = fileManager;
    }

    public Pair<String, String[]> getCommand(Scanner scanner) throws CommandDoesNotExistException {
        List<String> line = Arrays.stream(scanner.nextLine().strip().replaceAll(" +", " ").split(" ")).toList();

        if (!commandsList.contains(line.get(0)) && !line.get(0).equals("")) {
            throw new CommandDoesNotExistException(line.get(0));
        }

        String command = line.get(0);
        String[] args = line.subList(1, line.size()).toArray(new String[0]);

        return new Pair<>(command, args);
    }

    private static final ArrayList<String> commandsList = new ArrayList<>() {{
        add("help");
        add("info");
        add("show");
        add("insert");
        add("update");
        add("remove_key");
        add("clear");
        add("execute_script");
        add("exit");
        add("replace_if_greater");
        add("replace_if_lower");
        add("remove_greater_key");
        add("count_by_students_count");
        add("filter_by_should_be_expelled");
        add("print_field_descending_students_count");
    }};

    private static final ArrayList<String> studyGroupCommands = new ArrayList<>() {{
        add("insert");
        add("update");
        add("replace_if_lower");
        add("replace_if_greater");
    }};

    private static final ArrayList<String> lineArgCommands = new ArrayList<>() {{
        addAll(studyGroupCommands);
        add("remove_key");
        add("execute_script");
        add("remove_greater_key");
        add("count_by_students_count");
        add("filter_by_should_be_expelled");
    }};

    private final Map<String, CommandWithResponse> commandsTable = new HashMap<>() {{
        put("help", new HelpCommand());
        put("info", new InfoCommand());
        put("show", new ShowCommand());
        put("insert", new InsertCommand());
        put("update", new UpdateCommand());
        put("remove_key", new RemoveKeyCommand());
        put("clear", new ClearCommand());
        put("replace_if_greater", new ReplaceIfGreaterCommand());
        put("replace_if_lower", new ReplaceIfLowerCommand());
        put("remove_greater_key", new RemoveGreaterKeyCommand());
        put("count_by_students_count", new CountByStudentsCountCommand());
        put("filter_by_should_be_expelled", new FilterByShouldBeExpelledCommand());
        put("print_field_descending_students_count", new PrintFieldDescendingStudentsCountCommand());
    }};

    public Triplet<String, String[], StudyGroup> readCommand(Scanner scanner, boolean fromFile) throws CommandDoesNotExistException,
            InvalidInputException, KeyDoesNotExistException, KeyAlreadyExistsException {

        Pair<String, String[]> input = getCommand(scanner);
        String commandName = input.getFirst();
        String[] args = input.getSecond();
        StudyGroup studyGroup = null;

        CommandExecutor commandExecutor = new CommandExecutor();

        if (studyGroupCommands.contains(commandName)) {
            if (!fromFile) {
                StudyGroupConsoleReader studyGroupConsoleReader = new StudyGroupConsoleReader();
                studyGroup = studyGroupConsoleReader.readStudyGroup(scanner);
            } else {
                StudyGroupReader studyGroupReader = new StudyGroupReader();
                studyGroup = studyGroupReader.readStudyGroup(scanner);
            }
        }
        return new Triplet<>(commandName, args, studyGroup);
    }

    public CommandWithResponse pack(Triplet<String, String[], StudyGroup> parsedCommand) throws InvalidInputException, InvalidArgumentsException {
        String commandName = parsedCommand.getFirst();
        String[] args = parsedCommand.getSecond();
        StudyGroup studyGroup = parsedCommand.getThird();

        CommandWithResponse command = commandsTable.get(commandName);

        if (lineArgCommands.contains(commandName)) {
            if (args.length == 0) {
                throw new InvalidInputException("Something wrong with command arguments\n" +
                        "Please check that you do not enter any arguments in the same line with the command");
            }
            command.setArgs(args);
        }

        if (studyGroupCommands.contains(commandName)) {
            command.setStudyGroup(studyGroup);
        }

        return command;
    }
}

