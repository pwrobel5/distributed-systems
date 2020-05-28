package distributed.systems.rest;

import distributed.systems.rest.model.ExchangeRateEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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

            if (dateToString.trim().isEmpty()) dateTo = dateFrom;
            else dateTo = LocalDate.parse(dateToString, formatter);
        } catch (DateTimeParseException e) {
            resultBuilder.append("<h1>Incorrect date format!</h1></body></html>");
            return resultBuilder.toString();
        }

        if (baseSymbol == null || baseSymbol.trim().isEmpty()) baseSymbol = "EUR";
        else baseSymbol = baseSymbol.toUpperCase();

        resultBuilder.append("<b>Base: </b>").append(baseSymbol).append("<br>");
        foreignSymbols = (foreignSymbols == null) ? "" : foreignSymbols.trim();
        TreeMap<String, LinkedList<ExchangeRateEntry>> currenciesExchangeRates = new TreeMap<>();

        for (LocalDate date = dateFrom; date.compareTo(dateTo) <= 0; date = date.plusDays(1)) {
            TreeMap<Object, Object> currentDateRates = CurrencyRatesReader.readRatesForDate(baseSymbol, foreignSymbols, date);
            processData(date, currentDateRates, currenciesExchangeRates);
        }

        resultBuilder.append("<table style=\"width:50%\">");

        StringBuilder historyTableBuilder = new StringBuilder();
        for (String currencySymbol : currenciesExchangeRates.keySet()) {
            List<ExchangeRateEntry> currentValue = currenciesExchangeRates.get(currencySymbol);
            historyTableBuilder.append(makeCurrencyTable(currentValue, currencySymbol));
            Collections.sort(currentValue);
        }

        resultBuilder.append(makeStatisticTable(currenciesExchangeRates));

        resultBuilder.append(historyTableBuilder.toString());
        resultBuilder.append("</table>");

        resultBuilder.append("</body></html>");
        return resultBuilder.toString();
    }

    private static void processData(LocalDate date, TreeMap<Object, Object> currentDateRates, TreeMap<String, LinkedList<ExchangeRateEntry>> currenciesExchangeRates) {
        for (Object keyObject : currentDateRates.keySet()) {
            String currencySymbol = (String) keyObject;

            if (!currenciesExchangeRates.containsKey(currencySymbol)) {
                currenciesExchangeRates.put(currencySymbol, new LinkedList<>());
            }

            double exchangeRate = Double.parseDouble(currentDateRates.get(keyObject).toString());
            currenciesExchangeRates.get(currencySymbol).add(new ExchangeRateEntry(date, exchangeRate));
        }
    }

    private static String makeCurrencyTable(List<ExchangeRateEntry> ratesList, String currencySymbol) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<tr><th colspan=\"6\">").append(currencySymbol).append("</th></tr>");

        for (ExchangeRateEntry exchangeRateEntry : ratesList) {
            resultBuilder.append("<tr><td colspan=\"3\" align=\"center\">").append(exchangeRateEntry.getDate().toString()).append("</td>");
            resultBuilder.append("<td colspan=\"3\" align=\"center\">")
                    .append(String.format(Locale.US, "%10.6f", exchangeRateEntry.getRate())).append("</td></tr>");
        }

        return resultBuilder.toString();
    }

    private static String makeStatisticTable(TreeMap<String, LinkedList<ExchangeRateEntry>> currenciesExchangeRates) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<tr><th colspan=\"6\">Statistics</th></tr>");
        resultBuilder.append("<tr><th>Currency</th><th>Minimal rate</th><th>Minimal rate date</th>");
        resultBuilder.append("<th>Maximal rate</th><th>Maximal rate date</th>");
        resultBuilder.append("<th>Average rate</th></tr>");

        for (String currencySymbol : currenciesExchangeRates.keySet()) {
            resultBuilder.append("<tr><td align=\"center\">").append(currencySymbol).append("</td>");
            LinkedList<ExchangeRateEntry> currencyList = currenciesExchangeRates.get(currencySymbol);

            ExchangeRateEntry minimalRateEntry = currencyList.getFirst();
            resultBuilder.append("<td align=\"center\">")
                    .append(String.format(Locale.US, "%10.6f", minimalRateEntry.getRate()))
                    .append("</td><td align=\"center\">")
                    .append(minimalRateEntry.getDate().toString()).append("</td>");

            ExchangeRateEntry maximalRateEntry = currencyList.getLast();
            resultBuilder.append("<td align=\"center\">")
                    .append(String.format(Locale.US, "%10.6f", maximalRateEntry.getRate()))
                    .append("</td><td align=\"center\">")
                    .append(maximalRateEntry.getDate().toString()).append("</td>");

            resultBuilder.append("<td align=\"center\">").append(String.format(Locale.US, "%10.6f", calculateAverageRate(currencyList))).append("</td></tr>");
        }

        return resultBuilder.toString();
    }

    private static double calculateAverageRate(LinkedList<ExchangeRateEntry> rates) {
        double sum = 0.0;

        for (ExchangeRateEntry entry : rates) {
            sum += entry.getRate();
        }

        return sum / rates.size();
    }
}
