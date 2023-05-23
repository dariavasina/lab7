package common.json;

import common.collectionClasses.*;
import org.json.JSONObject;

public class StudyGroupJsonWriter {
    public static JSONObject toJson(StudyGroup studyGroup) {
        JSONObject json = new JSONObject();
        json.put("name", studyGroup.getName());

        JSONObject coordinatesJson = new JSONObject();
        Coordinates coordinates = studyGroup.getCoordinates();
        coordinatesJson.put("x", coordinates.getX());
        coordinatesJson.put("y", coordinates.getY());
        json.put("coordinates", coordinatesJson);

        Integer studentsCount = studyGroup.getStudentsCount();
        if (studentsCount != null) {
            json.put("studentsCount", studentsCount);
        }

        Integer shouldBeExpelled = studyGroup.getShouldBeExpelled();
        if (shouldBeExpelled != null) {
            json.put("shouldBeExpelled", shouldBeExpelled);
        }

        FormOfEducation formOfEducation = studyGroup.getFormOfEducation();
        if (formOfEducation != null) {
            json.put("formOfEducation", formOfEducation.toString());
        }

        Semester semester = studyGroup.getSemester();
        if (semester != null) {
            json.put("semester", semester.toString());
        }

        Person groupAdmin = studyGroup.getGroupAdmin();
        if (groupAdmin != null) {
            JSONObject groupAdminJson = new JSONObject();
            groupAdminJson.put("name", groupAdmin.getName());
            groupAdminJson.put("hairColor", groupAdmin.getHairColor());

            Location location = groupAdmin.getLocation();
            JSONObject locationJson = new JSONObject();
            locationJson.put("x", location.getX());
            locationJson.put("y", location.getY());
            Long z = location.getZ();
            locationJson.put("z", z);
            groupAdminJson.put("location", locationJson);

            String passportID = groupAdmin.getPassportID();
            if (passportID != null) {
                groupAdminJson.put("passportID", groupAdmin.getPassportID());
            }

            Country nationality = groupAdmin.getNationality();
            if (nationality != null) {
                groupAdminJson.put("nationality", groupAdmin.getNationality());
            }

            json.put("groupAdmin", groupAdminJson);
        }

        return json;
    }
}