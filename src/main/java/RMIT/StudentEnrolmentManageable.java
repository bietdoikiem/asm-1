package RMIT;

import java.util.ArrayList;

public interface StudentEnrolmentManageable {
    public abstract void add();
    public abstract boolean delete(StudentEnrolment enrolment);
    public abstract void update();
    public abstract StudentEnrolment getOne(Student student, Course course, String semester);
    public abstract ArrayList<StudentEnrolment> getAll();
    public abstract boolean displayOne();
    public abstract boolean displayAll();
}
