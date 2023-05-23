package common.collectionClasses;
import common.exceptions.InvalidInputException;

import java.io.Serializable;
import java.time.LocalDate;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private Long id;
    private static Long nextId = 0L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private Integer shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semester; //Поле может быть null
    private Person groupAdmin; //Поле может быть null

    public StudyGroup(String name, Coordinates coordinates, Integer studentsCount, Integer shouldBeExpelled, FormOfEducation formOfEducation, Semester semester, Person groupAdmin) {
        try {
            this.id = nextId;
            nextId++;
            setName(name);
            setCoordinates(coordinates);
            setStudentsCount(studentsCount);
            setShouldBeExpelled(shouldBeExpelled);
            setFormOfEducation(formOfEducation);
            setSemester(semester);
            setGroupAdmin(groupAdmin);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public StudyGroup() {};

    public StudyGroup(String name, Coordinates coordinates) {
        try {
            this.id = nextId;
            nextId++;
            setName(name);
            setCoordinates(coordinates);
            this.creationDate = LocalDate.now();
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public Integer getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemester() {
        return semester;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null) {
            throw new InvalidInputException("Please provide a group name");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) throws InvalidInputException {
        if (coordinates == null) {
            throw new InvalidInputException("Please provide coordinates for the group");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setStudentsCount(Integer studentsCount) throws InvalidInputException {
        if (studentsCount < 0) {
            throw new InvalidInputException("Students' count can't be below 0!");
        }
        this.studentsCount = studentsCount;
    }

    public void setShouldBeExpelled(Integer shouldBeExpelled) throws InvalidInputException {
        if (shouldBeExpelled < 0) {
            throw new InvalidInputException("ShouldBeExpelled can't be below 0!");
        }
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    @Override
    public int compareTo(StudyGroup studyGroup) {
        if (studentsCount > studyGroup.studentsCount) return 1;
        if (studentsCount < studyGroup.studentsCount) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Coordinates: " + coordinates + "\n" +
                "Creation date: " + creationDate + "\n" +
                "Number of students: " + studentsCount + "\n" +
                "Should be expelled: " + shouldBeExpelled + "\n" +
                "Form of education: " + formOfEducation + "\n" +
                "Group admin: " + groupAdmin + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != StudyGroup.class) {
            return false;
        }
        StudyGroup studyGroup = (StudyGroup) obj;
        return name.equals(studyGroup.getName());
    }
}
