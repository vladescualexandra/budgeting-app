package ro.ase.csie.degree.async;

import org.json.JSONException;

public class HandlerMessage<R> implements Runnable {

    private final Callback<R> mainThreadOperation;
    private final R result;


    public HandlerMessage(Callback<R> mainThreadOperation, R result) {
        this.mainThreadOperation = mainThreadOperation;
        this.result = result;
    }

    @Override
    public void run() {
        mainThreadOperation.updateUI(result);
    }
}
