package RMIT;

import java.util.Date;

public class Student {
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
}