// This autogenerated skeleton file illustrates how to build a server.
// You should copy it to another filename to avoid overwriting it.

#include "RecordingCCTV.h"
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/server/TSimpleServer.h>
#include <thrift/transport/TServerSocket.h>
#include <thrift/transport/TBufferTransports.h>

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;
using namespace ::apache::thrift::server;

using namespace  ::inthouse;

class RecordingCCTVHandler : virtual public RecordingCCTVIf {
 public:
  RecordingCCTVHandler() {
    // Your initialization goes here
  }

  void captureVideo(std::vector<Image> & _return, const std::string& dateFrom, const std::string& dateTo) {
    // Your implementation goes here
    printf("captureVideo\n");
  }

};

int main(int argc, char **argv) {
  int port = 9090;
  ::std::shared_ptr<RecordingCCTVHandler> handler(new RecordingCCTVHandler());
  ::std::shared_ptr<TProcessor> processor(new RecordingCCTVProcessor(handler));
  ::std::shared_ptr<TServerTransport> serverTransport(new TServerSocket(port));
  ::std::shared_ptr<TTransportFactory> transportFactory(new TBufferedTransportFactory());
  ::std::shared_ptr<TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());

  TSimpleServer server(processor, serverTransport, transportFactory, protocolFactory);
  server.serve();
  return 0;
}

