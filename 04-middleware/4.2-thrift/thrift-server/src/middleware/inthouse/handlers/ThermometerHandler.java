package middleware.inthouse.handlers;

import middleware.inthouse.DeviceState;
import middleware.inthouse.InvalidDateFormat;
import middleware.inthouse.NoData;
import middleware.inthouse.Thermometer;
import org.apache.thrift.TException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ThermometerHandler extends DeviceHandler implements Thermometer.Iface {
    private Random random;
    private static final double MIN_TEMPERATURE = -5.0;
    private static final double MAX_TEMPERATURE = 30.0;
    private static final String DATE_FORMAT = "HH:mm";
    private static final int TEMPERATURE_LIST_LENGTH = 5;
    private static final double AMPLITUDE_MAX = 5.0;
    private static final double AMPLITUDE_MIN = -5.0;

    public ThermometerHandler(int id) {
        super(id);
        random = new Random();
    }

    @Override
    public double getCurrentTemperature() throws TException {
        logger.info("Client trying to read temperature");

        if (state == DeviceState.OFF) {
            logger.warning("Device is off, client can't read temperature");
            throw new NoData("Thermometer is off!");
        }

        return (MAX_TEMPERATURE - MIN_TEMPERATURE) * random.nextDouble() + MIN_TEMPERATURE;
    }

    @Override
    public List<Double> getTemperatureHistory(String dateFrom, String dateTo) throws InvalidDateFormat, NoData, TException {
        logger.info("Client trying to read temperature history");

        if (state == DeviceState.OFF) {
            logger.warning("Device is off, client can't read temperature history");
            throw new NoData("Thermometer if off!");
        } else if (state == DeviceState.POWER_SAVING) {
            logger.warning("Device is in power saving mode, client can't read temperature history");
            throw new NoData("Thermometer is in power saving mode and did not collect data!");
        }

        try {
            Date from = new SimpleDateFormat(DATE_FORMAT).parse(dateFrom);
            Date to = new SimpleDateFormat(DATE_FORMAT).parse(dateTo);
        } catch (ParseException e) {
            logger.warning("Wrong input date format, temperature history will not be sent");
            throw new InvalidDateFormat(DATE_FORMAT);
        }

        List<Double> result = new LinkedList<>();
        double baseTemperature = (MAX_TEMPERATURE - MIN_TEMPERATURE) * random.nextDouble() + MIN_TEMPERATURE;
        for (int i = 0; i < TEMPERATURE_LIST_LENGTH; i++) {
            result.add(baseTemperature + AMPLITUDE_MIN + (AMPLITUDE_MAX - AMPLITUDE_MIN) * random.nextDouble());
        }

        return result;
    }
}
