package ro.ase.csie.degree.model;

public class Income extends Transaction{

    public Income() {
        super();
        this.category.setType(TransactionType.INCOME);
    }
}
