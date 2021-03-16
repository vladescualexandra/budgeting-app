package ro.ase.csie.degree.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.model.Currency;

public class CurrencyJSONParser {

    public static List<Currency> getCurrencies() {
        List<Currency> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(CURRENCIES_JSON);
            for (int i=0; i<array.length();i++) {
                JSONObject currency = array.getJSONObject(i);

                String code = currency.getString(CURRENCY_CODE);
                String symbol = currency.getString(CURRENCY_SYMBOL);
                String name = currency.getString(CURRENCY_NAME);

                list.add(new Currency(i, code, symbol, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static final String CURRENCY_CODE = "cc";
    private static final String CURRENCY_SYMBOL = "symbol";
    private static final String CURRENCY_NAME = "name";

    private static String CURRENCIES_JSON = "[\n" +
            "  {\n" +
            "    \"cc\": \"AED\",\n" +
            "    \"symbol\": \"\\u062f.\\u0625;\",\n" +
            "    \"name\": \"UAE dirham\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"AFN\",\n" +
            "    \"symbol\": \"Afs\",\n" +
            "    \"name\": \"Afghan afghani\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ALL\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"name\": \"Albanian lek\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"AMD\",\n" +
            "    \"symbol\": \"AMD\",\n" +
            "    \"name\": \"Armenian dram\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ANG\",\n" +
            "    \"symbol\": \"NA\\u0192\",\n" +
            "    \"name\": \"Netherlands Antillean gulden\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"AOA\",\n" +
            "    \"symbol\": \"Kz\",\n" +
            "    \"name\": \"Angolan kwanza\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ARS\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Argentine peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"AUD\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Australian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"AWG\",\n" +
            "    \"symbol\": \"\\u0192\",\n" +
            "    \"name\": \"Aruban florin\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"AZN\",\n" +
            "    \"symbol\": \"AZN\",\n" +
            "    \"name\": \"Azerbaijani manat\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BAM\",\n" +
            "    \"symbol\": \"KM\",\n" +
            "    \"name\": \"Bosnia and Herzegovina konvertibilna marka\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BBD\",\n" +
            "    \"symbol\": \"Bds$\",\n" +
            "    \"name\": \"Barbadian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BDT\",\n" +
            "    \"symbol\": \"\\u09f3\",\n" +
            "    \"name\": \"Bangladeshi taka\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BGN\",\n" +
            "    \"symbol\": \"BGN\",\n" +
            "    \"name\": \"Bulgarian lev\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BHD\",\n" +
            "    \"symbol\": \".\\u062f.\\u0628\",\n" +
            "    \"name\": \"Bahraini dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BIF\",\n" +
            "    \"symbol\": \"FBu\",\n" +
            "    \"name\": \"Burundi franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BMD\",\n" +
            "    \"symbol\": \"BD$\",\n" +
            "    \"name\": \"Bermudian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BND\",\n" +
            "    \"symbol\": \"B$\",\n" +
            "    \"name\": \"Brunei dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BOB\",\n" +
            "    \"symbol\": \"Bs.\",\n" +
            "    \"name\": \"Bolivian boliviano\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BRL\",\n" +
            "    \"symbol\": \"R$\",\n" +
            "    \"name\": \"Brazilian real\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BSD\",\n" +
            "    \"symbol\": \"B$\",\n" +
            "    \"name\": \"Bahamian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BTN\",\n" +
            "    \"symbol\": \"Nu.\",\n" +
            "    \"name\": \"Bhutanese ngultrum\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BWP\",\n" +
            "    \"symbol\": \"P\",\n" +
            "    \"name\": \"Botswana pula\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BYR\",\n" +
            "    \"symbol\": \"Br\",\n" +
            "    \"name\": \"Belarusian ruble\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"BZD\",\n" +
            "    \"symbol\": \"BZ$\",\n" +
            "    \"name\": \"Belize dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CAD\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Canadian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CDF\",\n" +
            "    \"symbol\": \"F\",\n" +
            "    \"name\": \"Congolese franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CHF\",\n" +
            "    \"symbol\": \"Fr.\",\n" +
            "    \"name\": \"Swiss franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CLP\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Chilean peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CNY\",\n" +
            "    \"symbol\": \"\\u00a5\",\n" +
            "    \"name\": \"Chinese/Yuan renminbi\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"COP\",\n" +
            "    \"symbol\": \"Col$\",\n" +
            "    \"name\": \"Colombian peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CRC\",\n" +
            "    \"symbol\": \"\\u20a1\",\n" +
            "    \"name\": \"Costa Rican colon\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CUC\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Cuban peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CVE\",\n" +
            "    \"symbol\": \"Esc\",\n" +
            "    \"name\": \"Cape Verdean escudo\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"CZK\",\n" +
            "    \"symbol\": \"K\\u010d\",\n" +
            "    \"name\": \"Czech koruna\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"DJF\",\n" +
            "    \"symbol\": \"Fdj\",\n" +
            "    \"name\": \"Djiboutian franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"DKK\",\n" +
            "    \"symbol\": \"Kr\",\n" +
            "    \"name\": \"Danish krone\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"DOP\",\n" +
            "    \"symbol\": \"RD$\",\n" +
            "    \"name\": \"Dominican peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"DZD\",\n" +
            "    \"symbol\": \"\\u062f.\\u062c\",\n" +
            "    \"name\": \"Algerian dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"EEK\",\n" +
            "    \"symbol\": \"KR\",\n" +
            "    \"name\": \"Estonian kroon\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"EGP\",\n" +
            "    \"symbol\": \"\\u00a3\",\n" +
            "    \"name\": \"Egyptian pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ERN\",\n" +
            "    \"symbol\": \"Nfa\",\n" +
            "    \"name\": \"Eritrean nakfa\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ETB\",\n" +
            "    \"symbol\": \"Br\",\n" +
            "    \"name\": \"Ethiopian birr\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"EUR\",\n" +
            "    \"symbol\": \"\\u20ac\",\n" +
            "    \"name\": \"European Euro\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"FJD\",\n" +
            "    \"symbol\": \"FJ$\",\n" +
            "    \"name\": \"Fijian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"FKP\",\n" +
            "    \"symbol\": \"\\u00a3\",\n" +
            "    \"name\": \"Falkland Islands pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GBP\",\n" +
            "    \"symbol\": \"\\u00a3\",\n" +
            "    \"name\": \"British pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GEL\",\n" +
            "    \"symbol\": \"GEL\",\n" +
            "    \"name\": \"Georgian lari\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GHS\",\n" +
            "    \"symbol\": \"GH\\u20b5\",\n" +
            "    \"name\": \"Ghanaian cedi\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GIP\",\n" +
            "    \"symbol\": \"\\u00a3\",\n" +
            "    \"name\": \"Gibraltar pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GMD\",\n" +
            "    \"symbol\": \"D\",\n" +
            "    \"name\": \"Gambian dalasi\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GNF\",\n" +
            "    \"symbol\": \"FG\",\n" +
            "    \"name\": \"Guinean franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GQE\",\n" +
            "    \"symbol\": \"CFA\",\n" +
            "    \"name\": \"Central African CFA franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GTQ\",\n" +
            "    \"symbol\": \"Q\",\n" +
            "    \"name\": \"Guatemalan quetzal\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"GYD\",\n" +
            "    \"symbol\": \"GY$\",\n" +
            "    \"name\": \"Guyanese dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"HKD\",\n" +
            "    \"symbol\": \"HK$\",\n" +
            "    \"name\": \"Hong Kong dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"HNL\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"name\": \"Honduran lempira\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"HRK\",\n" +
            "    \"symbol\": \"kn\",\n" +
            "    \"name\": \"Croatian kuna\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"HTG\",\n" +
            "    \"symbol\": \"G\",\n" +
            "    \"name\": \"Haitian gourde\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"HUF\",\n" +
            "    \"symbol\": \"Ft\",\n" +
            "    \"name\": \"Hungarian forint\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"IDR\",\n" +
            "    \"symbol\": \"Rp\",\n" +
            "    \"name\": \"Indonesian rupiah\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ILS\",\n" +
            "    \"symbol\": \"\\u20aa\",\n" +
            "    \"name\": \"Israeli new sheqel\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"INR\",\n" +
            "    \"symbol\": \"\\u20B9\",\n" +
            "    \"name\": \"Indian rupee\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"IQD\",\n" +
            "    \"symbol\": \"\\u062f.\\u0639\",\n" +
            "    \"name\": \"Iraqi dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"IRR\",\n" +
            "    \"symbol\": \"IRR\",\n" +
            "    \"name\": \"Iranian rial\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ISK\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"name\": \"Icelandic kr\\u00f3na\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"JMD\",\n" +
            "    \"symbol\": \"J$\",\n" +
            "    \"name\": \"Jamaican dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"JOD\",\n" +
            "    \"symbol\": \"JOD\",\n" +
            "    \"name\": \"Jordanian dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"JPY\",\n" +
            "    \"symbol\": \"\\u00a5\",\n" +
            "    \"name\": \"Japanese yen\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KES\",\n" +
            "    \"symbol\": \"KSh\",\n" +
            "    \"name\": \"Kenyan shilling\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KGS\",\n" +
            "    \"symbol\": \"\\u0441\\u043e\\u043c\",\n" +
            "    \"name\": \"Kyrgyzstani som\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KHR\",\n" +
            "    \"symbol\": \"\\u17db\",\n" +
            "    \"name\": \"Cambodian riel\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KMF\",\n" +
            "    \"symbol\": \"KMF\",\n" +
            "    \"name\": \"Comorian franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KPW\",\n" +
            "    \"symbol\": \"W\",\n" +
            "    \"name\": \"North Korean won\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KRW\",\n" +
            "    \"symbol\": \"W\",\n" +
            "    \"name\": \"South Korean won\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KWD\",\n" +
            "    \"symbol\": \"KWD\",\n" +
            "    \"name\": \"Kuwaiti dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KYD\",\n" +
            "    \"symbol\": \"KY$\",\n" +
            "    \"name\": \"Cayman Islands dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"KZT\",\n" +
            "    \"symbol\": \"T\",\n" +
            "    \"name\": \"Kazakhstani tenge\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LAK\",\n" +
            "    \"symbol\": \"KN\",\n" +
            "    \"name\": \"Lao kip\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LBP\",\n" +
            "    \"symbol\": \"\\u00a3\",\n" +
            "    \"name\": \"Lebanese lira\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LKR\",\n" +
            "    \"symbol\": \"Rs\",\n" +
            "    \"name\": \"Sri Lankan rupee\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LRD\",\n" +
            "    \"symbol\": \"L$\",\n" +
            "    \"name\": \"Liberian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LSL\",\n" +
            "    \"symbol\": \"M\",\n" +
            "    \"name\": \"Lesotho loti\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LTL\",\n" +
            "    \"symbol\": \"Lt\",\n" +
            "    \"name\": \"Lithuanian litas\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LVL\",\n" +
            "    \"symbol\": \"Ls\",\n" +
            "    \"name\": \"Latvian lats\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"LYD\",\n" +
            "    \"symbol\": \"LD\",\n" +
            "    \"name\": \"Libyan dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MAD\",\n" +
            "    \"symbol\": \"MAD\",\n" +
            "    \"name\": \"Moroccan dirham\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MDL\",\n" +
            "    \"symbol\": \"MDL\",\n" +
            "    \"name\": \"Moldovan leu\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MGA\",\n" +
            "    \"symbol\": \"FMG\",\n" +
            "    \"name\": \"Malagasy ariary\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MKD\",\n" +
            "    \"symbol\": \"MKD\",\n" +
            "    \"name\": \"Macedonian denar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MMK\",\n" +
            "    \"symbol\": \"K\",\n" +
            "    \"name\": \"Myanma kyat\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MNT\",\n" +
            "    \"symbol\": \"\\u20ae\",\n" +
            "    \"name\": \"Mongolian tugrik\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MOP\",\n" +
            "    \"symbol\": \"P\",\n" +
            "    \"name\": \"Macanese pataca\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MRO\",\n" +
            "    \"symbol\": \"UM\",\n" +
            "    \"name\": \"Mauritanian ouguiya\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MUR\",\n" +
            "    \"symbol\": \"Rs\",\n" +
            "    \"name\": \"Mauritian rupee\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MVR\",\n" +
            "    \"symbol\": \"Rf\",\n" +
            "    \"name\": \"Maldivian rufiyaa\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MWK\",\n" +
            "    \"symbol\": \"MK\",\n" +
            "    \"name\": \"Malawian kwacha\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MXN\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Mexican peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MYR\",\n" +
            "    \"symbol\": \"RM\",\n" +
            "    \"name\": \"Malaysian ringgit\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"MZM\",\n" +
            "    \"symbol\": \"MTn\",\n" +
            "    \"name\": \"Mozambican metical\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"NAD\",\n" +
            "    \"symbol\": \"N$\",\n" +
            "    \"name\": \"Namibian dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"NGN\",\n" +
            "    \"symbol\": \"\\u20a6\",\n" +
            "    \"name\": \"Nigerian naira\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"NIO\",\n" +
            "    \"symbol\": \"C$\",\n" +
            "    \"name\": \"Nicaraguan c\\u00f3rdoba\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"NOK\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"name\": \"Norwegian krone\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"NPR\",\n" +
            "    \"symbol\": \"NRs\",\n" +
            "    \"name\": \"Nepalese rupee\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"NZD\",\n" +
            "    \"symbol\": \"NZ$\",\n" +
            "    \"name\": \"New Zealand dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"OMR\",\n" +
            "    \"symbol\": \"OMR\",\n" +
            "    \"name\": \"Omani rial\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PAB\",\n" +
            "    \"symbol\": \"B./\",\n" +
            "    \"name\": \"Panamanian balboa\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PEN\",\n" +
            "    \"symbol\": \"S/.\",\n" +
            "    \"name\": \"Peruvian nuevo sol\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PGK\",\n" +
            "    \"symbol\": \"K\",\n" +
            "    \"name\": \"Papua New Guinean kina\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PHP\",\n" +
            "    \"symbol\": \"\\u20b1\",\n" +
            "    \"name\": \"Philippine peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PKR\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"name\": \"Pakistani rupee\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PLN\",\n" +
            "    \"symbol\": \"z\\u0142\",\n" +
            "    \"name\": \"Polish zloty\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"PYG\",\n" +
            "    \"symbol\": \"\\u20b2\",\n" +
            "    \"name\": \"Paraguayan guarani\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"QAR\",\n" +
            "    \"symbol\": \"QR\",\n" +
            "    \"name\": \"Qatari riyal\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"RON\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"name\": \"Romanian leu\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"RSD\",\n" +
            "    \"symbol\": \"din.\",\n" +
            "    \"name\": \"Serbian dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"RUB\",\n" +
            "    \"symbol\": \"R\",\n" +
            "    \"name\": \"Russian ruble\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SAR\",\n" +
            "    \"symbol\": \"SR\",\n" +
            "    \"name\": \"Saudi riyal\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SBD\",\n" +
            "    \"symbol\": \"SI$\",\n" +
            "    \"name\": \"Solomon Islands dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SCR\",\n" +
            "    \"symbol\": \"SR\",\n" +
            "    \"name\": \"Seychellois rupee\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SDG\",\n" +
            "    \"symbol\": \"SDG\",\n" +
            "    \"name\": \"Sudanese pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SEK\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"name\": \"Swedish krona\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SGD\",\n" +
            "    \"symbol\": \"S$\",\n" +
            "    \"name\": \"Singapore dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SHP\",\n" +
            "    \"symbol\": \"\\u00a3\",\n" +
            "    \"name\": \"Saint Helena pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SLL\",\n" +
            "    \"symbol\": \"Le\",\n" +
            "    \"name\": \"Sierra Leonean leone\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SOS\",\n" +
            "    \"symbol\": \"Sh.\",\n" +
            "    \"name\": \"Somali shilling\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SRD\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"name\": \"Surinamese dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SYP\",\n" +
            "    \"symbol\": \"LS\",\n" +
            "    \"name\": \"Syrian pound\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"SZL\",\n" +
            "    \"symbol\": \"E\",\n" +
            "    \"name\": \"Swazi lilangeni\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"THB\",\n" +
            "    \"symbol\": \"\\u0e3f\",\n" +
            "    \"name\": \"Thai baht\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TJS\",\n" +
            "    \"symbol\": \"TJS\",\n" +
            "    \"name\": \"Tajikistani somoni\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TMT\",\n" +
            "    \"symbol\": \"m\",\n" +
            "    \"name\": \"Turkmen manat\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TND\",\n" +
            "    \"symbol\": \"DT\",\n" +
            "    \"name\": \"Tunisian dinar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TRY\",\n" +
            "    \"symbol\": \"TRY\",\n" +
            "    \"name\": \"Turkish new lira\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TTD\",\n" +
            "    \"symbol\": \"TT$\",\n" +
            "    \"name\": \"Trinidad and Tobago dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TWD\",\n" +
            "    \"symbol\": \"NT$\",\n" +
            "    \"name\": \"New Taiwan dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"TZS\",\n" +
            "    \"symbol\": \"TZS\",\n" +
            "    \"name\": \"Tanzanian shilling\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"UAH\",\n" +
            "    \"symbol\": \"UAH\",\n" +
            "    \"name\": \"Ukrainian hryvnia\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"UGX\",\n" +
            "    \"symbol\": \"USh\",\n" +
            "    \"name\": \"Ugandan shilling\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"USD\",\n" +
            "    \"symbol\": \"US$\",\n" +
            "    \"name\": \"United States dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"UYU\",\n" +
            "    \"symbol\": \"$U\",\n" +
            "    \"name\": \"Uruguayan peso\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"UZS\",\n" +
            "    \"symbol\": \"UZS\",\n" +
            "    \"name\": \"Uzbekistani som\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"VEB\",\n" +
            "    \"symbol\": \"Bs\",\n" +
            "    \"name\": \"Venezuelan bolivar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"VND\",\n" +
            "    \"symbol\": \"\\u20ab\",\n" +
            "    \"name\": \"Vietnamese dong\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"VUV\",\n" +
            "    \"symbol\": \"VT\",\n" +
            "    \"name\": \"Vanuatu vatu\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"WST\",\n" +
            "    \"symbol\": \"WS$\",\n" +
            "    \"name\": \"Samoan tala\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"XAF\",\n" +
            "    \"symbol\": \"CFA\",\n" +
            "    \"name\": \"Central African CFA franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"XCD\",\n" +
            "    \"symbol\": \"EC$\",\n" +
            "    \"name\": \"East Caribbean dollar\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"XDR\",\n" +
            "    \"symbol\": \"SDR\",\n" +
            "    \"name\": \"Special Drawing Rights\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"XOF\",\n" +
            "    \"symbol\": \"CFA\",\n" +
            "    \"name\": \"West African CFA franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"XPF\",\n" +
            "    \"symbol\": \"F\",\n" +
            "    \"name\": \"CFP franc\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"YER\",\n" +
            "    \"symbol\": \"YER\",\n" +
            "    \"name\": \"Yemeni rial\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ZAR\",\n" +
            "    \"symbol\": \"R\",\n" +
            "    \"name\": \"South African rand\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ZMK\",\n" +
            "    \"symbol\": \"ZK\",\n" +
            "    \"name\": \"Zambian kwacha\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"cc\": \"ZWR\",\n" +
            "    \"symbol\": \"Z$\",\n" +
            "    \"name\": \"Zimbabwean dollar\"\n" +
            "  }\n" +
            "]";
}
