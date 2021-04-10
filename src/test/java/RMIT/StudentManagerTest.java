package RMIT;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentManagerTest {
    @Before
    public void resetSingleton() {
        StudentEnrolmentManager.reset();
        StudentManager.reset();
        CourseManager.reset();
    }

    @Test
    public void getInstance() {
        StudentManager studentManager = StudentManager.getInstance();
        assertNotNull(studentManager);
    }

    @Test
    public void reset() {
        StudentManager studentManager = StudentManager.getInstance();
        StudentManager.reset();
        StudentManager newStudentManager = StudentManager.getInstance();
        assertNotNull(newStudentManager);
    }

    @Test
    public void setListOfStudents() {
        StudentManager studentManager = StudentManager.getInstance();
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student("s3812649", "Nguyen Pham Quoc Minh", Converter.StrToDate("2001-08-24")));
        studentManager.setListOfStudents(studentList);
        assertNotNull(studentManager.getListOfStudents());
    }

    @Test
    public void addFromCsv() {
        StudentManager studentManager = StudentManager.getInstance();
        studentManager.addFromCsv("src/resources/students/default.csv");
        assertEquals(10, studentManager.getListOfStudents().size());
    }

    @Test
    public void saveToCsv() {
        StudentManager studentManager = StudentManager.getInstance();
        studentManager.addFromCsv("src/resources/students/default.csv");
        boolean res = studentManager.saveToCsv("src/resources/students/test-file", studentManager.getListOfStudents());
        assertTrue(res);
    }
    @Test
    public void getValidStudentId() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        Student student = studentEnrolmentManager.getStudentManager().getValidStudentId("s3812649");
        assertNotNull(student);
    }

    @Test
    public void getListOfStudents() {
        StudentManager studentManager = StudentManager.getInstance();
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student("s3812649", "Nguyen Pham Quoc Minh", Converter.StrToDate("2001-08-24")));
        studentManager.setListOfStudents(studentList);
        assertNotNull(studentManager.getListOfStudents());
    }
}