package RMIT;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    public static void reset() {
        single_instance = null;
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    public boolean addFromCsv(String route) {
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(route));
            // skip first header line
            row = csvReader.readLine();
            String[] header = row.split(",");
            HashMap<String, String> attributeMap = new HashMap<>();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                for (int i = 0; i < header.length; i++) {
                    attributeMap.put(header[i], data[i]);
                }
                this.listOfCourses.add(new Course(attributeMap.get("id"), attributeMap.get("name"), Integer.parseInt(attributeMap.get("credits")), attributeMap.get("semester")));
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found! Please check again.");
            return false;
        } catch(IndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveToCsv(String filename, ArrayList<Course> list) {
        try {
            FileWriter csvWriter = new FileWriter(String.format("%s.csv", filename));
            csvWriter.append("id");
            csvWriter.append(",");
            csvWriter.append("name");
            csvWriter.append(",");
            csvWriter.append("credits");
            csvWriter.append(",");
            csvWriter.append("semester");
            csvWriter.append("\n");

            for (Course course: list) {
                csvWriter.append(course.getId());
                csvWriter.append(",");
                csvWriter.append(course.getName());
                csvWriter.append(",");
                csvWriter.append(String.format("%d",course.getCredits()));
                csvWriter.append(",");
                csvWriter.append(course.getSemester());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot save report to file! Please check again");
            return false;
        }
        return true;
    }

    public Course getValidCourseIdAndSem(String courseId, String semester) {
        for (Course course : this.getListOfCourses()) {
            if (course.getId().equalsIgnoreCase(courseId) && course.getSemester().equalsIgnoreCase(semester)) {
                return course;
            }
        }
        return null;
    }

    public ArrayList<Course> getCoursesBySemester(String semester) {
        HashSet<Course> foundSet = new HashSet<Course>();

        for (Course course: this.getListOfCourses()) {
            if (course.getSemester().equals(semester)) {
                foundSet.add(course);
            }
        }
        return new ArrayList<>(foundSet);
    }
    public void setListOfCourses(ArrayList<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }
}
