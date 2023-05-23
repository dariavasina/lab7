package common.io.fileIO;

import common.collectionClasses.Location;
import common.exceptions.InvalidInputException;

import java.util.Scanner;

public class LocationReader {
    public Double readX(Scanner scanner) throws InvalidInputException {
        double x;
        try {
            String input = scanner.nextLine();
            x = Double.parseDouble(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("x coordinate must be a number");
        }
        return x;
    }

    public Float readY(Scanner scanner) throws InvalidInputException {
        float y;
        try {
            String input = scanner.nextLine();
            y = Float.parseFloat(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("y coordinate must be a number");
        }
        return y;
    }

    public Long readZ(Scanner scanner) throws InvalidInputException {
        long z;
        try {
            String input = scanner.nextLine();
            z = Long.parseLong(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("z coordinate must be a number");
        }
        return z;
    }

    public Location readLocation(Scanner scanner) throws InvalidInputException {
        double x = readX(scanner);
        float y = readY(scanner);
        long z = readZ(scanner);
        return new Location(x, y, z);
    }
}
