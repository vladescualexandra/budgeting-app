package ro.ase.csie.degree.model;

import java.io.Serializable;

public class Balance implements Serializable {

    private String id;
    private String name;
    private String user;
    private double available_amount;

    public Balance() {
    }

    public Balance(String id, String name, String user, double available_amount) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.available_amount = available_amount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
