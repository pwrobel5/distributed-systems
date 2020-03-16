package distributed.systems.rest.model;

import java.time.LocalDate;

public class ExchangeRateEntry {
    private LocalDate date;
    private double rate;

    public ExchangeRateEntry(LocalDate date, double rate) {
        this.date = date;
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


}
