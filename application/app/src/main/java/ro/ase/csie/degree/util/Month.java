package ro.ase.csie.degree.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.ase.csie.degree.util.language.Languages;

public class Month {

    int number;
    String en;
    String ro;

    public Month(int number, String en, String ro) {
        this.number = number;
        this.ro = ro;
        this.en = en;
    }

    public String getRo() {
        return ro;
    }

    public String getEn() {
        return en;
    }

    public static Month JANUARY = new Month(1, "January", "Ianuarie");
    public static Month FEBRUARY = new Month(2, "February", "Februarie");
    public static Month MARCH = new Month(3, "March", "Martie");
    public static Month APRIL = new Month(4, "April", "Aprilie");
    public static Month MAY = new Month(5, "May", "Mai");
    public static Month JUNE = new Month(6, "June", "Iunie");
    public static Month JULY = new Month(7, "July", "Iulie");
    public static Month AUGUST = new Month(8, "August", "August");
    public static Month SEPTEMBER = new Month(9, "September", "Septembrie");
    public static Month OCTOBER = new Month(10, "October", "Octombrie");
    public static Month NOVEMBER = new Month(11, "November", "Noiembrie");
    public static Month DECEMBER = new Month(12, "December", "December");

    private static List<Month> months = new ArrayList<>(Arrays.asList(JANUARY, FEBRUARY,
            MARCH, APRIL, MAY,
            JUNE, JULY, AUGUST,
            SEPTEMBER, OCTOBER, NOVEMBER,
            DECEMBER));

    public static String getMonth(String lang, int number) {
        Log.e("test", lang);
        if (lang.equals(Languages.ENGLISH)) {
            Log.e("test", "ENGLISH");
            return months.get(number).getEn();
        } else {
            Log.e("test", "ROMANIAN");
            return months.get(number).getRo();
        }
    }


}
