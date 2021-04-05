package RMIT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
	// write your code here
        StudentManager studentManager = StudentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        if (studentManager != null && courseManager != null && studentEnrolmentManager != null) {
            // retrieve students and courses from csv file
            studentManager.addFromCsv("src/resources/students/students.csv");
            courseManager.addFromCsv("src/resources/courses/courses.csv");
            // Set students and courses database for studentEnrolmentManager
            studentEnrolmentManager.setStudentManager(studentManager);
            studentEnrolmentManager.setCourseManager(courseManager);
            // retrieve enrolments from csv file
            studentEnrolmentManager.addFromCsv("src/resources/student-enrolments/student-enrolments.csv");
        }
//        if (studentManager != null && courseManager != null) {
//            System.out.println("List of students");
//            for (var student: studentManager.getListOfStudents()) {
//                System.out.println(student.toString());
//            }
//            System.out.println("List of courses");
//            for (var course: courseManager.getListOfCourses()) {
//                System.out.println(course.toString());
//            }
//            System.out.println("List of enrolments");
//            for (var e: studentEnrolmentManager.getListOfEnrolments()) {
//                System.out.println(e.toString());
//            }
//        }
        studentEnrolmentManager.menu();
    }
}
