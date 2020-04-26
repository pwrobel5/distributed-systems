package middleware.inthouse.handlers;

import middleware.inthouse.Device;
import middleware.inthouse.DeviceState;
import middleware.inthouse.ReplyStatus;
import middleware.inthouse.Status;
import org.apache.thrift.TException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeviceHandler implements Device.Iface {
    protected DeviceState state = DeviceState.OFF;
    protected int id;
    protected final Lock lock = new ReentrantLock();

    public DeviceHandler(int id) {
        id = id;
    }

    @Override
    public ReplyStatus turnOn() throws TException {
        lock.lock();

        ReplyStatus reply = new ReplyStatus();
        if (state == DeviceState.ON) {
            reply.setMessage("Device is already on!");
        } else {
            state = DeviceState.ON;
        }
        lock.unlock();

        reply.setStatus(Status.SUCCESS);
        return reply;
    }

    @Override
    public ReplyStatus turnOff() throws TException {
        lock.lock();

        ReplyStatus reply = new ReplyStatus();
        if (state == DeviceState.OFF) {
            reply.setMessage("Device is already off!");
        } else {
            state = DeviceState.OFF;
        }
        lock.unlock();

        reply.setStatus(Status.SUCCESS);
        return reply;
    }

    @Override
    public ReplyStatus powerSavingMode() throws TException {
        lock.lock();

        ReplyStatus reply = new ReplyStatus();
        if (state == DeviceState.POWER_SAVING) {
            reply.setMessage("Device is already in power saving mode!");
        } else {
            state = DeviceState.POWER_SAVING;
        }
        lock.unlock();
        
        reply.setStatus(Status.SUCCESS);
        return reply;
    }

    @Override
    public DeviceState getDeviceState() throws TException {
        return state;
    }
}
