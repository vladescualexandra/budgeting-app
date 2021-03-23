package ro.ase.csie.degree.firebase;

import ro.ase.csie.degree.util.DateConverter;

public enum DateDisplayType {
    DAY_MONTH_YEAR(0),
    MONTH_YEAR(1),
    YEAR(2),
    TOTAL(3);

    private final int type;

    DateDisplayType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static DateDisplayType getDateDisplayType(int position) {
        switch (position) {
            case 1:
                return DateDisplayType.MONTH_YEAR;
            case 2:
                return DateDisplayType.YEAR;
            case 3:
                return DateDisplayType.TOTAL;
            default:
                return DateDisplayType.DAY_MONTH_YEAR;
        }
    }

    public static String display(DateDisplayType type, int day, int month, int year) {
        switch (type) {
            case DAY_MONTH_YEAR:
                return DateConverter.toDisplayDate(day, month, year);
            case MONTH_YEAR:
                return DateConverter.toMonthYear(month, year);
            case YEAR:
                return DateConverter.toYear(year);
            default:
                return "";
        }
    }
}
