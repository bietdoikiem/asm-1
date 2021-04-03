package RMIT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void addCoursesFromCsv(String route) {
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
                this.listOfCourses.add(new Course(attributeMap.get("id"), attributeMap.get("name"), Integer.parseInt(attributeMap.get("credits"))));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IndexOutOfBoundsException ignored) {
        }
    }

    public void setListOfCourses(ArrayList<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }
}
