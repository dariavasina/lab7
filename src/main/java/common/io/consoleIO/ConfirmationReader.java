package common.io.consoleIO;

import java.util.Scanner;

public class ConfirmationReader {
    public static boolean checkTheDesireToEnter(Scanner scanner, String valueName) {
        System.out.printf("Do you want to enter %s? (y/n): ", valueName);
        String input = scanner.nextLine().strip();
        return input.equals("y");
    }
}
