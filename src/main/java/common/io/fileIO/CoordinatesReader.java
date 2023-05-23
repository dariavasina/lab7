package common.io.fileIO;

import common.collectionClasses.Coordinates;
import common.exceptions.InvalidInputException;

import java.util.Scanner;

public class CoordinatesReader {
    public Double readX(Scanner scanner) throws InvalidInputException {
        double x;
        try {
            String input = scanner.nextLine();
            x = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("x coordinate must be a number");
        }
        return x;
    }

    public int readY(Scanner scanner) throws InvalidInputException {
        int y;
        try {
            String input = scanner.nextLine();
            y = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("y coordinate must be a number");
        }
        return y;
    }

    public Coordinates readCoordinates(Scanner scanner) throws InvalidInputException {
        Double x = readX(scanner);
        int y = readY(scanner);
        return new Coordinates(x, y);

    }
}
