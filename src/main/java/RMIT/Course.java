package RMIT;

public class Course implements Comparable<Course> {
    private String id;
    private String name;
    private int credits;
    private String semester;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Course() {
        super();
    }

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
        this.credits = 0;
    }

    public Course(String id, String name, int credits, String semester) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.semester = semester;
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

    public int compareTo(Course course) {
        if (this.id.equals(course.id) && this.name.equals(course.name) && this.credits == course.credits) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Course ID: " + this.getId() + " || " + "Course Name: " + this.getName() +
                " || " + "Course Credits: " + this.getCredits();
    }
}
