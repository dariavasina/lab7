package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.collectionClasses.StudyGroup;
import common.commands.CommandExecutor;
import common.collectionManagement.StudyGroupCollectionManager;
import databaseManagement.DataLoader;
import databaseManagement.DatabaseHandler;
import server.threads.ClientHandler;
import server.threads.ServerConsoleThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        int port;

        String jdbcURL = "jdbc:postgresql://localhost:5432/studs";



        if (args.length == 0) {
            System.out.println("Please enter port as an argument");
        }

        String username = null;
        String password = null;

        try {
            Scanner credentials = new Scanner(new FileReader("credentials.txt"));
            username = credentials.nextLine().trim();
            password = credentials.nextLine().trim();
        } catch (FileNotFoundException e) {
            System.out.println("File credentials.txt not found");
            logger.error("File credentials.txt not found");
            System.exit(-1);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to read username and password from credentials.txt");
            System.exit(0);
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Postgresql driver not found");
        }

        try {
            port = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("Server started on port " + port);

            DataLoader dataLoader = new DataLoader();

            DatabaseHandler databaseHandler = new DatabaseHandler(jdbcURL, username, password, logger);
            databaseHandler.connectToDatabase();
            dataLoader.setDatabaseHandler(databaseHandler);

            LinkedHashMap<Long, StudyGroup> collection = dataLoader.loadCollection();
            logger.info("Collection loaded from database");


            StudyGroupCollectionManager collectionManager = new StudyGroupCollectionManager();
            collectionManager.setCollection(collection);

            Map<Long, String> elementsOwners = dataLoader.loadElementsOwners();
            collectionManager.setElementsOwners(elementsOwners);

            CommandExecutor commandExecutor = new CommandExecutor(collectionManager);

            int numThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService requestThreadPool = Executors.newFixedThreadPool(numThreads);
            ExecutorService commandExecutionThreadPool = Executors.newFixedThreadPool(numThreads);

            ServerCommandExecutor serverCommandExecutor = new ServerCommandExecutor(collectionManager);
            Thread consoleInputThread = new ServerConsoleThread(serverCommandExecutor);
            consoleInputThread.start();

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("New client connected: " + clientSocket.getInetAddress());
                    requestThreadPool.submit(new ClientHandler(clientSocket, commandExecutor, collectionManager, commandExecutionThreadPool, databaseHandler, logger));
                } catch (IOException e) {
                    logger.error("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (BindException e) {
            System.out.println("Port is busy, please enter another port");
            logger.error(e.getMessage());
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.out.println("Port must be a number, please try again");
            logger.error(e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            logger.error(e.getMessage());
            System.exit(-1);
        } catch (SQLException e) {
            System.out.println("Could not load collection from the database: " + e.getMessage());
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }

    //todo change commands that work WITH MAP NOT DATABASE

    //todo - message 'server is down' is written until server is up again - probably need to first read the command,
    // then connect to the server


    //todo
    // Enter a command: remove_key 35
    // Exception: Cannot invoke "String.equals(Object)" because the return value of
    // "java.util.Map.get(Object)" is null

    //todo
    //save

    //Exception in thread "Thread-0" java.lang.NullPointerException
    //	at java.base/java.util.Objects.requireNonNull(Objects.java:208)
    //	at java.base/sun.nio.fs.WindowsFileSystem.getPath(WindowsFileSystem.java:216)
    //	at java.base/java.nio.file.Path.of(Path.java:147)
    //	at java.base/java.nio.file.Paths.get(Paths.java:69)
    //	at common.collectionManagement.StudyGroupCollectionManager.save(StudyGroupCollectionManager.java:265)
    //	at server.ServerCommandExecutor.execute(ServerCommandExecutor.java:54)
    //	at server.threads.ServerConsoleThread.lambda$new$0(ServerConsoleThread.java:23)
    //	at java.base/java.lang.Thread.run(Thread.java:833)

}

