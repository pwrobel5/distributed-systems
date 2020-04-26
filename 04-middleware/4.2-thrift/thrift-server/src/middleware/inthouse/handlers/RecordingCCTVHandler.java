package middleware.inthouse.handlers;

import middleware.inthouse.*;
import org.apache.thrift.TException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RecordingCCTVHandler extends CCTVHandler implements RecordingCCTV.Iface {
    private static final List<String> IMAGE_FILE_NAMES = new LinkedList<>(Arrays.asList("grapefruit.jpg", "apple.jpg", "potato.jpg"));

    public RecordingCCTVHandler(int id) {
        super(id);
    }

    @Override
    public List<Image> captureVideo(String dateFrom, String dateTo) throws InvalidDateFormat, NoData, TException {
        if (state == DeviceState.OFF) {
            throw new NoData("CCTV is off and has no recording!");
        } else if (state == DeviceState.POWER_SAVING) {
            throw new NoData("CCTV is in power saving mode and did not record any video!");
        }

        try {
            Date from = new SimpleDateFormat(DATE_FORMAT).parse(dateFrom);
            Date to = new SimpleDateFormat(DATE_FORMAT).parse(dateTo);
        } catch (ParseException e) {
            throw new InvalidDateFormat(DATE_FORMAT);
        }

        List<Image> video = new LinkedList<>();

        try {
            for (String fileName : IMAGE_FILE_NAMES) {
                byte[] imageData = getImageData(fileName);
                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                Date date = new Date();
                String dateString = dateFormat.format(date);
                video.add(new Image().setData(imageData).setDateTaken(dateString));
            }
        } catch (IOException e) {
            throw new NoData("Cannot read data from camera memory!");
        }

        return video;
    }
}
