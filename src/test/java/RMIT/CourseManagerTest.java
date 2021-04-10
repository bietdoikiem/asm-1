package RMIT;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CourseManagerTest {

    @Before
    public void resetSingleton() {
        StudentEnrolmentManager.reset();
        StudentManager.reset();
        CourseManager.reset();
    }

    @Test
    public void getInstance() {
        CourseManager courseManager = CourseManager.getInstance();
        assertNotNull(courseManager);
    }

    @Test
    public void reset() {
        CourseManager courseManager = CourseManager.getInstance();
        CourseManager.reset();
        CourseManager newCourseManager = CourseManager.getInstance();
        assertNotNull(newCourseManager);
    }

    @Test
    public void getListOfCourses() {
        CourseManager courseManager = CourseManager.getInstance();
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(c1);
        courseManager.setListOfCourses(courseList);
        assertNotNull(courseManager.getListOfCourses());
    }

    @Test
    public void addFromCsv() {
        CourseManager courseManager = CourseManager.getInstance();
        courseManager.addFromCsv("src/resources/courses/default.csv");
        assertEquals(6, courseManager.getListOfCourses().size());
    }

    @Test
    public void saveToCsv() {
        CourseManager courseManager = CourseManager.getInstance();
        courseManager.addFromCsv("src/resources/courses/default.csv");
        boolean res = courseManager.saveToCsv("src/resources/courses/test-file", courseManager.getListOfCourses());
        assertTrue(res);
    }

    @Test
    public void getValidCourseIdAndSem() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        Course course = studentEnrolmentManager.getCourseManager().getValidCourseIdAndSem("COSC2440", "2021A");
        assertNotNull(course);
    }

    @Test
    public void getCoursesBySemester() {
        String semester = "2021A";
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        ArrayList<Course> coursesList = studentEnrolmentManager.getCourseManager().getCoursesBySemester(semester);
        assertNotNull(coursesList);
    }

    @Test
    public void setListOfCourses() {
        CourseManager courseManager = CourseManager.getInstance();
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(c1);
        courseManager.setListOfCourses(courseList);
        assertNotNull(courseManager.getListOfCourses());
    }
}