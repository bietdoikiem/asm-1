package RMIT;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Student student_1 = new Student("s3812649", "Nguyen Pham Quoc Minh", new Date(24, 8, 2001));
        Course course_1 = new Course("COSC2040", "Computer Vision", 3);
        Course course_2 = new Course("COSC2408", "Computer Engineering", 3);

        // array list of students and courses
        ArrayList<Student> studentList = new ArrayList<Student>();
        studentList.add(student_1);
        ArrayList<Course> courseList = new ArrayList<Course>();
        courseList.add(course_1);
        courseList.add(course_2);
        // Init manager
        StudentManager studentManager = StudentManager.getInstance();
        assert studentManager != null;
        studentManager.setListOfStudents(studentList);
        CourseManager courseManager = CourseManager.getInstance();
        assert courseManager != null;
        courseManager.setListOfCourses(courseList);
        StudentEnrolmentManager manager = StudentEnrolmentManager.getInstance();
        // add manager database
        assert manager != null;
        manager.setStudentManager(studentManager);
        manager.setCourseManager(courseManager);
//        manager.add();
//
//        System.out.println("List of enrolments: ");
//        for (StudentEnrolment enrolment: manager.getListOfEnrolments()) {
//            System.out.println(enrolment.toString());
//        }
//        manager.update();
        manager.menu();
    }
}
