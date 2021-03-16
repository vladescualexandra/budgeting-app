package ro.ase.csie.degree.model;


import java.io.Serializable;

public class Currency implements Serializable {

    private String code;
    private String symbol;
    private String name;


    public Currency() {

    }

    public Currency(String code, String symbol, String name) {
        this.code = code;
        this.symbol = symbol;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.symbol + "\t (" + this.name + ")";
    }
}
