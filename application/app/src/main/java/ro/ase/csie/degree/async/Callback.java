package ro.ase.csie.degree.async;

import org.json.JSONException;

public interface Callback<R> {
    void updateUI(R result);
}
