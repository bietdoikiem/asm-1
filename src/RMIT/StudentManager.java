package RMIT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentManager implements CSVManageable<Student> {

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
                this.listOfStudents.add(new Student(attributeMap.get("id"), attributeMap.get("name"), Converter.StrToDate(attributeMap.get("birthDate"))));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IndexOutOfBoundsException ignored) {
        }
    }

    public void saveToCsv(String filename, ArrayList<Student> list) {
        try {
            FileWriter csvWriter = new FileWriter(String.format("src/resources/course-report/%s_students.csv", filename));
            csvWriter.append("ID");
            csvWriter.append(",");
            csvWriter.append("Name");
            csvWriter.append(",");
            csvWriter.append("birthDate");
            csvWriter.append("\n");

            for (Student student: list) {
                csvWriter.append(student.getId());
                csvWriter.append(",");
                csvWriter.append(student.getName());
                csvWriter.append(",");
                csvWriter.append(String.format("%s",student.getBirthDateString()));
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

    public ArrayList<Student> getListOfStudents() {
        return listOfStudents;
    }
}
