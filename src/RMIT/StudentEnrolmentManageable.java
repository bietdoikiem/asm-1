package RMIT;

import java.util.ArrayList;

public interface StudentEnrolmentManageable {
    public abstract void add();
    public abstract void update();
    public abstract void delete();
    public abstract void getOne();
    public abstract ArrayList<StudentEnrolment> getAll();

}
