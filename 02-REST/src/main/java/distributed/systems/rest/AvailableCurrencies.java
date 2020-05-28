package distributed.systems.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.TreeMap;

@Path("/available")
public class AvailableCurrencies {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAvailableCurrenciesHTML() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getForeignURI());

        String queryResult = webTarget.path("symbols").queryParam("access_key", AccessKey.ACCESS_KEY)
                .request(MediaType.APPLICATION_JSON).get(String.class);

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<html><head><meta charset=\"UTF-8\"><title>Available currencies</title></head><body>");

        TreeMap<Object, Object> availableCurrencies = JSONParsingUtils.parseJSON(queryResult, "symbols");
        if (availableCurrencies == null) {
            resultBuilder.append("<h1>Error with reading data from remote server</h1>");
        } else {
            resultBuilder.append("<table style=\"width:50%\">");
            resultBuilder.append("<tr><th>Symbol</th><th>Description</th></tr>");

            for (Object symbol : availableCurrencies.keySet()) {
                resultBuilder.append("<tr><td align=\"center\">");
                resultBuilder.append(symbol.toString());
                resultBuilder.append("</td><td align=\"center\">");
                resultBuilder.append(availableCurrencies.get(symbol).toString());
                resultBuilder.append("</td></tr>");
            }

            resultBuilder.append("</table>");
        }

        resultBuilder.append("</body></html>");
        return resultBuilder.toString();
    }

    private static URI getForeignURI() {
        return UriBuilder.fromUri("http://data.fixer.io/api/").build();
    }
}
