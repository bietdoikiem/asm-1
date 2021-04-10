package RMIT;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Execute the program
//        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
//        assert studentEnrolmentManager != null;
//        studentEnrolmentManager.execute();

        // test
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        CourseManager courseManager = CourseManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentEnrolmentManager.setStudentManager(studentManager);
        studentEnrolmentManager.setCourseManager(courseManager);
        studentEnrolmentManager.populate();
    }
}
