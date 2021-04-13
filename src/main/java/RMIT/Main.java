package RMIT;



public class Main {

    public static void main(String[] args) {
        // Execute the program
        StudentEnrolmentManager studentEnrolmentManager = StudentEnrolmentManager.getInstance();
        assert studentEnrolmentManager != null;
        studentEnrolmentManager.execute();
    }
}
