package distributed.systems.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyRatesReader {

    public static TreeMap<Object, Object> readRatesForDate(String baseSymbol, String foreignSymbols, LocalDate date) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getForeignURI());

        if(!baseSymbol.equalsIgnoreCase("EUR") && !foreignSymbols.contains(baseSymbol))
            foreignSymbols = foreignSymbols + "," + baseSymbol;

        String queryResult = webTarget.path(date.toString())
                .queryParam("access_key", AccessKey.ACCESS_KEY)
                .queryParam("symbols", foreignSymbols)
                .request(MediaType.APPLICATION_JSON).get(String.class);

        TreeMap<Object, Object> jsonResult = JSONParsingUtils.parseJSON(queryResult, "rates");
        if(jsonResult != null && !baseSymbol.equalsIgnoreCase("EUR")) includeNondefaultBase(jsonResult, baseSymbol);

        return jsonResult;
    }

    private static void includeNondefaultBase(TreeMap<Object, Object> rates, String baseSymbol) {
        Double divisionFactor = (Double) rates.get(baseSymbol);

        for(Map.Entry<Object, Object> entryObject : rates.entrySet()) {
            Double value = (Double) entryObject.getValue();
            rates.put(entryObject.getKey(), value / divisionFactor);
        }

        rates.remove(baseSymbol);
    }
    
    private static URI getForeignURI() {
        return UriBuilder.fromUri("http://data.fixer.io/api/").build();
    }
}
