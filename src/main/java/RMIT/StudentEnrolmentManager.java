package RMIT;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentEnrolmentManager implements StudentEnrolmentManageable {
    // Implement Singleton Pattern to Manager class
    private static StudentEnrolmentManager single_instance = null;
    private ArrayList<StudentEnrolment> listOfEnrolments;
    private StudentManager studentManager;
    private CourseManager courseManager;
    public enum Method {
        ADD,
        UPDATE,
        DELETE;
    }


    private StudentEnrolmentManager() {
        this.listOfEnrolments = new ArrayList<>();
    }

    public static StudentEnrolmentManager getInstance() {
        if (single_instance == null) {
            single_instance = new StudentEnrolmentManager();
            return single_instance;
        }
        return null;
    }

    public static void reset() {
        single_instance = null;
    }

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public StudentManager getStudentManager() {
        return this.studentManager;
    }

    public CourseManager getCourseManager() {
        return this.courseManager;
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

    public ArrayList<StudentEnrolment> getAll() {
        return listOfEnrolments;
    }

    // =============== MENU METHODS =============== //
    public void add() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        while (true) {
            System.out.println("Add functionality called (Type 'return' to return to Main menu at any points). Please: ");
            String sId = stringInputValidator("student");
            Student student = this.studentManager.getValidStudentId(sId);
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
            ArrayList<String> listOfCourseIds = listInputValidator("course", semester, Method.ADD);
            // -- cancel check --
            if (listOfCourseIds == null) {
                break;
            }
//            System.out.println(listOfCourseIds.toString());
            ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();
            for (String courseId : listOfCourseIds) {
                listOfCourseRefs.add(this.courseManager.getValidCourseIdAndSem(courseId, semester));
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
            System.out.println("Do you want to continue to enroll for more students? (Press enter OR type any keys to keep adding OR type 'return' to return to Main menu)");
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
            Student student = this.studentManager.getValidStudentId(sId);

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
            System.out.println("======================================================================================");
            System.out.printf("%-15s%-50s%-50s\n","ID","Name","Credits");
            for (Course course : listOfCourses) {
                System.out.printf("%-15s%-50s%-50s\n",course.getId(),course.getName(),course.getCredits());
            }

            // Update option (1 - Add, 2 - Delete)
            System.out.println("======================================================================================");
            System.out.println("Do you want to delete or add new courses from the list? (Choose between 1 - 2 or type 'return' to return to Main Menu)");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            navigateOption = sc.nextLine();
            // Checkin navigation option for available options
            switch (navigateOption) {
                case "1": { // add case
                    // do - while loop to check for any 'return' statement.
                    do {
                        ArrayList<String> addedCourseIds = listInputValidator("course", semester,Method.UPDATE);
                        ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();

                        // Detect null array if the user calls 'return' statement for list of courses
                        if (addedCourseIds != null) {
                            for (String courseId : addedCourseIds) {
                                listOfCourseRefs.add(this.courseManager.getValidCourseIdAndSem(courseId, semester));
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
                        System.out.println("Do you want to continue to update enrolments for more students? (Press enter or type any keys to keep modifying or type 'return' to return to Main Menu)");
                        navigateOption = sc.nextLine();
                        break;
                    } while (true);
                    break;
                }
                case "2": { // delete case
                    do {
                        ArrayList<String> deletedCourseIds = listInputValidator("course", semester,Method.DELETE);
                        ArrayList<Course> listOfCourseRefs = new ArrayList<Course>();

                        // Detect null array if the user calls 'return' statement for list of courses
                        if (deletedCourseIds != null) {
                            for (String courseId : deletedCourseIds) {
                                listOfCourseRefs.add(this.courseManager.getValidCourseIdAndSem(courseId, semester));
                            }
                        } else {
                            // if list of course ID is null -> we sent return command to main menu
                            navigateOption = "RETURN";
                            break;
                        }
                        int deleteCounter = 0;
                        for (Course course : listOfCourseRefs) {
                            boolean respond = this.delete(getOne(student, course, semester));
                            if (respond) {
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

    public boolean delete(StudentEnrolment enrolment) {
        return this.listOfEnrolments.remove(enrolment);
    }

    // ----- DISPLAY METHODS ----- //
    public boolean displayOne() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        boolean res = false;
        while (true) {
            System.out.println("1. Display all courses of a student in one semester");
            System.out.println("2. Display all students of a course in one semester");
            System.out.println("3. Display all courses offer in one semester");
            System.out.println("Choose options (1 to 3). Or type 'return' to Main Menu");
            navigateOption = sc.nextLine();
            switch(navigateOption) {
                case "1": {
                    res = this.displayCoursesByStudent();
                    break;
                }
                case "2": {
                    res = this.displayStudentsByCourse();
                    break;
                }
                case "3": {
                    res = this.displayCoursesBySemester();
                    break;
                }
                default: {
                    break;
                }
            }
            if (navigateOption.equalsIgnoreCase("return")) {
                return res;
            }
        }
    }

    public boolean displayAll() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        while (true) {
            // -- cancel check --
            String semester = stringInputValidator("semester");
            if (semester.equalsIgnoreCase("return")) {
                break;
            }
            System.out.println("====================================================================================================================================================================================");
            System.out.printf("%-15s%-30s%-30s%-30s%-50s%-15s%-50s\n", "SID", "SName", "SBirthDate", "CID", "CName", "CCredits", "Semester");
            for (StudentEnrolment enrolment : this.getAll()) {
                if (enrolment.getSemester().equalsIgnoreCase(semester))
                System.out.printf("%-15s%-30s%-30s%-30s%-50s%-15s%-50s\n", enrolment.getStudent().getId(), enrolment.getStudent().getName(), enrolment.getStudent().getBirthDateString(),
                        enrolment.getCourse().getId(), enrolment.getCourse().getName(), enrolment.getCourse().getCredits(), semester);
            }
            // Update option (1 - Add, 2 - Delete)
            System.out.println("====================================================================================================================================================================================");
            System.out.println("Do you want to continue to view more? Type any to continue or type 'return' to return to the Menu");
            navigateOption = sc.nextLine();
            if (navigateOption.equalsIgnoreCase("return")) {
                return true;
            }
        }
        return false;
    }



    public boolean displayCoursesByStudent() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        boolean res;
        while (true) {
            String sId = stringInputValidator("student");
            // -- cancel check --
            if (sId.equals("return")) {
                break;
            }
            String semester = stringInputValidator("semester");
            // -- cancel check --
            if (semester.equals("return".toUpperCase())) {
                break;
            }
            // get list of courses by semester
            ArrayList<Course> listOfCourses = getCoursesByStudentId(sId, semester);

            System.out.printf("Student %s - List of courses by semester %s: %n", sId, semester);
            System.out.printf("%-15s%-50s%-30s%-50s\n","ID","Name","Credits","Semester");
            for (Course course : listOfCourses) {
                System.out.printf("%-15s%-50s%-30s%-50s\n",course.getId(),course.getName(),course.getCredits(), course.getSemester());
            }
            System.out.println("Do you want to save these result to CSV file ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            navigateOption = sc.nextLine();
            if (navigateOption.equals("1")) {
                res = courseManager.saveToCsv(String.format("src/resources/student-report/%s_%s", sId, semester), listOfCourses);
                if (res) {
                    System.out.println("Save reports to file successfully");
                }
            }
            // end-of-function option
            System.out.println("Do you want to continue to view more courses of a student? (Press enter or type any keys to keep adding or type 'return' to return to report menu)");
            navigateOption = sc.nextLine();
            if (navigateOption.equalsIgnoreCase("return")) {
                System.out.println(navigateOption);
                return true;
            }
        }
        return false;
    }

    public boolean displayStudentsByCourse() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        boolean res; // respond msg status
        while (true) {
            String semester = stringInputValidator("semester");
            // -- cancel check --
            if (semester.equals("return".toUpperCase())) {
                break;
            }

            String cId = courseInputValidator(semester);
            System.out.println(cId);
            // -- cancel check --
            if (cId.equals("return")) {
                break;
            }
            // get list of courses by semester
            ArrayList<Student> listOfStudents = getStudentsByCourseId(cId, semester);

            System.out.printf("Course %s - List of students by semester %s: %n", cId, semester);
            System.out.printf("%-15s%-50s%-50s\n","ID","Name","DateOfBirth");
            for (Student student: listOfStudents) {
                System.out.printf("%-15s%-50s%-50s\n",student.getId(),student.getName(),student.getBirthDateString());
            }

            System.out.println("Do you want to save these result to CSV file ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            navigateOption = sc.nextLine();
            if (navigateOption.equals("1")) {
                res = studentManager.saveToCsv(String.format("src/resources/course-report/%s_%s", cId, semester), listOfStudents);
                if (res) {
                    System.out.println("Save reports to file successfully");
                }
            }
            // end-of-function option
            System.out.println("Do you want to continue to view more courses by student? (Press enter or type any keys to keep adding or type 'return' to return to report menu)");
            navigateOption = sc.nextLine();
            if (navigateOption.equalsIgnoreCase("return")) {
                return true;
            }
        }
        return false;
    }

    public boolean displayCoursesBySemester() {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        boolean res;
        while (true) {
            String semester = stringInputValidator("semester");
            // -- cancel check --
            if (semester.equals("return")) {
                break;
            }
            // get list of courses by semester
            ArrayList<Course> listOfCourses = this.getCourseManager().getCoursesBySemester(semester);

            System.out.printf("List of courses by semester %s: %n", semester);
            System.out.printf("%-15s%-50s%-50s\n", "ID", "Name", "Credits");
            for (Course course: listOfCourses) {
                System.out.printf("%-15s%-50s%-50s\n",course.getId(),course.getName(),course.getCredits());
            }

            System.out.println("Do you want to save these result to CSV file ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            navigateOption = sc.nextLine();
            if (navigateOption.equals("1")) {
                res = courseManager.saveToCsv(String.format("src/resources/semester-course-report/%s_courses",semester), listOfCourses);
                if (res) {
                    System.out.println("Save reports to file successfully");
                }
            }
            // end-of-function option
            System.out.println("Do you want to continue to view more courses by student? (Press enter or type any keys to keep adding or type 'return' to return to report menu)");
            navigateOption = sc.nextLine();
            if (navigateOption.equalsIgnoreCase("return")) {
                return true;
            }
            System.out.println(navigateOption);
        }
        return false;
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String input = "";
        boolean res;
        while (true) {
            System.out.println("===== Menu to manage enrolments for students =====");
            System.out.println("0. Exit console application");
            System.out.println("1. Enroll student for a semester");
            System.out.println("2. Update enrollments for a student");
            System.out.println("3. Display Reports By Semester");
            System.out.println("4. Display All Enrolments By Semester");
            System.out.println("===== Choose the above options by typing its indicated number order (0 to 4)");
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
                    res = this.displayOne();
                    if(!res) {
                        System.out.println("Cancelling display functions");
                    }
                    break;
                }
                case "4": {
                    res = this.displayAll();
                    if(!res) {
                        System.out.println("Cancelling display functions");
                    }
                    break;
                }
                case "0": {
                    System.out.println("Console is shutting down...");
                    break;
                }
                default:
                    break;
            }
            if (input.equalsIgnoreCase("0")) {
                return;
            }
        }
    }

    // ----- CSV Manipulation Methods ----- //
    public boolean addFromCsv(String route) {
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(route));
            // skip first header line
            row = csvReader.readLine();
            String[] header = row.split(",");
            HashMap<String, String> attributeMap = new HashMap<String, String>();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                for (int i = 0; i < header.length; i++) {
                    attributeMap.put(header[i], data[i]);
                }
                this.listOfEnrolments.add(new StudentEnrolment(this.studentManager.getValidStudentId(attributeMap.get("studentId")), this.courseManager.getValidCourseIdAndSem(attributeMap.get("courseId"), attributeMap.get("semester")), attributeMap.get("semester")));
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found! Please type 'return' to main menu and try again");
            return false;
        } catch(IndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //----------------------- QUERY METHODS -----------------------//
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


    // Overload methods for specifying semester
    public ArrayList<Student> getStudentsByCourseId(String courseId, String semester) {
        ArrayList<Student> foundList = new ArrayList<Student>();

        for (StudentEnrolment enrolment : this.listOfEnrolments) {
            if (enrolment.getCourse().getId().equalsIgnoreCase(courseId) && enrolment.getSemester().equalsIgnoreCase(semester)) {
                foundList.add(enrolment.getStudent());
            }
        }

        return foundList;
    }

    // get one enrolment specified by student, course and semester
    public StudentEnrolment getOne(Student student, Course course, String semester) {
        StudentEnrolment searchEnrolment = new StudentEnrolment(student, course, semester);
        for (StudentEnrolment enrolment: this.listOfEnrolments) {
            if (enrolment.compareTo(searchEnrolment) == 1) {
                return enrolment;
            }
        }
        return null;
    }



    public boolean isValidSemester(String semester) {
        Pattern pattern = Pattern.compile("(\\d+)[A-Z]$");
        Matcher matcher = pattern.matcher(semester);
        return matcher.find();
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

    public String courseInputValidator(String semester) {
        Scanner sc = new Scanner(System.in);
        String navigateOption = "";
        while (true) {
            System.out.println("Enter course's ID: ");
            while (true) {
                navigateOption = sc.nextLine().toUpperCase();
                if (this.courseManager.getValidCourseIdAndSem(navigateOption, semester) != null) {
                    return navigateOption;
                } else if (navigateOption.equalsIgnoreCase("return")) {
                    System.out.println("Returning to menu...");
                    return navigateOption;
                } else {
                    System.out.println("Input is not validated in the course database. (Try again or cancelling request by typing 'return'):");
                }
            }
        }
    }

    public String stringInputValidator(String option) {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        switch (option) {
            case "student": {
                System.out.println("Enter student's ID: ");
                while (true) {
                    inputStr = input.nextLine();
                    if (this.studentManager.getValidStudentId(inputStr) != null) {
                        return inputStr;
                    } else if (inputStr.equals("return")) {
                        System.out.println("Returning to menu...");
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
                        System.out.println("Returning to menu...");
                        return inputStr;
                    } else {
                        System.out.println("Wrong format of semester (Example: 2021A, 2021B, etc.). (Try again or cancelling request by typing 'return')");
                    }
                }
            }
        }
        return null;
    }


    // methods that specify type including the methods to be called (ex: update, delete, etc.)
    public ArrayList<String> listInputValidator(String type, String semester,Method method) {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        ArrayList<String> listOfCourseIds = new ArrayList<String>();
        System.out.printf("Enter list of %s Id that the students would like to %s (Type in '.' to stop adding more courses or 'return' to cancel request): %n",type, method);
        while (input.hasNext()) {
            inputStr = input.nextLine().toUpperCase();
            while (true) {
                // if the input is valid -> break the inner loop and continue checking the next input statement
                if (this.courseManager.getValidCourseIdAndSem(inputStr, semester) != null) {
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
                    System.out.println("Returning to menu...");
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


    public boolean execute() {
        // attach studentManager and courseManager to the database if haven't set those
        if (this.studentManager == null && this.courseManager == null) {
            StudentManager studentManager = StudentManager.getInstance();
            CourseManager courseManager = CourseManager.getInstance();
            this.setStudentManager(studentManager);
            this.setCourseManager(courseManager);
        }
        this.populate();
        this.menu();
        return true;
    }
    public boolean populateDefault() {
        if (this.studentManager != null && this.courseManager != null) {
            this.studentManager.addFromCsv("src/resources/students/default.csv");
            this.courseManager.addFromCsv("src/resources/courses/default.csv");
            this.addFromCsv("src/resources/student-enrolments/default.csv");
            return true;
        }
        return false;
    }

    public void populate() {
        Scanner sc = new Scanner(System.in);
        String navigateOption;
        boolean res;
        while (true) {
            System.out.println("Welcome to Student Enrolment Manager!");
            System.out.println("Do you want to use your own database or use default one? (Choose 1 or 2)");
            System.out.println("1. Initialize Database");
            System.out.println("2. Default Database");
            navigateOption = sc.nextLine();
            // check option
            while (true) {
                if (navigateOption.equals("1")) {
                    // for Students
                    System.out.println("Custom Database! Type 'return' to choose again.");
                    System.out.println("Please type file's name (include .csv) for student's database: (Note: Please put the CSV file in 'src/resources/students/' route");
                    String studentFileName = sc.nextLine();
                    if (studentFileName.equalsIgnoreCase("return")) {
                        break;
                    }
                    res = studentManager.addFromCsv(String.format("src/resources/students/%s", studentFileName));
                    if (!res) {
                        break;
                    }
                    // for Courses
                    System.out.println("Please type file's name (include .csv) for course's database: (Note: Please put the CSV file in 'src/resources/courses/' route");
                    String courseFileName = sc.nextLine();
                    if (courseFileName.equalsIgnoreCase("return")) {
                        break;
                    }
                    res = courseManager.addFromCsv(String.format("src/resources/courses/%s", courseFileName));
                    if (!res) {
                        break;
                    }
                    // for Enrolments
                    System.out.println("Please type file's name (include .csv) for enrolment's database: (Note: Please put the CSV file in 'src/resources/student-enrolments/' route");
                    String enrolmentFileName = sc.nextLine();
                    if (enrolmentFileName.equalsIgnoreCase("return")) {
                        break;
                    }
                    res = this.addFromCsv(String.format("src/resources/student-enrolments/%s", enrolmentFileName));
                    if (!res) {
                        break;
                    }
                    System.out.println("Load custom database successfully!");
                    navigateOption = "success";
                    break;
                } else if (navigateOption.equals("2")) {
                    res = this.populateDefault();
                    if (res) {
                        System.out.println("Load default database successfully!");
                        navigateOption = "success";
                    }
                    break;
                }
                else {
                    System.out.println("Wrong choice. Please check again!");
                    break;
                }
            }
            if (navigateOption.equals("success")) {
                break;
            }
        }
    }
}
