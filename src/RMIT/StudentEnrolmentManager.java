package RMIT;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentEnrolmentManager implements StudentEnrolmentManageable {
    // Implement Singleton Pattern to Manager class
    private static StudentEnrolmentManager single_instance = null;
    private ArrayList<StudentEnrolment> listOfEnrolments;
    private StudentManager studentManager;
    private CourseManager courseManager;


    private StudentEnrolmentManager() {
        this.listOfEnrolments = new ArrayList<StudentEnrolment>();
    }

    public static StudentEnrolmentManager getInstance() {
        if (single_instance == null) {
            single_instance = new StudentEnrolmentManager();
            return single_instance;
        }
        return null;
    }

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }
    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }
    public StudentEnrolment addEnrolment(StudentEnrolment enrolment) {
        if (isDuplicatedEnrolment(enrolment)) {
            System.out.println("Duplicated Enrolments. Check again!");
            return null;
        }
        this.listOfEnrolments.add(enrolment);
        System.out.println("Enrolled students successfully!");
        return enrolment;
    }

    public ArrayList<StudentEnrolment> getListOfEnrolments() {
        return listOfEnrolments;
    }

    public void setListOfEnrolments(ArrayList<StudentEnrolment> listOfEnrolments) {
        this.listOfEnrolments = listOfEnrolments;
    }

    public void add() {
        System.out.println("Add functionality called (Type 'exit' to cancel the process at any points). Please: ");
        String sId = stringInputValidator("student");
        Student student = getValidStudentId(sId);
        System.out.println(sId);

        String semester = stringInputValidator("semester");
        // debug
        System.out.println(semester);

        ArrayList<String> listOfCourseIds = courseInputValidator();
        //debug
        System.out.println(listOfCourseIds.toString());
        ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();
        for (String courseId: listOfCourseIds) {
            listOfCourseRefs.add(getValidCourseId(courseId));
        }

        // Start creating enrolments for the specified semester
        for (Course course: listOfCourseRefs) {
            this.listOfEnrolments.add(new StudentEnrolment(student, course, semester));
        }
    }


    public void update() {

    }

    public void delete() {

    }

    public void getOne() {

    }

    public ArrayList<Course> getCoursesByStudentId(String studentId) {
        ArrayList<Course> foundList = new ArrayList<Course>();

        for (StudentEnrolment enrolment: this.listOfEnrolments) {
            if (enrolment.getStudent().getId().equals(studentId)) {
                foundList.add(enrolment.getCourse());
            }
        }

        return foundList;
    }

    public ArrayList<Student> getStudentsByCourseId(String courseId) {
        ArrayList<Student> foundList = new ArrayList<Student>();

        for (StudentEnrolment enrolment: this.listOfEnrolments) {
            if (enrolment.getCourse().getId().equals(courseId)) {
                foundList.add(enrolment.getStudent());
            }
        }

        return foundList;
    }

    public ArrayList<StudentEnrolment> getAll() {
        return listOfEnrolments;
    }

    // --- Helper Functions -- //
    public boolean isDuplicatedEnrolment(StudentEnrolment enrolment) {
        for (StudentEnrolment enrol: listOfEnrolments) {
            if (enrolment.compareTo(enrol) == 1) {
                return true;
            }
        }
        return false;
    }

    public Student getValidStudentId(String studentId) {
        for (Student student: studentManager.getListOfStudents()) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public Course getValidCourseId(String courseId) {
        for (Course course: courseManager.getListOfCourses()) {
            if (course.getId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    public boolean isValidSemester(String semester) {
        Pattern pattern = Pattern.compile("(\\d+)[A-Z]$");
        Matcher matcher = pattern.matcher(semester);
        return matcher.find();
    }

    public String stringInputValidator(String option) {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        switch (option) {
            case "student": {
                System.out.println("Enter student's ID: ");
                while (true) {
                    inputStr = input.nextLine();
                    if (getValidStudentId(inputStr) != null) {
                        return inputStr;
                    } else if (inputStr.equals("cancel")) {
                        System.out.println("Cancelling the add request...");
                        System.exit(0);
                    } else {
                        System.out.println("Input is not validated in the student database. (Try again or cancelling add request by typing 'cancel'):");
                    }
                }
            }

            case "semester": {
                System.out.println("Enter semester: ");
                while (true) {
                    inputStr = input.nextLine().toUpperCase();
                    if (isValidSemester(inputStr)) {
                        return inputStr;
                    } else if (inputStr.equals("CANCEL")) {
                        System.out.println("Cancelling the add request...");
                        System.exit(0);
                    } else {
                        System.out.println("Wrong format of semester (Example: 2021A, 2021B, etc.). (Try again or cancelling add request by typing 'cancel')");
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<String> courseInputValidator() {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        ArrayList<String> listOfCourseIds = new ArrayList<String>();
        System.out.println("Enter list of courses' Id that the students would like to enroll (Type in '.' to stop adding more courses or 'cancel' to cancel add request): ");
        while (input.hasNext()) {
            inputStr = input.nextLine().toUpperCase();
            while (true) {
                if (getValidCourseId(inputStr) != null) {
                    listOfCourseIds.add(inputStr);
                    break;
                } else if (inputStr.equals(".")) {
                    if (listOfCourseIds.size() == 0) {
                        System.out.println("Error ! There is no course yet to be added. Please add one !");
                        inputStr = input.nextLine().toUpperCase();
                    } else {
                        break;
                    }
                }
                else if (inputStr.equals("CANCEL")){
                    System.out.println("Cancelling the add request...");
                    System.exit(0);
                }
                else {
                    System.out.println("Input is not validated in the course database. (Try again or stop by typing '.')");
                    inputStr = input.nextLine().toUpperCase();
                }
            }
            if(inputStr.equals(".")) {
                break;
            }
        }
        return listOfCourseIds;
    }
}
