package common.io.consoleIO;

import common.collectionClasses.Color;
import common.collectionClasses.Country;
import common.collectionClasses.Location;
import common.collectionClasses.Person;
import common.exceptions.InvalidInputException;
import common.io.fileIO.PersonReader;

import java.util.Scanner;

public class PersonConsoleReader extends PersonReader {
    @Override
    public String readName(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter person's name: ");
        return super.readName(scanner);
    }

    @Override
    public String readPassportId(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter passport id: ");
        return super.readPassportId(scanner);
    }

    @Override
    public Color readHairColor(Scanner scanner) throws InvalidInputException {
        System.out.println("Enter one of the following hair colors: \nRED, \nYELLOW, \nORANGE");
        return super.readHairColor(scanner);
    }

    @Override
    public Country readNationality(Scanner scanner) throws InvalidInputException {
        System.out.println("Enter one of the following countries: \nRUSSIA, \nFRANCE, \nINDIA, \nVATICAN, \nNORTH_KOREA");
        return super.readNationality(scanner);
    }


    public Person readPerson(Scanner scanner){
        String name;
        while (true) {
            try {
                name = readName(scanner);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage() + ", please try again");
            }
        }

        Color color;
        while (true) {
            try {
                color = readHairColor(scanner);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage()+ ", please try again");
            }
        }

        LocationConsoleReader lr = new LocationConsoleReader();
        Location location = lr.readLocation(scanner);

        Person person = new Person(name, color, location);

        boolean answer = ConfirmationReader.checkTheDesireToEnter(scanner, "passportID");
        if (answer) {
            String passportID;
            while (true) {
                try {
                    passportID = readPassportId(scanner);
                    person.setPassportID(passportID);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
        }

        answer = ConfirmationReader.checkTheDesireToEnter(scanner, "nationality");
        if (answer) {
            Country country;
            while (true) {
                try {
                    country = readNationality(scanner);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
            person.setNationality(country);
        }
        return person;
    }
}
