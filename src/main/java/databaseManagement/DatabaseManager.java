package databaseManagement;

import common.collectionClasses.*;

import java.sql.*;

public class DatabaseManager {
    private final DatabaseHandler databaseHandler;
    private final DataLoader dataLoader;

    public DatabaseManager(DatabaseHandler databaseHandler, DataLoader dataLoader) {
        this.databaseHandler = databaseHandler;
        this.dataLoader = dataLoader;
    }

    public DataLoader getDataLoader() {
        return dataLoader;
    }


    public boolean insertPerson(Person person) throws SQLException {

        String personInsertQuery = "insert into person (name, passport_id, hair_color_id, " +
                "nationality_id, x_coordinate, y_coordinate, z_coordinate) values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement personInsertStatement = databaseHandler.getConnection().prepareStatement(personInsertQuery)) {

            personInsertStatement.setString(1, person.getName());

            if (person.getPassportID() != null) {
                personInsertStatement.setString(2, person.getPassportID());
            } else {
                personInsertStatement.setNull(2, Types.NULL);
            }


            personInsertStatement.setInt(3, getColorId(person.getHairColor()));

            if (person.getNationality() != null) {
                personInsertStatement.setInt(4, getCountryId(person.getNationality()));
            } else {
                personInsertStatement.setNull(4, Types.NULL);
            }

            Location location = person.getLocation();
            personInsertStatement.setFloat(5, location.getX().floatValue());
            personInsertStatement.setFloat(6, location.getY());

            if (location.getZ() != null) {
                personInsertStatement.setFloat(7, location.getZ().floatValue());
            } else {
                personInsertStatement.setNull(7, Types.NULL);
            }

            personInsertStatement.executeUpdate();

            return true;
        }
    }


    public int getColorId(Color color) throws SQLException {

        String getColorQuery = "select id from color where name = ?";

        try (PreparedStatement getColorStatement = databaseHandler.getConnection().prepareStatement(getColorQuery)) {

            getColorStatement.setString(1, color.toString());

            ResultSet resultSet = getColorStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int getCountryId(Country country) throws SQLException {

        String getCountryQuery = "select id from country where name = ?";

        try (PreparedStatement getCountryStatement = databaseHandler.getConnection().prepareStatement(getCountryQuery)) {

            getCountryStatement.setString(1, country.toString());

            ResultSet resultSet = getCountryStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    public int getFormOfEducationId(FormOfEducation formOfEducation) throws SQLException {

        String getFormOfEducationQuery = "select id from form_of_education where name = ?";

        try (PreparedStatement getFormOfEducationStatement = databaseHandler.getConnection().prepareStatement(getFormOfEducationQuery)) {

            getFormOfEducationStatement.setString(1, formOfEducation.toString());

            ResultSet resultSet = getFormOfEducationStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    public int getSemester(Semester semester) throws SQLException {

        String getSemesterQuery = "select id from semester where name = ?";

        try (PreparedStatement getSemesterStatement = databaseHandler.getConnection().prepareStatement(getSemesterQuery)) {

            getSemesterStatement.setString(1, semester.toString());

            ResultSet resultSet = getSemesterStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int getPersonId(Person person) throws SQLException {

        String getPersonIdQuery = "select id from person where passport_id = ?";

        try (PreparedStatement getPersonIdStatement = databaseHandler.getConnection().prepareStatement(getPersonIdQuery)) {

            getPersonIdStatement.setString(1, person.getPassportID());

            ResultSet resultSet = getPersonIdStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int getUserId(String username) throws SQLException {

        String getUserIdQuery = "select id from users where username = ?";

        try (PreparedStatement getUserIdStatement = databaseHandler.getConnection().prepareStatement(getUserIdQuery)) {

            getUserIdStatement.setString(1, username);

            ResultSet resultSet = getUserIdStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    public Long getStudyGroupIdByCollectionKey(Long key) throws SQLException {

        String getStudyGroupIdByCollectionKeyQuery = "select id from study_group where collection_key = ?";

        try (PreparedStatement getStudyGroupIdByCollectionKeyStatement = databaseHandler.getConnection().prepareStatement(getStudyGroupIdByCollectionKeyQuery)) {

            getStudyGroupIdByCollectionKeyStatement.setLong(1, key);

            ResultSet resultSet = getStudyGroupIdByCollectionKeyStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        }
        return 0L;
    }


    public boolean insertStudyGroupWithoutId(StudyGroup studyGroup, String owner, Long key) throws SQLException {
        int formOfEducationId;
        if (studyGroup.getFormOfEducation() != null) {
            formOfEducationId = getFormOfEducationId(studyGroup.getFormOfEducation());
        } else {
            formOfEducationId = -1;
        }
        int semesterId;
        if (studyGroup.getSemester() != null) {
            semesterId = getSemester(studyGroup.getSemester());
        } else {
            semesterId = -1;
        }

        int ownerId = getUserId(owner);

        String insertStudyGroupQuery = "insert into study_group (name, x_coordinate, " +
                "y_coordinate, creation_date, students_count, should_be_expelled, form_of_education_id, semester_id, " +
                "group_admin_id, owner_id, collection_key) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStudyGroupStatement = databaseHandler.getConnection().prepareStatement(insertStudyGroupQuery)) {

            insertStudyGroupStatement.setString(1, studyGroup.getName());
            insertStudyGroupStatement.setDouble(2, studyGroup.getCoordinates().getX());
            insertStudyGroupStatement.setInt(3, studyGroup.getCoordinates().getY());
            insertStudyGroupStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            if (studyGroup.getStudentsCount() != null) {
                insertStudyGroupStatement.setInt(5, studyGroup.getStudentsCount());
            } else {
                insertStudyGroupStatement.setInt(5, 1);
            }

            if (studyGroup.getShouldBeExpelled() != null) {
                insertStudyGroupStatement.setLong(6, studyGroup.getShouldBeExpelled());
            } else {
                insertStudyGroupStatement.setLong(6, 1);
            }

            if (formOfEducationId !=-1) {
                insertStudyGroupStatement.setInt(7, formOfEducationId);
            } else {
                insertStudyGroupStatement.setNull(7, Types.NULL);
            }

            if (semesterId != -1) {
                insertStudyGroupStatement.setInt(8, semesterId);
            } else {
                insertStudyGroupStatement.setNull(8, Types.NULL);
            }


            if (studyGroup.getGroupAdmin() != null) {
                insertPerson(studyGroup.getGroupAdmin());
                int groupAdminId = getPersonId(studyGroup.getGroupAdmin());
                insertStudyGroupStatement.setInt(9, groupAdminId);
            } else {
                insertStudyGroupStatement.setNull(9, Types.NULL);
            }

            insertStudyGroupStatement.setInt(10, ownerId);
            insertStudyGroupStatement.setLong(11, key);

            insertStudyGroupStatement.executeUpdate();

            return true;

        }
    }

    public boolean insertStudyGroupWithId(StudyGroup studyGroup, String owner, Long key) throws SQLException {
        int formOfEducationId;
        if (studyGroup.getFormOfEducation() != null) {
            formOfEducationId = getFormOfEducationId(studyGroup.getFormOfEducation());
        } else {
            formOfEducationId = -1;
        }
        int semesterId;
        if (studyGroup.getSemester() != null) {
            semesterId = getSemester(studyGroup.getSemester());
        } else {
            semesterId = -1;
        }

        int ownerId = getUserId(owner);

        String insertStudyGroupQuery = "insert into study_group (id, name, x_coordinate, " +
                "y_coordinate, creation_date, students_count, should_be_expelled, form_of_education_id, semester_id, " +
                "group_admin_id, owner_id, collection_key) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStudyGroupStatement = databaseHandler.getConnection().prepareStatement(insertStudyGroupQuery)) {

            insertStudyGroupStatement.setInt(1, studyGroup.getId().intValue());
            insertStudyGroupStatement.setString(2, studyGroup.getName());
            insertStudyGroupStatement.setDouble(3, studyGroup.getCoordinates().getX());
            insertStudyGroupStatement.setInt(4, studyGroup.getCoordinates().getY());
            insertStudyGroupStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            if (studyGroup.getStudentsCount() != null) {
                insertStudyGroupStatement.setInt(6, studyGroup.getStudentsCount());
            } else {
                insertStudyGroupStatement.setInt(6, 1);
            }

            if (studyGroup.getShouldBeExpelled() != null) {
                insertStudyGroupStatement.setLong(7, studyGroup.getShouldBeExpelled());
            } else {
                insertStudyGroupStatement.setLong(7, 1);
            }

            if (formOfEducationId !=-1) {
                insertStudyGroupStatement.setInt(8, formOfEducationId);
            } else {
                insertStudyGroupStatement.setNull(8, Types.NULL);
            }

            if (semesterId != -1) {
                insertStudyGroupStatement.setInt(9, semesterId);
            } else {
                insertStudyGroupStatement.setNull(9, Types.NULL);
            }


            if (studyGroup.getGroupAdmin() != null) {
                insertPerson(studyGroup.getGroupAdmin());
                int groupAdminId = getPersonId(studyGroup.getGroupAdmin());
                insertStudyGroupStatement.setInt(10, groupAdminId);
            } else {
                insertStudyGroupStatement.setNull(10, Types.NULL);
            }

            insertStudyGroupStatement.setInt(11, ownerId);
            insertStudyGroupStatement.setLong(12, key);

            insertStudyGroupStatement.executeUpdate();

            return true;

        }
    }


    public boolean removeStudyGroup(Long id) throws SQLException {

        String removeQuery = "delete from person\n" +
                "where person.id in (\n" +
                "    select p.id\n" +
                "    from study_group\n" +
                "    join person p on p.id = study_group.group_admin_id\n" +
                "    where study_group.id = ?);\n" +
                "\n" +
                "\n" +
                "delete from study_group where id = ?;";

        try (PreparedStatement removeStatement = databaseHandler.getConnection().prepareStatement(removeQuery)) {

            removeStatement.setObject(1, id, Types.BIGINT);
            removeStatement.setObject(2, id, Types.BIGINT);

            removeStatement.executeUpdate();

            return true;
        }
    }

    public boolean removeAll(String login) throws SQLException {

        int ownerId = getUserId(login);
        String removeStudyGroupsQuery = "delete from person where person.id in (\n" +
                "    select p.id\n" +
                "    from study_group\n" +
                "    join person p on p.id = study_group.group_admin_id\n" +
                "    where owner_id = ?\n" +
                "    );\n" +
                "\n" +
                "    delete from study_group where owner_id = ?" ;

        try (PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(removeStudyGroupsQuery))
        {
            preparedStatement.setInt(1, ownerId);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.executeUpdate();
        }
        return true;
    }

    /*private void deleteStudyGroup(int studyGroupId, int ownerId) throws SQLException {
        String deleteQuery = "DELETE FROM study_group WHERE id = ? and owner_id = ?";
        try (PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, studyGroupId);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.executeUpdate();
        }
    }

    private void deletePerson(int personId) throws SQLException {
        String deleteQuery = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, personId);
            preparedStatement.executeUpdate();
        }
    }*/
}
