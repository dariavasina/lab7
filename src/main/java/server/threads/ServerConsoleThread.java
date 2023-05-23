package server.threads;

import common.dataStructures.Pair;
import common.exceptions.CommandDoesNotExistException;
import common.exceptions.InvalidArgumentsException;
import common.exceptions.CommandDoesNotExistException;
import server.ServerCommandExecutor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServerConsoleThread extends Thread {
    public ServerConsoleThread(ServerCommandExecutor serverCommandExecutor) {
        super(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                List<String> line = Arrays.stream(scanner.nextLine().strip().replaceAll(" +", " ").split(" ")).toList();
                Pair<String, String[]> commandPair = new Pair<>(line.get(0), line.subList(1, line.size()).toArray(new String[0]));
                //logger.info("Server command `{}` successfully read", commandPair.getFirst());
                try {
                    serverCommandExecutor.execute(commandPair);
                } catch (InvalidArgumentsException | CommandDoesNotExistException | IOException e) {
                    System.out.println(e.getClass());
                    System.out.println(e.getMessage());
                    //logger.error(e.getMessage());
                }
            }
        });
    }
}