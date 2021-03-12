package ro.ase.csie.degree.firebase;

public enum Table {

    USERS ("users"),
    BUDGET ("budget"),
    CATEGORIES ("categories"),
    BALANCES ("balances"),
    TRANSACTIONS ("transactions");

    private final String table;

    Table(String table) {
        this.table = table;
    }
}
