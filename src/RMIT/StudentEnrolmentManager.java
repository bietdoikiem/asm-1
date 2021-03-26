package RMIT;

import java.util.ArrayList;

public interface StudentEnrolmentManager {
    public abstract boolean add(Student student);
    public abstract boolean update(Student student);
    public abstract boolean getOne(String studentID);
    public abstract ArrayList<Student> getAll();

}
