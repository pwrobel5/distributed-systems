package middleware.inthouse.handlers;

import middleware.inthouse.CCTV;
import middleware.inthouse.DeviceState;
import middleware.inthouse.Image;
import middleware.inthouse.NoData;
import org.apache.thrift.TException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CCTVHandler extends DeviceHandler implements CCTV.Iface {
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public CCTVHandler(int id) {
        super(id);
    }

    protected byte[] getImageData(String fileName) throws IOException {
        File imageFile = new File(fileName);
        BufferedImage bufferedImage = ImageIO.read(imageFile);

        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return data.getData();
    }

    @Override
    public Image captureImage() throws NoData, TException {
        logger.info("Client trying to capture image from class: " + this.getClass().getName());

        if (state == DeviceState.OFF) {
            logger.warning("CCTV device if off, client can't capture image");
            throw new NoData("CCTV is off!");
        }

        try {
            byte[] byteImage = getImageData("potato.jpg");
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date date = new Date();
            String dateString = dateFormat.format(date);

            return new Image().setData(byteImage).setDateTaken(dateString);
        } catch (IOException e) {
            logger.warning("Error with reading image for client, image will not be sent");
            throw new NoData("Cannot capture image!");
        }
    }
}
