package ro.ase.csie.degree.model;

public class Balance {

    private String name;
    private double available_amount;

    public Balance() {
    }

    public Balance(String name, double available_amount) {
        this.name = name;
        this.available_amount = available_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(double available_amount) {
        this.available_amount = available_amount;
    }
}
