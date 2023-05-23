package common.io.fileIO;

import common.collectionClasses.Color;
import common.collectionClasses.Country;
import common.collectionClasses.Person;
import common.exceptions.InvalidInputException;

import java.util.Scanner;

public class PersonReader {
    public String readName(Scanner scanner) throws InvalidInputException {
        String name = scanner.nextLine().strip();
        if (name.isEmpty()) {
            throw new InvalidInputException("Name can't be empty");
        }
        return name;
    }

    public String readPassportId(Scanner scanner) throws InvalidInputException {
        String passportID = scanner.nextLine().strip();
        if (passportID.length() > 30 || passportID.length() < 6) {
            throw new InvalidInputException("Passport ID's length must be in between 6 and 30");
        }
        return passportID;
    }

    public Color readHairColor(Scanner scanner) throws InvalidInputException {
        Color color;
        String input = scanner.nextLine().strip().toLowerCase();
        switch (input) {
            case "red" -> color = Color.RED;
            case "yellow" -> color = Color.YELLOW;
            case "orange" -> color = Color.ORANGE;
            default -> throw new InvalidInputException("Hair color not found");
        }
        return color;
    }

    public Country readNationality(Scanner scanner) throws InvalidInputException {
        Country country;
        String input = scanner.nextLine().strip().toLowerCase();
        switch (input) {
            case "russia" -> country = Country.RUSSIA;
            case "france" -> country = Country.FRANCE;
            case "india" -> country = Country.INDIA;
            case "vatican" -> country = Country.VATICAN;
            case "north_korea" -> country = Country.NORTH_KOREA;
            default -> throw new InvalidInputException("Nationality not found");
        }
        return country;
    }

    public Person readPerson(Scanner scanner) throws InvalidInputException {
        String name = readName(scanner);
        String passportID = readPassportId(scanner);
        Color hairColor = readHairColor(scanner);
        Country nationality = readNationality(scanner);
        LocationReader lr = new LocationReader();
        return new Person(name, passportID, hairColor, nationality, lr.readLocation(scanner));
    }
}
