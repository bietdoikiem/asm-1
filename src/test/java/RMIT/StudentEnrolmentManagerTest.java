package RMIT;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentEnrolmentManagerTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();


    @Before
    public void resetSingleton() {
        StudentEnrolmentManager.reset();
        StudentManager.reset();
        CourseManager.reset();
    }

    @Test
    public void getInstance() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        assertNotNull(studentEnrolmentManager);
    }

    @Test
    public void reset() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        // reset singleton objects
        StudentManager.reset();
        CourseManager.reset();
        StudentEnrolmentManager.reset();
        // try to re-assign
        StudentEnrolmentManager newStudentEnrolmentManager = StudentEnrolmentManager.getInstance();
        StudentManager newStudentManager = StudentManager.getInstance();
        CourseManager newCourseManager = CourseManager.getInstance();
        assertNotNull(newStudentEnrolmentManager);
        assertNotNull(newStudentManager);
        assertNotNull(newCourseManager);
    }

    @Test
    public void setStudentManager() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        // reset default student manager for testing
        StudentManager.reset();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        assertNotNull(studentEnrolmentManager.getStudentManager());
    }

    @Test
    public void getStudentManager() {
        StudentManager studentManager = StudentManager.getInstance();
        assertNotNull(studentManager);
    }

    @Test
    public void getCourseManager() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        studentEnrolmentManager.setCourseManager(courseManager);
        assertNotNull(courseManager);
    }

    @Test
    public void setCourseManager() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        studentEnrolmentManager.setCourseManager(courseManager);
        assertNotNull(studentEnrolmentManager.getCourseManager());
    }

    @Test
    public void addEnrolment() {
    }

    @Test
    public void getAll() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        ArrayList<StudentEnrolment> getList = studentEnrolmentManager.getAll();
        assertNotNull(getList);
    }


    @Test
    public void add() {
        // populated result
        systemInMock.provideLines("2","1", "s3812649", "2021a", "isys3414", ".", "return", "0");
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        // the default size of arraylist before we add one to the enrolment list
        int currentDefaultSize = 20;
        assert studentEnrolmentManager != null;
        studentEnrolmentManager.execute();
        // compare the old size with the new one after having added to the enrolment list
        assertNotEquals(currentDefaultSize, studentEnrolmentManager.getAll().size());
    }

    @Test
    public void update() {
        // input for add and delete method in update menu
        systemInMock.provideLines("2","2","s3812649","2021a", "1", "isys3414",".","a","s3812649", "2021a", "2", "isys3414", ".", "return", "0");
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        // default size before we manipulate the enrolment list
        int currentSize = 20;
        studentEnrolmentManager.execute();
        // compare the size after we add and delete the same course -> they must have equal size of array list
        assertEquals(currentSize, studentEnrolmentManager.getAll().size());
    }

    @Test
    public void delete() {
        systemInMock.provideLines("2");
        // init default managers
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        // set student and course managers to the manager of student's enrolment manually
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.populate();
        // init current size of default database
        int currentSize = 20;
        studentEnrolmentManager.delete(studentEnrolmentManager.getAll().get(0));
        assertNotEquals(20, studentEnrolmentManager.getAll().size());
    }

    @Test
    public void displayOne() {
        systemInMock.provideLines("1", "s3812649","2021a", "2", "return", "return");
        boolean res; // respond status
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = studentEnrolmentManager.displayOne();
        System.out.println(res);
        assertTrue(res);
    }

    @Test
    public void displayAll() {
        systemInMock.provideLines("2021a", "return");
        boolean res; // respond status
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = studentEnrolmentManager.displayAll();
        System.out.println(res);
        assertTrue(res);
    }

    @Test
    public void menu() {
        systemInMock.provideLines("0");
        boolean res;
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = studentEnrolmentManager.menu();
        assertTrue(res);
    }

    @Test
    public void displayCoursesByStudent() {
        systemInMock.provideLines("s3812649", "2021a", "2", "return");
        boolean res;
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = studentEnrolmentManager.displayCoursesByStudent();
        assertTrue(res);
    }

    @Test
    public void displayStudentsByCourse() {
        systemInMock.provideLines("2021a", "cosc2440", "2", "return");
        boolean res;
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = studentEnrolmentManager.displayStudentsByCourse();
        assertTrue(res);
    }

    @Test
    public void displayCoursesBySemester() {
        systemInMock.provideLines("2021a","2", "return");
        boolean res;
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = studentEnrolmentManager.displayCoursesBySemester();
        assertTrue(res);
    }

    @Test
    public void saveToCsv() {
        boolean res;
        String semester = "2021A";
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        res = courseManager.saveToCsv("src/resources/semester-course-report/test-file", studentEnrolmentManager.getCourseManager().getCoursesBySemester(semester));
        assertTrue(res);
    }

    @Test
    public void addFromCsv() {
        boolean res;
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentManager.addFromCsv("src/resources/students/default.csv");
        courseManager.addFromCsv("src/resources/courses/default.csv");
        res = studentEnrolmentManager.addFromCsv("src/resources/student-enrolments/default.csv");
        assertTrue(res);
    }

    @Test
    public void getCoursesByStudentId() {
        boolean res;
        String semester = "2021A";
        String sId = "s3812649";
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        ArrayList<Course> courseList = studentEnrolmentManager.getCoursesByStudentId(sId, semester);
        assertNotNull(courseList);
    }


    @Test
    public void getStudentsByCourseId() {
        String semester = "2021A";
        String cId = "COSC2440";
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        ArrayList<Student> studentList = studentEnrolmentManager.getStudentsByCourseId(cId, semester);
        assertNotNull(studentList);
    }




    @Test
    public void getOne() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        StudentEnrolment enrolment = studentEnrolmentManager.getOne(studentEnrolmentManager.getStudentManager().getValidStudentId("s3812649"), studentEnrolmentManager.getCourseManager().getValidCourseIdAndSem("COSC2440", "2021A"), "2021A");
        assertNotNull(enrolment);
    }

    @Test
    public void isDuplicatedEnrolment() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        Course course = new Course("COSC2440", "Software Architecture", 3,"2021A");
        Student student = new Student("s3812649", "Nguyen Pham Quoc Minh", Converter.StrToDate("2001-08-24"));
        StudentEnrolment enrol_1 = new StudentEnrolment(student, course, "2021A");
        studentEnrolmentManager.addEnrolment(enrol_1);
        assertTrue(studentEnrolmentManager.isDuplicatedEnrolment(enrol_1));

    }





    @Test
    public void isValidSemester() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        assertTrue(studentEnrolmentManager.isValidSemester("2021A"));
    }

    @Test
    public void stringInputValidator() {
        systemInMock.provideLines("s3812649");
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        String return_str = studentEnrolmentManager.stringInputValidator("student");
        assertEquals("s3812649", return_str);
    }

    @Test
    public void listInputValidator() {
        systemInMock.provideLines("cosc2440", ".");
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populateDefault();
        ArrayList<String> courseIdList = studentEnrolmentManager.listInputValidator("course", "2021A", StudentEnrolmentManager.Method.ADD);
        assertEquals(1, courseIdList.size());
    }


    @Test
    public void execute() {
        systemInMock.provideLines("2", "0");
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        boolean res = studentEnrolmentManager.execute();
        assertTrue(res);
    }

    @Test
    public void populateDefault() {
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.setStudentManager(studentManager);
        boolean res = studentEnrolmentManager.populateDefault();
        assertTrue(res);
    }

    @Test
    public void populate() {
        systemInMock.provideLines("2");
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populate();
        assertEquals(20, studentEnrolmentManager.getAll().size());
    }
}