package RMIT;

import java.util.ArrayList;

public class StudentEnrolment implements StudentEnrolmentManager {
    private ArrayList<Student> student;
    private ArrayList<Course> course;
    private String semester;

    public StudentEnrolment() {
        super();
    }

    public StudentEnrolment(String semester) {
        this.student = new ArrayList<Student>();
        this.course = new ArrayList<Course>();
        this.semester = semester;
    }

    // ### GETTERS ### //
    public ArrayList<Student> getStudent() {
        return student;
    }

    public ArrayList<Course> getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    // ### SETTERS ### //

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean add(Student student) {
        return true;
    }

    public boolean update(Student student) {
        return false;
    }

    public boolean getOne(String studentID) {
        return true;
    }

    public ArrayList<Student> getAll() {
        return student;
    }
}
