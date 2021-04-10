package ro.ase.csie.degree.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ro.ase.csie.degree.firebase.DateDisplayType;

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

    public static int[] toPieces(Date date) {
        String[] piecesString = toString(date).split("/");
        int[] pieces = new int[3];
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = Integer.parseInt(piecesString[i]);
        }
        return pieces;
    }

    public static boolean filterMonthYear(Date date, Date filter) {
        int[] pieces = toPieces(date);
        int[] filters = toPieces(filter);

        for (int i=1; i<pieces.length; i++) {
            if (pieces[i] != filters[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean filter(DateDisplayType type, Date date, Date filter) {
        int[] pieces = toPieces(date);
        int[] filters = toPieces(filter);

        for (int i=type.getType(); i<pieces.length; i++) {
            if (pieces[i] != filters[i]) {
                return false;
            }
        }
        return true;
    }
}
