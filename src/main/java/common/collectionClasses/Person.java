package common.collectionClasses;
import common.exceptions.InvalidInputException;

import java.io.Serializable;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Длина строки не должна быть больше 30, Длина строки должна быть не меньше 6, Значение этого поля должно быть уникальным, Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person() {};

    public Person(String name, Color hairColor, Location location) {
        this.name = name;
        this.hairColor = hairColor;
        this.location = location;
    }

    public Person(String name, String passportID, Color hairColor, Country nationality, Location location) {
        try {
            setName(name);
            setPassportID(passportID);
            setHairColor(hairColor);
            setNationality(nationality);
            setLocation(location);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name can't be empty!");
        }
        this.name = name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) throws InvalidInputException {
        if (passportID != null) {
            if (passportID.length() < 6 || passportID.length() > 30) {
                throw new InvalidInputException("Passport ID's length is in between 6 and 30");
            }
        }
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) throws InvalidInputException {
        if (hairColor == null) {
            throw new InvalidInputException("Hair color must be specified");
        }
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) throws InvalidInputException {
        if (location == null) {
            throw new InvalidInputException("Location must be specified");
        }
        this.location = location;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", " +
                "Passport ID: " + passportID + ", " +
                "Hair color: " + hairColor + ", " +
                "Nationality: " + nationality + ", " +
                "Location: " + location;
    }
}
