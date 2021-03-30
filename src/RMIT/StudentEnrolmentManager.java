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
            System.out.println("Duplicated Enrolments for course " + enrolment.getCourse().getId() +". Check again!");
            return null;
        }
        this.listOfEnrolments.add(enrolment);
        return enrolment;
    }

    public StudentEnrolment deleteEnrolment(StudentEnrolment enrolment) {
        this.listOfEnrolments.remove(enrolment);
        return enrolment;
    }

    public ArrayList<StudentEnrolment> getListOfEnrolments() {
        return listOfEnrolments;
    }

    public void setListOfEnrolments(ArrayList<StudentEnrolment> listOfEnrolments) {
        this.listOfEnrolments = listOfEnrolments;
    }

    public void add() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        while (true) {
            System.out.println("Add functionality called (Type 'return' to return to Main menu at any points). Please: ");
            String sId = stringInputValidator("student");
            Student student = getValidStudentId(sId);
//            System.out.println(sId);
            // -- cancel check --
            if (sId.equals("return")) {
                break;
            }
            String semester = stringInputValidator("semester");
            // -- cancel check --
            if (semester.equals("return".toUpperCase())) {
                break;
            }
//            System.out.println(semester);
            ArrayList<String> listOfCourseIds = listInputValidator("course");
            // -- cancel check --
            if (listOfCourseIds == null) {
                break;
            }
//            System.out.println(listOfCourseIds.toString());
            ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();
            for (String courseId : listOfCourseIds) {
                listOfCourseRefs.add(getValidCourseId(courseId));
            }

            // Start creating enrolments for the specified semester
            int addCounter = 0;
            for (Course course : listOfCourseRefs) {
                StudentEnrolment enrolment = this.addEnrolment(new StudentEnrolment(student, course, semester));
                if (enrolment != null) {
                    addCounter++;
                }
            }

            System.out.println("Enroll Student " + sId + " to " + addCounter + " course(s) successfully!");
            System.out.println("Do you want to continue to enroll for more students? (Press enter or type any keys to keep adding or type 'return' to return to Main menu)");
            navigateOption = sc.nextLine();
            if (navigateOption.equals("return")) {
                break;
            }
        }
    }


    public void update() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        // While loop for continuous 'Update' menu
        while (true) {
            System.out.println("Update functionality called (Type 'return' to return to Main Menu). Please: ");
            String sId = stringInputValidator("student");

            // -- cancel check -- //
            if (sId.equals("return")) {
                break;
            }
            // get student reference by id
            Student student = getValidStudentId(sId);

            // get semester's input
            String semester = stringInputValidator("semester");
            // -- cancel check --
            if (semester.equals("return".toUpperCase())) {
                break;
            }

            // get course by studentId and semester
            ArrayList<Course> listOfCourses = getCoursesByStudentId(sId, semester);

            // Display course info by columns and rows format
            System.out.println("Student " + sId + " - List of courses: ");
            System.out.println("===========================================================");
            System.out.printf("%-15s%-25s%-25s\n","ID","Name","Credits");
            for (Course course : listOfCourses) {
                System.out.printf("%-15s%-25s%-25s\n",course.getId(),course.getName(),course.getCredits());
            }

            // Update option (1 - Add, 2 - Delete)
            System.out.println("===========================================================");
            System.out.println("Do you want to delete or add new courses from the list? (Choose between 1 - 2)");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            navigateOption = sc.nextLine();
            // Checkin navigation option for available options
            switch (navigateOption) {
                case "1": { // add case
                    // do - while loop to check for any 'return' statement.
                    do {
                        ArrayList<String> addedCourseIds = listInputValidator("course", "update");
                        ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();

                        // Detect null array if the user calls 'return' statement for list of courses
                        if (addedCourseIds != null) {
                            for (String courseId : addedCourseIds) {
                                listOfCourseRefs.add(getValidCourseId(courseId));
                            }
                        } else {
                            // if list of course ID is null -> we sent return command to main menu
                            navigateOption = "RETURN";
                            break;
                        }
                        int addCounter = 0;
                        for (Course course : listOfCourseRefs) {
                            StudentEnrolment enrolment = this.addEnrolment(new StudentEnrolment(student, course, semester));
                            if (enrolment != null) {
                                addCounter++;
                            }
                        }
                        System.out.println("Enroll Student " + sId + " to " + addCounter + " more course(s) successfully!");
                        System.out.println("Do you want to continue to update enrolments for more students? (Press enter or type any keys to keep modifying or type 'return' to return to Main menu)");
                        navigateOption = sc.nextLine();
                        break;
                    } while (true);
                    break;
                }
                case "2": { // delete case
                    do {
                        ArrayList<String> deletedCourseIds = listInputValidator("course", "delete");
                        ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();

                        // Detect null array if the user calls 'return' statement for list of courses
                        if (deletedCourseIds != null) {
                            for (String courseId : deletedCourseIds) {
                                listOfCourseRefs.add(getValidCourseId(courseId));
                            }
                        } else {
                            // if list of course ID is null -> we sent return command to main menu
                            navigateOption = "RETURN";
                            break;
                        }
                        int deleteCounter = 0;
                        for (Course course : listOfCourseRefs) {
                            StudentEnrolment enrolment = this.deleteEnrolment(findEnrolment(student, course, semester));
                            if (enrolment != null) {
                                deleteCounter++;
                            }
                        }
                        System.out.println("Delete " + deleteCounter + " course(s) " + "for Student " + sId + "  successfully!");
                        System.out.println("Do you want to continue to update enrolments for more students? (Press enter or type any keys to keep modifying or type 'return' to return to Main menu)");
                        navigateOption = sc.nextLine();
                        break;
                    } while (true);
                    break;
                }
                default: {
                    break;
                }
            }
            // if return statement is made, return to main menu
            if (navigateOption.equals("return") || navigateOption.equals("RETURN")) {
                return;
            }
        }

    }

    public void delete() {

    }

    public void getOne() {

    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String input = "";
        while (true) {
            System.out.println("===== Menu to manage enrolments for students =====");
            System.out.println("1. Enroll student for a semester");
            System.out.println("2. Update enrollments for a student");
            System.out.println("3. Display all courses of a student in a specified semester");
            System.out.println("4. Display all students of a course in a specified semester");
            System.out.println("===== Choose the above options by typing its indicated number order (1 to 4) || Exit the console by typing 'exit' =====");
            System.out.print("Your choice: ");
            input = sc.nextLine();
            switch (input) {
                case "1": {
                    this.add();
                    break;
                }
                case "2": {
                    this.update();
                    break;
                }
                case "3": {
                    System.out.println("Print all courses for 1 student in 1 semester");
                    break;
                }
                case "4": {
                    System.out.println("Print all students of 1 course in 1 semester");
                    break;
                }
                case "exit": {
                    System.out.println("Console is shutting down...");
                    System.exit(0);
                }
                default:
                    break;
            }
        }
    }

    public ArrayList<Course> getCoursesByStudentId(String studentId) {
        ArrayList<Course> foundList = new ArrayList<Course>();

        for (StudentEnrolment enrolment : this.listOfEnrolments) {
            if (enrolment.getStudent().getId().equals(studentId)) {
                foundList.add(enrolment.getCourse());
            }
        }

        return foundList;
    }

    // Overload methods for specifying semester
    public ArrayList<Course> getCoursesByStudentId(String studentId, String semester) {
        ArrayList<Course> foundList = new ArrayList<Course>();

        for (StudentEnrolment enrolment : this.listOfEnrolments) {
            if (enrolment.getStudent().getId().equals(studentId) && enrolment.getSemester().equals(semester)) {
                foundList.add(enrolment.getCourse());
            }
        }

        return foundList;
    }




    public ArrayList<Student> getStudentsByCourseId(String courseId) {
        ArrayList<Student> foundList = new ArrayList<Student>();

        for (StudentEnrolment enrolment : this.listOfEnrolments) {
            if (enrolment.getCourse().getId().equals(courseId)) {
                foundList.add(enrolment.getStudent());
            }
        }

        return foundList;
    }

    // Overload methods for specifying semester
    public ArrayList<Student> getStudentsByCourseId(String courseId, String semester) {
        ArrayList<Student> foundList = new ArrayList<Student>();

        for (StudentEnrolment enrolment : this.listOfEnrolments) {
            if (enrolment.getCourse().getId().equals(courseId) && enrolment.getSemester().equals(semester)) {
                foundList.add(enrolment.getStudent());
            }
        }

        return foundList;
    }

    public StudentEnrolment findEnrolment(Student student, Course course, String semester) {
        StudentEnrolment searchEnrolment = new StudentEnrolment(student, course, semester);
        for (StudentEnrolment enrolment: this.listOfEnrolments) {
            if (enrolment.compareTo(searchEnrolment) == 1) {
                return enrolment;
            }
        }
        return null;
    }

    public ArrayList<StudentEnrolment> getAll() {
        return listOfEnrolments;
    }

    // --- Helper Functions -- //
    public boolean isDuplicatedEnrolment(StudentEnrolment enrolment) {
        for (StudentEnrolment enrol : listOfEnrolments) {
            if (enrolment.compareTo(enrol) == 1) {
                return true;
            }
        }
        return false;
    }

    public Student getValidStudentId(String studentId) {
        for (Student student : studentManager.getListOfStudents()) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public Course getValidCourseId(String courseId) {
        for (Course course : courseManager.getListOfCourses()) {
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
                    } else if (inputStr.equals("return")) {
                        System.out.println("Returning to main menu...");
                        return inputStr;
                    } else {
                        System.out.println("Input is not validated in the student database. (Try again or cancelling request by typing 'return'):");
                    }
                }
            }
            case "semester": {
                System.out.println("Enter semester: ");
                while (true) {
                    inputStr = input.nextLine().toUpperCase();
                    if (isValidSemester(inputStr)) {
                        return inputStr;
                    } else if (inputStr.equals("RETURN")) {
                        System.out.println("Returning to main menu...");
                        return inputStr;
                    } else {
                        System.out.println("Wrong format of semester (Example: 2021A, 2021B, etc.). (Try again or cancelling request by typing 'return')");
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<String> listInputValidator(String type) {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        ArrayList<String> listOfCourseIds = new ArrayList<String>();
        System.out.printf("Enter list of %s Id that the students would like to enroll (Type in '.' to stop adding more courses or 'return' to cancel request): %n",type);
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
                } else if (inputStr.equals("RETURN")) {
                    System.out.println("Returning to main menu...");
                    return null;
                } else {
                    System.out.println("Input is not validated in the course database. (Try again or stop by typing '.')");
                    inputStr = input.nextLine().toUpperCase();
                }
            }
            if (inputStr.equals(".")) {
                break;
            }
        }
        return listOfCourseIds;
    }

    // methods that specify type including the methods to be called (ex: update, delete, etc.)
    public ArrayList<String> listInputValidator(String type, String method) {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        ArrayList<String> listOfCourseIds = new ArrayList<String>();
        System.out.printf("Enter list of %s Id that the students would like to %s (Type in '.' to stop adding more courses or 'return' to cancel request): %n",type, method);
        while (input.hasNext()) {
            inputStr = input.nextLine().toUpperCase();
            while (true) {
                // if the input is valid -> break the inner loop and continue checking the next input statement
                if (getValidCourseId(inputStr) != null) {
                    listOfCourseIds.add(inputStr);
                    break;
                } else if (inputStr.equals(".")) { // if user has cancel adding process by typing '.', break the inner loop
                    if (listOfCourseIds.size() == 0) { // if there aren't any courses being added to the list -> throw errors and ask again for at least 1 course
                        System.out.printf("Error ! There is no %s yet to be added. Please add one ! %n", type);
                        inputStr = input.nextLine().toUpperCase();
                    } else { // if there has been at least 1 course before returning to main menu -> break the inner loop
                        break;
                    }
                } else if (inputStr.equals("RETURN")) { // if user asks to return to main menu -> end method by returning null
                    System.out.println("Returning to main menu...");
                    return null;
                } else { // validate input in the database
                    System.out.printf("%s is not validated in the %s database. (Try again or stop by typing '.') %n", type.substring(0, 1).toUpperCase() + type.substring(1), type);
                    inputStr = input.nextLine().toUpperCase();
                }
            }
            // if user has cancel adding process by typing '.', break the outer loop
            if (inputStr.equals(".")) {
                break;
            }
        }
        return listOfCourseIds;
    }
}
