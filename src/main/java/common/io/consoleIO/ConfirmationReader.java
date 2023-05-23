package common.io.consoleIO;

import java.util.Scanner;

public class ConfirmationReader {
    Scanner scanner;
    public ConfirmationReader() {
        this.scanner = new Scanner(System.in);
    }

    public static boolean checkTheDesireToEnter(Scanner scanner, String valueName) {
        System.out.printf("Do you want to enter %s? (y/n): ", valueName);
        String input = scanner.nextLine().strip();
        return input.equals("y");
    }

    public boolean readConfirmationFromConsole() {
        System.out.println("Enter y/n to confirm");
        return scanner.nextLine().trim().equals("y");
    }
}
