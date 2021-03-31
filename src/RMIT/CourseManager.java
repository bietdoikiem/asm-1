package RMIT;

import java.util.ArrayList;

public class CourseManager {

    private static CourseManager single_instance = null;
    private ArrayList<Course> listOfCourses;

    private CourseManager() {
        this.listOfCourses = new ArrayList<Course>();
    }

    public static CourseManager getInstance() {
        if (single_instance == null) {
            single_instance = new CourseManager();
            return single_instance;
        }
        return null;
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    public void setListOfCourses(ArrayList<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }
}
