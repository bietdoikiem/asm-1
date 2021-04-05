package RMIT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseManager implements CSVManageable<Course> {

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

    public void addFromCsv(String route) {
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

    public void saveToCsv(String filename, ArrayList<Course> list) {
        try {
            FileWriter csvWriter = new FileWriter(String.format("src/resources/student-report/%s_courses.csv", filename));
            csvWriter.append("id");
            csvWriter.append(",");
            csvWriter.append("name");
            csvWriter.append(",");
            csvWriter.append("credits");
            csvWriter.append("\n");

            for (Course course: list) {
                csvWriter.append(course.getId());
                csvWriter.append(",");
                csvWriter.append(course.getName());
                csvWriter.append(",");
                csvWriter.append(String.format("%d",course.getCredits()));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Save report to file successfully!");
        } catch (IOException e) {
            System.out.println("Cannot save report to file! Please check again");
            e.printStackTrace();
        }
    }

    public void setListOfCourses(ArrayList<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }
}
