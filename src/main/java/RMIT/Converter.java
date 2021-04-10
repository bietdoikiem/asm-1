package RMIT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
    public static Date StrToDate(String input) {
        // listing the available date format

        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy/MM/dd");
        // create array of format
        SimpleDateFormat[] formats = new SimpleDateFormat[2];
        formats[0] = formatter1;
        formats[1] =formatter2;
        Date data = null;
        Date data2 =null;
        // Iterate through Date format, match input with the right one and convert it to Date
        for (SimpleDateFormat format : formats) {
            try {
                data2 = data;
                data = format.parse(input);
            } catch (ParseException exception) {
                data = data2;
            }
        }
        return data;
    }
    public static String DateToStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // format Date into year-month-day string format
        return formatter.format(date);
    }
}
