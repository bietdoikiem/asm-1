package RMIT;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentEnrolmentTest {

    @Test
    public void getStudent() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        StudentEnrolment enrolment = new StudentEnrolment(s1, c1, c1.getSemester());
        assertNotNull(enrolment);
    }

    @Test
    public void setStudent() {
        StudentEnrolment enrolment = new StudentEnrolment();
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        enrolment.setStudent(s1);
    }

    @Test
    public void getCourse() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        StudentEnrolment enrolment = new StudentEnrolment(s1, c1, c1.getSemester());
        assertNotNull(enrolment.getCourse());
    }

    @Test
    public void setCourse() {
        StudentEnrolment enrolment = new StudentEnrolment();
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        enrolment.setCourse(c1);
    }

    @Test
    public void getSemester() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        StudentEnrolment enrolment = new StudentEnrolment(s1, c1, c1.getSemester());
        assertEquals("2021A", enrolment.getSemester());
    }

    @Test
    public void setSemester() {
        StudentEnrolment enrolment = new StudentEnrolment();
        enrolment.setSemester("2021A");
        assertNotNull(enrolment.getSemester());
    }

    @Test
    public void compareTo() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        StudentEnrolment enrolment = new StudentEnrolment(s1, c1, c1.getSemester());
        StudentEnrolment enrolment2 = new StudentEnrolment(s1, c1, c1.getSemester());
        assertEquals(1, enrolment2.compareTo(enrolment));
    }

    @Test
    public void testToString() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        StudentEnrolment enrolment = new StudentEnrolment(s1, c1, c1.getSemester());
        assertEquals("Student ID: " + enrolment.getStudent().getId() + " || " + "Course ID: " + enrolment.getCourse().getId() + " || " + enrolment.getSemester(), enrolment.toString());
    }
}