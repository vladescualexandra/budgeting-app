package ro.ase.csie.degree.async;

public interface Callback<R> {
    void updateUI(R result);
}
