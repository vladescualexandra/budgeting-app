package ro.ase.csie.degree.firebase;

public enum DateDisplayType {
    DAY_MONTH_YEAR (0),
    MONTH_YEAR (1),
    YEAR(2),
    TOTAL (3);

    private int type;

    DateDisplayType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
