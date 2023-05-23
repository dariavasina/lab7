package common.io.consoleIO;

import common.collectionClasses.Location;
import common.exceptions.InvalidInputException;
import common.io.fileIO.LocationReader;

import java.util.Scanner;

public class LocationConsoleReader extends LocationReader {
    @Override
    public Double readX(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter location's x coordinate: ");
        return super.readX(scanner);
    }

    @Override
    public Float readY(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter location's y coordinate: ");
        return super.readY(scanner);
    }

    @Override
    public Long readZ(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter location's z coordinate: ");
        return super.readZ(scanner);
    }

    public Location readLocation(Scanner scanner){
        Double x;
        while (true) {
            try {
                x = readX(scanner);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage() + ", please try again");
            }
        }

        Float y;
        while (true) {
            try {
                y = readY(scanner);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage()+ ", please try again");
            }
        }


        long z;
        boolean answer = ConfirmationReader.checkTheDesireToEnter(scanner, "z coordinate");
        if (answer) {
            while (true) {
                try {
                    z = readZ(scanner);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
            return new Location(x, y, z);
        }
        return new Location(x, y);
    }
}
