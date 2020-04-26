package middleware.inthouse.server;

import middleware.inthouse.Bulbulator;
import middleware.inthouse.CCTV;
import middleware.inthouse.RecordingCCTV;
import middleware.inthouse.Thermometer;
import middleware.inthouse.handlers.BulbulatorHandler;
import middleware.inthouse.handlers.CCTVHandler;
import middleware.inthouse.handlers.RecordingCCTVHandler;
import middleware.inthouse.handlers.ThermometerHandler;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {
    public static void main(String[] args) {
        try {
            TServerTransport serverTransport = new TServerSocket(50060);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            TMultiplexedProcessor multiplex = new TMultiplexedProcessor();

            multiplex.registerProcessor("T1", new Thermometer.Processor<>(new ThermometerHandler(1)));
            multiplex.registerProcessor("T2", new Thermometer.Processor<>(new ThermometerHandler(2)));
            multiplex.registerProcessor("BLB", new Bulbulator.Processor<>(new BulbulatorHandler(3)));
            multiplex.registerProcessor("CCTV1", new CCTV.Processor<>(new CCTVHandler(4)));
            multiplex.registerProcessor("CCTV2", new CCTV.Processor<>(new CCTVHandler(5)));
            multiplex.registerProcessor("RecCCTV", new RecordingCCTV.Processor<>(new RecordingCCTVHandler(6)));

            TServer server = new TThreadPoolServer(new Args(serverTransport).protocolFactory(protocolFactory).processor(multiplex));

            System.out.println("Starting multiplex thread pool server...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
