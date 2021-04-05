package RMIT;

import java.util.ArrayList;

public interface StudentEnrolmentManageable {
    public abstract void add();
    public abstract void update();
    public abstract StudentEnrolment delete(StudentEnrolment enrolment);
    public abstract void getOne();
    public abstract void getAll();
}
