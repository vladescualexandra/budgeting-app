package ro.ase.csie.degree.network;

import java.util.concurrent.Callable;

import ro.ase.csie.degree.async.AsyncTaskRunner;
import ro.ase.csie.degree.async.Callback;

public class CurrenciesManager {

    private static final String API_KEY = "de2df0aa08b9a8e38beb";
    private static final String CURRENCIES_API = "https://free.currconv.com/api/v7/currencies?apiKey=" + API_KEY;
    private static final String SERVER = "https://free.currconv.com";

    private static final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private static String buildURL(String from, String to) {
        return SERVER
                + "/api/v7/convert?q="
                + from
                + "_"
                + to
                + "&compact=ultra&apiKey="
                + API_KEY;
    }

    public static void getConversionFromURL(String from, String to, Callback<String> conversionCallback) {
        Callable<String> asyncOperation = new HttpManager(buildURL(from, to));
        asyncTaskRunner.executeAsync(asyncOperation, conversionCallback);
    }

    public static void getCurrenciesFromURL(Callback<String> currenciesCallback) {
        Callable<String> asyncOperation = new HttpManager(CURRENCIES_API);
        asyncTaskRunner.executeAsync(asyncOperation, currenciesCallback);
    }

}
