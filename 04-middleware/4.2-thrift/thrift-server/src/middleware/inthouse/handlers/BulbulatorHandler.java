package middleware.inthouse.handlers;

import middleware.inthouse.*;
import org.apache.thrift.TException;

public class BulbulatorHandler extends DeviceHandler implements Bulbulator.Iface {
    public BulbulatorHandler(int id) {
        super(id);
    }

    @Override
    public ReplyStatus makeBulbulbul() throws NoData, TException {
        logger.info("Client calling bulbulbul method");

        if (state == DeviceState.OFF) {
            logger.warning("Bulbulator is off, client can't call bulbulbul");
            throw new NoData("Bulbulator is off!");
        } else if (state == DeviceState.POWER_SAVING) {
            state = DeviceState.ON;
        }

        ReplyStatus reply = new ReplyStatus(Status.SUCCESS);
        reply.setMessage("Bulbulbulbulbul");

        return reply;
    }
}
