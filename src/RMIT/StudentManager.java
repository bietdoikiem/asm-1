package RMIT;

import java.util.ArrayList;

public class StudentManager {

    private static StudentManager single_instance;
    private ArrayList<Student> listOfStudents;
    private StudentManager() {
        this.listOfStudents = new ArrayList<Student>();
    }

    public static StudentManager getInstance() {
        if (single_instance == null) {
            single_instance = new StudentManager();
            return single_instance;
        }
        return null;
    }

    public void setListOfStudents(ArrayList<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public ArrayList<Student> getListOfStudents() {
        return listOfStudents;
    }
}
