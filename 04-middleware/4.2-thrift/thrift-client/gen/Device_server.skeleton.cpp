// This autogenerated skeleton file illustrates how to build a server.
// You should copy it to another filename to avoid overwriting it.

#include "Device.h"
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/server/TSimpleServer.h>
#include <thrift/transport/TServerSocket.h>
#include <thrift/transport/TBufferTransports.h>

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;
using namespace ::apache::thrift::server;

using namespace  ::inthouse;

class DeviceHandler : virtual public DeviceIf {
 public:
  DeviceHandler() {
    // Your initialization goes here
  }

  void turnOn(ReplyStatus& _return) {
    // Your implementation goes here
    printf("turnOn\n");
  }

  void turnOff(ReplyStatus& _return) {
    // Your implementation goes here
    printf("turnOff\n");
  }

  void powerSavingMode(ReplyStatus& _return) {
    // Your implementation goes here
    printf("powerSavingMode\n");
  }

  DeviceState::type getDeviceState() {
    // Your implementation goes here
    printf("getDeviceState\n");
  }

};

int main(int argc, char **argv) {
  int port = 9090;
  ::std::shared_ptr<DeviceHandler> handler(new DeviceHandler());
  ::std::shared_ptr<TProcessor> processor(new DeviceProcessor(handler));
  ::std::shared_ptr<TServerTransport> serverTransport(new TServerSocket(port));
  ::std::shared_ptr<TTransportFactory> transportFactory(new TBufferedTransportFactory());
  ::std::shared_ptr<TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());

  TSimpleServer server(processor, serverTransport, transportFactory, protocolFactory);
  server.serve();
  return 0;
}

