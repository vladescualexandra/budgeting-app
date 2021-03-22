package ro.ase.csie.degree.model;

public class Transfer extends Transaction {

    private Balance balance_to;

    public Transfer() {
        super();
        this.category.setType(TransactionType.TRANSFER);
    }

    public Balance getBalance_to() {
        return balance_to;
    }

    public void setBalance_to(Balance balance_to) {
        this.balance_to = balance_to;
    }
}
