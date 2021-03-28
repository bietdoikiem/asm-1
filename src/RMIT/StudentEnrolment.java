package RMIT;

import java.util.ArrayList;

public class StudentEnrolment implements Comparable<StudentEnrolment> {
    private Student student;
    private Course course;
    private String semester;

    public StudentEnrolment() {
        super();
    }

    public StudentEnrolment(Student student, Course course,String semester) {
        this.student = student;
        this.course = course;
        this.semester = semester;
    }
    // ##### //
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    // ##### //
    public int compareTo(StudentEnrolment enrolment) {
        if (this.student.compareTo(enrolment.student) == 1 && this.course.compareTo(enrolment.course) == 1 && this.semester.equals(enrolment.semester)) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Student ID: " + this.student.getId() + " || " + "Course ID: " + this.course.getId() + " || " + this.semester;
    }
}
