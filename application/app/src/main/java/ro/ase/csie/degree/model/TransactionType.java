package ro.ase.csie.degree.model;

public enum TransactionType {

    EXPENSE ("expense"),
    INCOME ("income"),
    TRANSFER ("transfer");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
