package ro.ase.csie.degree.model;

import ro.ase.csie.degree.R;

public class Transfer extends Transaction {

    private Balance balance_to;

    public Transfer(Transaction transaction) {
        super(transaction);
        setTransferCategory();
    }

    public Transfer() {
        super();
        setTransferCategory();
    }

    private void setTransferCategory() {
        this.category = new Category();
        this.category.setType(TransactionType.TRANSFER);
        this.category.setName(TransactionType.TRANSFER.toString());
        this.category.setColor(R.color.rally_blue);
    }

    public Balance getBalance_to() {
        return balance_to;
    }

    public void setBalance_to(Balance balance_to) {
        this.balance_to = balance_to;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "balance_to=" + balance_to +
                ", details='" + details + '\'' +
                ", category=" + category +
                ", balance_from=" + balance_from +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
