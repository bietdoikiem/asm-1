package RMIT;

public class Course {
    private String id;
    private String name;
    private int credits;

    public Course() {
        super();
    }

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
        this.credits = 0;
    }

    // ### GETTERS ### //
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    // ### SETTERS #### //
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
