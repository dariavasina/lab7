package client;

import common.collectionClasses.StudyGroup;
import common.commands.CommandWithResponse;
import common.dataStructures.Triplet;
import common.exceptions.*;
import common.io.consoleIO.CommandParser;
import common.networkStructures.AuthenticationResponse;
import common.networkStructures.CommandRequest;
import common.networkStructures.CommandResponse;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ScriptExecutor {
    private HashSet<String> executedScripts = new HashSet<>();

    public void executeScript(String filename, InetAddress address, int port, Authenticator authenticator) throws ScriptRecursionException, FileAccessException {
        executedScripts.add(filename);

        CommandParser commandParser = new CommandParser();
        try {
            File file = new File(filename);
            if (!file.exists() || !file.canRead()) {
                throw new FileAccessException("Cannot read file: " + filename);
            }
            Scanner scanner = new Scanner(file);
            while (true) {
                try (Socket socket = new Socket(address, port);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

                    Triplet<String, String[], StudyGroup> parsedCommand = commandParser.readCommand(scanner, true);
                    String commandName = parsedCommand.getFirst();
                    String[] args = parsedCommand.getSecond();
                    if (commandName.equals("execute_script")) {
                        if (executedScripts.contains(args[0])) {
                            throw new ScriptRecursionException("You should not call execute_script recursively!");
                        }
                        executedScripts.add(args[0]);
                        executeScript(args[0], address, port, authenticator);
                    } else {
                        CommandParser cp = new CommandParser();
                        CommandWithResponse command = cp.pack(parsedCommand);
                        CommandRequest request = new CommandRequest(command);
                        request.setUsername(authenticator.getUsername());
                        request.setPassword(authenticator.getPassword());

                        out.writeObject(request);
                        out.flush();

                        CommandResponse response = (CommandResponse) in.readObject();

                        if (response == null) {
                            System.out.println("Server is down\nPlease try again later");
                        } else {
                            System.out.println(response.getOutput());
                        }
                    }
                } catch (CommandDoesNotExistException | ScriptRecursionException | InvalidInputException |
                         KeyDoesNotExistException
                         | InvalidArgumentsException | ClassNotFoundException | KeyAlreadyExistsException e) {
                    System.out.println(e.getMessage());

                } catch (Exception e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

