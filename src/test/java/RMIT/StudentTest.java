package RMIT;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void getId() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        assertEquals("s3812649", s1.getId());
    }

    @Test
    public void getName() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        assertEquals("Quoc Minh", s1.getName());
    }

    @Test
    public void getBirthDate() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        assertNotNull(s1.getBirthDate());
    }

    @Test
    public void getBirthDateString() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        assertNotNull("2001-08-24",Converter.DateToStr(s1.getBirthDate()));
    }

    @Test
    public void setId() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        s1.setId("s999");
        assertEquals("s999", s1.getId());
    }

    @Test
    public void setName() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        s1.setName("Minh Ng");
        assertEquals("Minh Ng", s1.getName());
    }

    @Test
    public void setBirthDate() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        s1.setBirthDate(Converter.StrToDate("2001-01-17"));
        assertEquals("2001-01-17", Converter.DateToStr(s1.getBirthDate()));
    }

    @Test
    public void compareTo() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        Student s2 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        assertEquals(1,s1.compareTo(s2));
    }

    @Test
    public void testToString() {
        Student s1 = new Student("s3812649", "Quoc Minh", Converter.StrToDate("2001-08-24"));
        assertEquals("ID: " + s1.getId() + " " + "NAME: " + s1.getName() + " " + "birthDate: " + s1.getBirthDateString(), s1.toString());
    }
}