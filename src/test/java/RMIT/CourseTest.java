package RMIT;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void getSemester() {
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        assertEquals("2021A", c1.getSemester());
    }

    @Test
    public void setSemester() {
        Course c1 = new Course();
        c1.setSemester("2021A");
        assertEquals("2021A", c1.getSemester());
    }

    @Test
    public void getId() {
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        assertEquals("COSC2440", c1.getId());
    }

    @Test
    public void getName() {
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        assertEquals("SADI", c1.getName());
    }

    @Test
    public void getCredits() {
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        assertEquals(3, c1.getCredits());
    }

    @Test
    public void setId() {
        Course c1 = new Course();
        c1.setId("COSC2440");
        assertEquals("COSC2440", c1.getId());
    }

    @Test
    public void setName() {
        Course c1 = new Course();
        c1.setName("SADI");
        assertEquals("SADI", c1.getName());
    }

    @Test
    public void setCredits() {
        Course c1 = new Course();
        c1.setCredits(3);
        assertEquals(3, c1.getCredits());
    }

    @Test
    public void compareTo() {
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        Course c2 = new Course("COSC2440", "SADI", 3,"2021A");
        assertEquals(1, c1.compareTo(c2));
    }

    @Test
    public void testToString() {
        Course c1 = new Course("COSC2440", "SADI", 3,"2021A");
        String testStr = "Course ID: " + c1.getId() + " || " + "Course Name: " + c1.getName() +
                " || " + "Course Credits: " + c1.getCredits();
        assertEquals(testStr, c1.toString());
    }
}