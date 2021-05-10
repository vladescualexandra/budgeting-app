package ro.ase.csie.degree.util;

public enum Themes {

    LIGHT("light"),
    NIGHT("night");

    private String theme;

    Themes(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.theme;
    }
}
