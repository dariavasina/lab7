package databaseManagement;

import common.collectionClasses.*;
import common.exceptions.InvalidInputException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataLoader {
    private DatabaseHandler databaseHandler;

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public LinkedHashMap<Long, StudyGroup> loadCollection() throws SQLException {
        String query = "select sg.id, sg.name, sg.x_coordinate, sg.y_coordinate, creation_date, students_count, should_be_expelled,\n" +
                "                form_of_education.name, semester.name, p.id, p.name, p.passport_id, color.name, country.name,\n" +
                "                p.x_coordinate, p.y_coordinate, p.z_coordinate, sg.collection_key\n" +
                "                from study_group sg\n" +
                "                left join form_of_education on sg.form_of_education_id = form_of_education.id\n" +
                "                left join semester on sg.semester_id = semester.id\n" +
                "                left join person p on p.id = sg.group_admin_id\n" +
                "                left join color on p.hair_color_id = color.id\n" +
                "                left join country on p.nationality_id = country.id;";


        LinkedHashMap<Long, StudyGroup> data = new LinkedHashMap<>();

        try (PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);

                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getDouble(3));
                coordinates.setY(resultSet.getInt(4));

                java.time.LocalDate creationDate = resultSet.getTimestamp(5).toLocalDateTime().toLocalDate();
                int studentsCount = resultSet.getInt(6);
                int shouldBeExpelled = resultSet.getInt(7);

                String f = resultSet.getString(8);
                FormOfEducation formOfEducation;
                if (!resultSet.wasNull()) {
                    formOfEducation = FormOfEducation.valueOf(f);
                } else {
                    formOfEducation = null;
                }

                String s = resultSet.getString(9);
                Semester semester;

                if (!resultSet.wasNull()) {
                    semester = Semester.valueOf(s);
                } else {
                    semester = null;
                }

                Person groupAdmin = new Person();
                int groupAdminId = resultSet.getInt(10);
                if (!resultSet.wasNull()) {
                    groupAdmin.setName(resultSet.getString(11));

                    String p = resultSet.getString(12);
                    if (!resultSet.wasNull()) {
                        groupAdmin.setPassportID(p);
                    }

                    groupAdmin.setHairColor(Color.valueOf(resultSet.getString(13)));

                    String n = resultSet.getString(14);
                    if (!resultSet.wasNull()) {
                        groupAdmin.setNationality(Country.valueOf(n));
                    }

                    Location location = new Location();
                    location.setX(resultSet.getDouble(15));
                    location.setY(resultSet.getFloat(16));
                    location.setZ(resultSet.getInt(17));
                    groupAdmin.setLocation(location);

                }

                Long collection_key = resultSet.getLong(18);

                StudyGroup studyGroup = new StudyGroup();

                studyGroup.setId(id);
                studyGroup.setName(name);
                studyGroup.setCoordinates(coordinates);
                studyGroup.setCreationDate(creationDate);
                studyGroup.setStudentsCount(studentsCount);
                studyGroup.setShouldBeExpelled(shouldBeExpelled);
                studyGroup.setFormOfEducation(formOfEducation);
                studyGroup.setSemester(semester);
                studyGroup.setGroupAdmin(groupAdmin);


                data.put(collection_key, studyGroup);
            }
        } catch (InvalidInputException e) {
            //
        }

        return data;
    }

    public Map<Long, String> loadElementsOwners() throws SQLException{
        Map<Long, String> elementsOwners = new HashMap<>();
        String query = "select study_group.id, u.username\n" +
                "from study_group\n" +
                "inner join users u on study_group.owner_id = u.id;";
        try (PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long studyGroupId = resultSet.getLong(1);
                String username = resultSet.getString(2);

                elementsOwners.put(studyGroupId, username);
            }
        }
        return elementsOwners;
    }
}
