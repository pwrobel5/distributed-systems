package distributed.systems.rest;

import distributed.systems.rest.model.ExchangeRateEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

@Path("/currency_rates")
public class CurrencyRates {
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getCurrencyRateHTML(@QueryParam("baseSymbol") String baseSymbol,
                                      @QueryParam("foreignSymbols") String foreignSymbols,
                                      @QueryParam("dateFrom") String dateFromString,
                                      @QueryParam("dateTo") String dateToString) {

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<html><head><meta charset=\"UTF-8\"><title>Exchange rates</title></head><body>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateFrom, dateTo;
        try {
            dateFrom = LocalDate.parse(dateFromString, formatter);

            if(dateToString.trim().isEmpty()) dateTo = dateFrom;
            else dateTo = LocalDate.parse(dateToString, formatter);
        } catch(DateTimeParseException e) {
            resultBuilder.append("<h1>Incorrect date format!</h1></body></html>");
            return resultBuilder.toString();
        }

        if(baseSymbol.trim().isEmpty()) baseSymbol = "EUR";
        resultBuilder.append("<b>Base: </b>").append(baseSymbol).append("<br>");
        foreignSymbols = foreignSymbols.trim();
        TreeMap<String, List<ExchangeRateEntry>> currenciesExchangeRates = new TreeMap<>();

        for(LocalDate date = dateFrom; date.compareTo(dateTo) <= 0; date = date.plusDays(1)) {
            TreeMap<Object, Object> currentDateRates = CurrencyRatesReader.readRatesForDate(baseSymbol, foreignSymbols, date);
            processData(date, currentDateRates, currenciesExchangeRates);
        }

        resultBuilder.append("<table style=\"width:50%\">");
        for(String currencySymbol : currenciesExchangeRates.keySet()) {
            resultBuilder.append(makeCurrencyTable(currenciesExchangeRates.get(currencySymbol), currencySymbol));
        }
        resultBuilder.append("</table>");

        resultBuilder.append("</body></html>");
        return resultBuilder.toString();
    }

    private static void processData(LocalDate date, TreeMap<Object, Object> currentDateRates, TreeMap<String, List<ExchangeRateEntry>> currenciesExchangeRates) {
        for (Object keyObject : currentDateRates.keySet()) {
            String currencySymbol = (String) keyObject;

            if(!currenciesExchangeRates.containsKey(currencySymbol)) {
                currenciesExchangeRates.put(currencySymbol, new LinkedList<>());
            }

            Double exchangeRate = (Double) currentDateRates.get(keyObject);
            currenciesExchangeRates.get(currencySymbol).add(new ExchangeRateEntry(date, exchangeRate));
        }
    }

    private static String makeCurrencyTable(List<ExchangeRateEntry> ratesList, String currencySymbol) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<tr><th colspan=\"2\">").append(currencySymbol).append("</th></tr>");

        for(ExchangeRateEntry exchangeRateEntry : ratesList) {
            resultBuilder.append("<tr><td>").append(exchangeRateEntry.getDate().toString()).append("</td>");
            resultBuilder.append("<td>").append(exchangeRateEntry.getRate()).append("</td></tr>");
        }

        return resultBuilder.toString();
    }
}
