package ro.ase.csie.degree.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    public static String format(int day, int month, int year) {
        return day + "/" + (month + 1) + "/" + year;
    }

    public static long toMillis(String date) {
        if (date != null) {
            try {
                Date parsedDate = sdf.parse(date);
                return parsedDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static String toString(Date date) {
        return sdf.format(date);
    }

    public static Date toDate(int day, int month, int year) {
        try {
            return sdf.parse(format(day, month, year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
