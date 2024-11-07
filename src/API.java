import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;

public class API {

    JSONObject currencyDataObject;
    LinkedList<String> currencyNames = new LinkedList<>();

    public API(){
    }

    public void fetchApi() throws IOException, ParseException {
        String urlMain = "https://api.freecurrencyapi.com/";
        String apiKey = "your key";

        URL url = new URL(urlMain + apiKey);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        System.out.println(responseCode);

        if (responseCode == 200){
            StringBuilder currencyRatesJson = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()){
                currencyRatesJson.append(scanner.nextLine());
            }

            scanner.close();

            System.out.println(currencyRatesJson);

            JSONParser parser = new JSONParser();
            currencyDataObject = (JSONObject) parser.parse(String.valueOf(currencyRatesJson));
            currencyDataObject = (JSONObject) currencyDataObject.get("data");

        }
        else {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }

    //getting Currency Rate by handing a Currency Name
    public double getCurrencyRate(String currencyAbbrevation){
        if (currencyDataObject.get(currencyAbbrevation) instanceof Long){
            long x = (long) currencyDataObject.get(currencyAbbrevation);
            return x;
        }
        else {
            double x = (double) currencyDataObject.get(currencyAbbrevation);
            return x;
        }
    }

    //Adding Currency Names to a LinkedList
    public void addCurrencyNames(){
        for (Object keyName : currencyDataObject.keySet()){
            currencyNames.add((String) keyName);
        }
    }

    public LinkedList<String> getCurrencyNames(){
        return currencyNames;
    }


}
