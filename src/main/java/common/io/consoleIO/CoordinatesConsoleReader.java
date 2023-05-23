package common.io.consoleIO;

import common.collectionClasses.Coordinates;
import common.exceptions.InvalidInputException;
import common.io.fileIO.CoordinatesReader;

import java.util.Scanner;

public class CoordinatesConsoleReader extends CoordinatesReader {
    @Override
    public Double readX(Scanner scanner) throws InvalidInputException{
        System.out.print("Enter x coordinate: ");
        return super.readX(scanner);
    }

    @Override
    public int readY(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter y coordinate: ");
        return super.readY(scanner);
    }

    public Coordinates readCoordinates(Scanner scanner) {
        Double x;
        while (true) {
            try {
                x = readX(scanner);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage() + ", please try again");
            }
        }

        boolean answer = ConfirmationReader.checkTheDesireToEnter(scanner, "y coordinate");
        if (answer) {
            int y;
            while (true) {
                try {
                    y = readY(scanner);
                    return new Coordinates(x, y);
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
        }
        return new Coordinates(x);
    }
}
