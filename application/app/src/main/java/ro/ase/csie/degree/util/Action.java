package ro.ase.csie.degree.util;

import java.io.Serializable;

public enum Action implements Serializable {

    CREATE_NEW_TRANSACTION (0),
    CREATE_NEW_TEMPLATE (1),
    USE_TEMPLATE (2);

    private int id;

    Action(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static final String ACTION = "action";

}
