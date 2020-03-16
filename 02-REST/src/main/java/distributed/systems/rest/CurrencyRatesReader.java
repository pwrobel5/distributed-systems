package distributed.systems.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;
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

        return JSONParsingUtils.parseJSON(queryResult, "rates");
    }

    private static URI getForeignURI() {
        return UriBuilder.fromUri("http://data.fixer.io/api/").build();
    }
}
