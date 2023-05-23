package common.io.fileIO;

import common.collectionClasses.*;
import common.exceptions.InvalidInputException;

import java.util.Scanner;

public class StudyGroupReader {
    public String readName(Scanner scanner) throws InvalidInputException {
        return scanner.nextLine().strip();
    }

    public Integer readStudentsCount(Scanner scanner) throws InvalidInputException {
        int studentsCount;
        try {
            String input = scanner.nextLine();
            studentsCount = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("Students' count is a number");
        }
        return studentsCount;
    }

    public Integer readShouldBeExpelled(Scanner scanner) throws InvalidInputException {
        int shouldBeExpelled;
        try {
            String input = scanner.nextLine();
            shouldBeExpelled = Integer.parseInt(input);
        }
        catch (NumberFormatException nfe) {
            throw new InvalidInputException("ShouldBeExpelled is a number");
        }
        return shouldBeExpelled;
    }

    public FormOfEducation readFormOfEducation(Scanner scanner) throws InvalidInputException {
        FormOfEducation f;
        String input = scanner.nextLine().toLowerCase().strip();
        switch (input) {
            case "distance_education" -> f = FormOfEducation.DISTANCE_EDUCATION;
            case "full_time_education" -> f = FormOfEducation.FULL_TIME_EDUCATION;
            case "evening_classes" -> f = FormOfEducation.EVENING_CLASSES;
            default -> throw new InvalidInputException("Form of education not found");
        }
        return f;
    }

    public Semester readSemester(Scanner scanner) throws InvalidInputException {
        Semester s;
        String input = scanner.nextLine();
        try {
            int semester = Integer.parseInt(input);
            switch (semester) {
                case 3 -> s = Semester.THIRD;
                case 4 -> s = Semester.FOURTH;
                case 8 -> s = Semester.EIGHTH;
                default -> throw new InvalidInputException("Semester number not found");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Semester number not found");
        }
        return s;
    }

    public StudyGroup readStudyGroup(Scanner scanner) throws InvalidInputException {
        String name = readName(scanner);
        Coordinates coordinates = new CoordinatesReader().readCoordinates(scanner);
        Integer studentsCount = readStudentsCount(scanner);
        Integer shouldBeExpelled = readShouldBeExpelled(scanner);
        FormOfEducation form = readFormOfEducation(scanner);
        Semester semester = readSemester(scanner);
        Person groupAdmin = new PersonReader().readPerson(scanner);
        return new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, form, semester, groupAdmin);
    }
}
