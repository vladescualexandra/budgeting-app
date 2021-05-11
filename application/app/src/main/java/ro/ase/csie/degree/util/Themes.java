package ro.ase.csie.degree.util;

public enum Themes {

    LIGHT("Light"),
    NIGHT("Night");

    private String theme;

    Themes(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.theme;
    }
}
