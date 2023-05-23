package common.json;

import common.collectionClasses.*;
import common.exceptions.FileParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class StudyGroupJsonReader {
    public static Coordinates readCoordinates(JSONObject coordinatesJson) throws FileParseException {
        double x;
        try {
            x = coordinatesJson.getDouble("x");
        } catch (JSONException e) {
            throw new FileParseException(e);
        }

        int y;
        try {
            y = coordinatesJson.getInt("y");
        } catch (JSONException e) {
            throw new FileParseException(e);
        }

        return new Coordinates(x, y);
    }

    public static Person readGroupAdmin(JSONObject groupAdminJson) throws FileParseException {
        String name = groupAdminJson.getString("name");

        String passportID = null;
        if (groupAdminJson.has("passportID")) {
            passportID = groupAdminJson.getString("passportID");
        }

        Color hairColor = null;
        if (groupAdminJson.has("hairColor")) {
            try {
                hairColor = Color.valueOf(groupAdminJson.getString("hairColor"));
            } catch (IllegalArgumentException e) {
                throw new FileParseException(e);
            }
        }


        Country nationality = null;
        if (groupAdminJson.has("nationality")) {
            try {
                nationality = Country.valueOf(groupAdminJson.getString("nationality"));
            } catch (IllegalArgumentException e) {
                throw new FileParseException(e);
            }

        }

        JSONObject locationJson = groupAdminJson.getJSONObject("location");
        Double x = null;
        try {
            x = locationJson.getDouble("x");
        } catch (JSONException e) {
            throw new FileParseException(e);
        }

        Float y = null;
        try {
            y = (float) locationJson.getDouble("y");
        } catch (JSONException e) {
            throw new FileParseException(e);
        }

        long z;
        Location location = new Location(x, y);

        if (locationJson.has("z")) {
            z = locationJson.getLong("z");
            try {
                location.setZ(z);
            } catch (IllegalArgumentException e) {
                throw new FileParseException(e);
            }

        }

        return new Person(name, passportID, hairColor, nationality, location);
    }



    public static StudyGroup readStudyGroup(JSONObject json) throws FileParseException {
        String name = json.getString("name");

        Coordinates coordinates = null;
        coordinates = readCoordinates(json.getJSONObject("coordinates"));

        Integer studentsCount = 0;
        if (json.has("studentsCount")) {
            try {
                studentsCount = json.getInt("studentsCount");
            } catch (JSONException e) {
                throw new FileParseException(e);
            }
        }

        Integer shouldBeExpelled = 0;
        if (json.has("shouldBeExpelled")) {
            try {
                shouldBeExpelled = json.getInt("shouldBeExpelled");
            } catch (JSONException e) {
                throw new FileParseException(e);
            }
        }


        FormOfEducation formOfEducation = null;
        if (json.has("formOfEducation")) {
            try {
                formOfEducation = FormOfEducation.valueOf(json.getString("formOfEducation"));
            } catch (IllegalArgumentException e) {
                throw new FileParseException(e);
            }

        }

        Semester semester = null;
        if (json.has("semester")) {
            try {
                semester = Semester.valueOf(json.getString("semester"));
            } catch (JSONException e) {
                throw new FileParseException(e);
            }

        }

        Person groupAdmin = null;
        if (json.has("groupAdmin")) {
            groupAdmin = readGroupAdmin(json.getJSONObject("groupAdmin"));

        }

        return new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semester, groupAdmin);

    }
}
