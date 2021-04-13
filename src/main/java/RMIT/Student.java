package RMIT;

import java.util.Date;

public class Student implements Comparable<Student> {
    private String id;
    private String name;
    private Date birthDate;

    public Student() {
        super();
    }


    public Student(String id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    // ### GETTERS ### //
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBirthDateString() {
        return Converter.DateToStr(this.birthDate);
    }

    // ### SETTERS ### //
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int compareTo(Student student) {
        if (this.id.equals(student.id) && this.name.equals(student.name) && this.getBirthDateString().equals(student.getBirthDateString())) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + "NAME: " + this.getName() + " " + "birthDate: " + this.getBirthDateString();
    }
}
