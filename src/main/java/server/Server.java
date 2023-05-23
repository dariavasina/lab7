package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.collectionClasses.StudyGroup;
import common.commands.CommandExecutor;
import common.collectionManagement.StudyGroupCollectionManager;
import databaseManagement.DataLoader;
import databaseManagement.DatabaseHandler;
import threads.ClientHandler;

public class Server {
    public static void main(String[] args) {

        int port;

        String jdbcURL = "jdbc:postgresql://localhost:5432/studs";

        if (args.length < 2) {
            System.out.println("Please enter port and path to the collection file as an argument");
        }

        String username = null;
        String password = null;

        try {
            Scanner credentials = new Scanner(new FileReader("src/main/java/server/credentials.txt"));
            username = credentials.nextLine().trim();
            password = credentials.nextLine().trim();
        } catch (FileNotFoundException e) {
            System.out.println("File credentials.txt not found");
            System.exit(-1);
        }

        try {
            port = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            DataLoader dataLoader = new DataLoader();

            DatabaseHandler databaseHandler = new DatabaseHandler(jdbcURL, username, password);
            databaseHandler.connectToDatabase();
            dataLoader.setDatabaseHandler(databaseHandler);

            LinkedHashMap<Long, StudyGroup> collection = dataLoader.loadCollection();


            StudyGroupCollectionManager collectionManager = new StudyGroupCollectionManager();
            collectionManager.setCollection(collection);

            Map<Long, String> elementsOwners = dataLoader.loadElementsOwners();
            collectionManager.setElementsOwners(elementsOwners);

            CommandExecutor commandExecutor = new CommandExecutor(collectionManager);

            int numThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService requestThreadPool = Executors.newFixedThreadPool(numThreads);
            ExecutorService commandExecutionThreadPool = Executors.newFixedThreadPool(numThreads);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    requestThreadPool.submit(new ClientHandler(clientSocket, commandExecutor, collectionManager, commandExecutionThreadPool, databaseHandler));
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (BindException e) {
            System.out.println("Port is busy, please enter another port");
        } catch (NumberFormatException e) {
            System.out.println("Port must be a number, please try again");
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not load collection from the database: " + e.getMessage());
        }
    }

    //todo change commands that work WITH MAP NOT DATABASE

    //todo - message 'server is down' is written until server is up again - probably need to first read the command,
    // then connect to the server


    //todo
    // Enter a command: remove_key 35
    // Exception: Cannot invoke "String.equals(Object)" because the return value of
    // "java.util.Map.get(Object)" is null

}

