package RMIT;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void strToDate() {
        Date newDate = Converter.StrToDate("2001-08-24");
        assertNotNull(newDate);
    }

    @Test
    public void dateToStr() {
        String newString = Converter.DateToStr(Converter.StrToDate("2001-08-24"));
        assertNotNull(newString);
    }
}