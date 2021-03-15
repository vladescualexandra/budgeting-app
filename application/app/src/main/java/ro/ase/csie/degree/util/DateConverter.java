package ro.ase.csie.degree.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

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

    public static Date toDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toMonthYear(int month, int year) {
        return new DateFormatSymbols().getMonths()[month] + " " + year;
    }

    public static String toYear(int selectedYear) {
        return "Year " + selectedYear;
    }

    public static String toDisplayDate(int day, int month, int year) {
        return day + " " + new DateFormatSymbols().getMonths()[month] + " " + year;

    }
}
