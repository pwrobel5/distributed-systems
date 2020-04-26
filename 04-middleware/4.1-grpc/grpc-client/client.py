import grpc
import event_notification_pb2_grpc



if __name__ == "__main__":
    channel = grpc.insecure_channel("localhost:50055")
    stub = event_notification_pb2_grpc.CulturalNewsletterStub(channel)

    channel.close()