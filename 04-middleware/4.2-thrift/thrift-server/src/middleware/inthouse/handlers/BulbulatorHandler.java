package middleware.inthouse.handlers;

import middleware.inthouse.*;
import org.apache.thrift.TException;

public class BulbulatorHandler extends DeviceHandler implements Bulbulator.Iface {
    public BulbulatorHandler(int id) {
        super(id);
    }

    @Override
    public ReplyStatus makeBulbulbul() throws NoData, TException {
        if (state == DeviceState.OFF) {
            throw new NoData("Bulbulator is off!");
        } else if (state == DeviceState.POWER_SAVING) {
            state = DeviceState.ON;
        }

        ReplyStatus reply = new ReplyStatus(Status.SUCCESS);
        reply.setMessage("Bulbulbulbulbul");

        return reply;
    }
}
