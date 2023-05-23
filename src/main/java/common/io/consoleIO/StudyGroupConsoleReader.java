package common.io.consoleIO;

import common.collectionClasses.*;
import common.exceptions.InvalidInputException;
import common.io.fileIO.StudyGroupReader;

import java.util.Scanner;

public class StudyGroupConsoleReader extends StudyGroupReader {

    @Override
    public String readName(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter group's name: ");
        return super.readName(scanner);
    }

    @Override
    public Integer readStudentsCount(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter students' count: ");
        return super.readStudentsCount(scanner);
    }

    @Override
    public Integer readShouldBeExpelled(Scanner scanner) throws InvalidInputException {
        System.out.print("Enter shouldBeExpelled: ");
        return super.readShouldBeExpelled(scanner);
    }

    @Override
    public FormOfEducation readFormOfEducation(Scanner scanner) throws InvalidInputException {
        System.out.println("Enter one of the following forms of education: \nDISTANCE_EDUCATION, \nFULL_TIME_EDUCATION, \nEVENING_CLASSES");
        return super.readFormOfEducation(scanner);
    }

    @Override
    public Semester readSemester(Scanner scanner) throws InvalidInputException {
        System.out.println("Enter one of the following semesters: \n3, \n4, \n8");
        return super.readSemester(scanner);
    }


    public StudyGroup readStudyGroup(Scanner scanner) {
        String name;
        while (true) {
            try {
                name = readName(scanner);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage() + ", please try again");
            }
        }

        CoordinatesConsoleReader reader = new CoordinatesConsoleReader();
        Coordinates coordinates = reader.readCoordinates(scanner);

        StudyGroup studyGroup = new StudyGroup(name, coordinates);

        boolean answer = ConfirmationReader.checkTheDesireToEnter(scanner, "studentsCount");
        if (answer) {
            Integer studentsCount;
            while (true) {
                try {
                    studentsCount = readStudentsCount(scanner);
                    studyGroup.setStudentsCount(studentsCount);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }

        }
        else {
            try {
                studyGroup.setStudentsCount(0);
            } catch (InvalidInputException e) {}
        }

        answer = ConfirmationReader.checkTheDesireToEnter(scanner, "shouldBeExpelled");
        if (answer) {
            Integer shouldBeExpelled;
            while (true) {
                try {
                    shouldBeExpelled = readShouldBeExpelled(scanner);
                    studyGroup.setShouldBeExpelled(shouldBeExpelled);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
        }

        answer = ConfirmationReader.checkTheDesireToEnter(scanner, "formOfEducation");
        if (answer) {
            FormOfEducation f;
            while (true) {
                try {
                    f = readFormOfEducation(scanner);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
            studyGroup.setFormOfEducation(f);
        }

        answer = ConfirmationReader.checkTheDesireToEnter(scanner, "semester");
        if (answer) {
            Semester s;
            while (true) {
                try {
                    s = readSemester(scanner);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage() + ", please try again");
                }
            }
            studyGroup.setSemester(s);
        }

        answer = ConfirmationReader.checkTheDesireToEnter(scanner, "groupAdmin");
        if (answer) {
            PersonConsoleReader pr = new PersonConsoleReader();
            Person admin = pr.readPerson(scanner);
            studyGroup.setGroupAdmin(admin);
        }
        return studyGroup;
    }
}
