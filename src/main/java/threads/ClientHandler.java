package threads;

import common.collectionManagement.StudyGroupCollectionManager;
import common.commands.CommandExecutor;
import common.commands.CommandWithResponse;
import common.exceptions.EmptyCollectionException;
import common.exceptions.InvalidInputException;
import common.networkStructures.*;
import databaseManagement.DataLoader;
import databaseManagement.DatabaseHandler;
import databaseManagement.DatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final CommandExecutor commandExecutor;
    private final StudyGroupCollectionManager collectionManager;
    private final ExecutorService commandExecutionThreadPool;
    private final DatabaseHandler databaseHandler;

    public ClientHandler(Socket clientSocket, CommandExecutor commandExecutor, StudyGroupCollectionManager collectionManager, ExecutorService commandExecutionThreadPool, DatabaseHandler databaseHandler) {
        this.clientSocket = clientSocket;
        this.commandExecutor = commandExecutor;
        this.collectionManager = collectionManager;
        this.commandExecutionThreadPool = commandExecutionThreadPool;
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            Request object = null;
            DataLoader dataLoader = new DataLoader();
            try {
                object = (Request) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            if (object.getClass() == AuthenticationRequest.class) {
                AuthenticationResponse response = new AuthenticationResponse();
                AuthenticationRequest request = (AuthenticationRequest) object;
                String username = request.getUsername();
                String password = request.getPassword();

                System.out.println("Received authentication request from user " + username);

                try {
                    if (request.isNewUser()) {
                        boolean registrationResult = databaseHandler.registerUser(username, password);
                        response.setAuthenticated(registrationResult);

                        if (!registrationResult) {
                            response.setException(new InvalidInputException("User with the entered username already exists"));
                        }

                    } else {
                        response.setAuthenticated(true);

                        if (!databaseHandler.userExists(username)) {
                            response.setAuthenticated(false);
                            response.setException(new InvalidInputException("Unknown username"));
                        }

                        if (databaseHandler.userExists(username) && !databaseHandler.getUsersPassword(username).equals(password)) {
                            response.setAuthenticated(false);
                            response.setException(new InvalidInputException("Invalid password"));
                        }
                    }

                } catch (SQLException e) {
                    response.setAuthenticated(false);
                    response.setException(e);
                }
                try {
                    out.writeObject(response);
                    out.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                //System.out.println(response.getOutput());
                System.out.println("Response to authentication successfully sent to client");


            } else {
                CommandRequest request = (CommandRequest) object;
                System.out.println("Received request: " + request.getCommand());

                try {
                    if (!request.getPassword().equals(databaseHandler.getUsersPassword(request.getUsername()))) {
                        System.out.println(request.getUsername());
                        Response response = new CommandResponse("Exception: Authentication error");

                        try {
                            out.writeObject(response);
                            out.flush();
                            System.out.println("Response to a command successfully sent to client");
                        } catch (IOException e) {
                            System.err.println("Error sending response: " + e.getMessage());
                        }
                        return;
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                }


                CommandWithResponse command = request.getCommand();
                command.setDatabaseHandler(databaseHandler);

                commandExecutor.setCollection(collectionManager);
                command.setCollection(collectionManager);


                Future<CommandResponse> futureResponse = commandExecutionThreadPool.submit(() -> {
                    try {
                        command.setDatabaseManager(new DatabaseManager(databaseHandler, dataLoader));
                        command.setUsername(request.getUsername());

                        commandExecutor.execute(command);
                        return commandExecutor.getCommandResponse();
                    } catch (Exception e) {
                        System.out.println("Command " +  command.getClass() + " threw the exception: " + e.getClass());
                        return new CommandResponse("Exception: " + e.getMessage());
                    }
                });

                try {
                    out.writeObject(futureResponse.get());
                    out.flush();
                    System.out.println("Response to a command successfully sent to client");
                } catch (IOException e) {
                    System.err.println("Error sending response: " + e.getMessage());
                } catch (ExecutionException | InterruptedException ex) {
                    System.out.println("Error receiving response: " + ex.getMessage());
                }

            }
        } catch (IOException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}