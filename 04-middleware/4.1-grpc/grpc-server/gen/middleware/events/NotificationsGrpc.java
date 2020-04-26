package middleware.events;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.0)",
    comments = "Source: event_notification.proto")
public final class NotificationsGrpc {

  private NotificationsGrpc() {}

  public static final String SERVICE_NAME = "events.Notifications";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<middleware.events.CulturalNewsletterSubscription,
      middleware.events.CulturalEventNotification> getSubscribeCulturalNewsletterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubscribeCulturalNewsletter",
      requestType = middleware.events.CulturalNewsletterSubscription.class,
      responseType = middleware.events.CulturalEventNotification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<middleware.events.CulturalNewsletterSubscription,
      middleware.events.CulturalEventNotification> getSubscribeCulturalNewsletterMethod() {
    io.grpc.MethodDescriptor<middleware.events.CulturalNewsletterSubscription, middleware.events.CulturalEventNotification> getSubscribeCulturalNewsletterMethod;
    if ((getSubscribeCulturalNewsletterMethod = NotificationsGrpc.getSubscribeCulturalNewsletterMethod) == null) {
      synchronized (NotificationsGrpc.class) {
        if ((getSubscribeCulturalNewsletterMethod = NotificationsGrpc.getSubscribeCulturalNewsletterMethod) == null) {
          NotificationsGrpc.getSubscribeCulturalNewsletterMethod = getSubscribeCulturalNewsletterMethod =
              io.grpc.MethodDescriptor.<middleware.events.CulturalNewsletterSubscription, middleware.events.CulturalEventNotification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubscribeCulturalNewsletter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  middleware.events.CulturalNewsletterSubscription.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  middleware.events.CulturalEventNotification.getDefaultInstance()))
              .setSchemaDescriptor(new NotificationsMethodDescriptorSupplier("SubscribeCulturalNewsletter"))
              .build();
        }
      }
    }
    return getSubscribeCulturalNewsletterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<middleware.events.WeatherForecastSubscription,
      middleware.events.WeatherForecastNotification> getSubscribeWeatherForecastMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubscribeWeatherForecast",
      requestType = middleware.events.WeatherForecastSubscription.class,
      responseType = middleware.events.WeatherForecastNotification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<middleware.events.WeatherForecastSubscription,
      middleware.events.WeatherForecastNotification> getSubscribeWeatherForecastMethod() {
    io.grpc.MethodDescriptor<middleware.events.WeatherForecastSubscription, middleware.events.WeatherForecastNotification> getSubscribeWeatherForecastMethod;
    if ((getSubscribeWeatherForecastMethod = NotificationsGrpc.getSubscribeWeatherForecastMethod) == null) {
      synchronized (NotificationsGrpc.class) {
        if ((getSubscribeWeatherForecastMethod = NotificationsGrpc.getSubscribeWeatherForecastMethod) == null) {
          NotificationsGrpc.getSubscribeWeatherForecastMethod = getSubscribeWeatherForecastMethod =
              io.grpc.MethodDescriptor.<middleware.events.WeatherForecastSubscription, middleware.events.WeatherForecastNotification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubscribeWeatherForecast"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  middleware.events.WeatherForecastSubscription.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  middleware.events.WeatherForecastNotification.getDefaultInstance()))
              .setSchemaDescriptor(new NotificationsMethodDescriptorSupplier("SubscribeWeatherForecast"))
              .build();
        }
      }
    }
    return getSubscribeWeatherForecastMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NotificationsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationsStub>() {
        @java.lang.Override
        public NotificationsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationsStub(channel, callOptions);
        }
      };
    return NotificationsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NotificationsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationsBlockingStub>() {
        @java.lang.Override
        public NotificationsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationsBlockingStub(channel, callOptions);
        }
      };
    return NotificationsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NotificationsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationsFutureStub>() {
        @java.lang.Override
        public NotificationsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationsFutureStub(channel, callOptions);
        }
      };
    return NotificationsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class NotificationsImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribeCulturalNewsletter(middleware.events.CulturalNewsletterSubscription request,
        io.grpc.stub.StreamObserver<middleware.events.CulturalEventNotification> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeCulturalNewsletterMethod(), responseObserver);
    }

    /**
     */
    public void subscribeWeatherForecast(middleware.events.WeatherForecastSubscription request,
        io.grpc.stub.StreamObserver<middleware.events.WeatherForecastNotification> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeWeatherForecastMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeCulturalNewsletterMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                middleware.events.CulturalNewsletterSubscription,
                middleware.events.CulturalEventNotification>(
                  this, METHODID_SUBSCRIBE_CULTURAL_NEWSLETTER)))
          .addMethod(
            getSubscribeWeatherForecastMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                middleware.events.WeatherForecastSubscription,
                middleware.events.WeatherForecastNotification>(
                  this, METHODID_SUBSCRIBE_WEATHER_FORECAST)))
          .build();
    }
  }

  /**
   */
  public static final class NotificationsStub extends io.grpc.stub.AbstractAsyncStub<NotificationsStub> {
    private NotificationsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationsStub(channel, callOptions);
    }

    /**
     */
    public void subscribeCulturalNewsletter(middleware.events.CulturalNewsletterSubscription request,
        io.grpc.stub.StreamObserver<middleware.events.CulturalEventNotification> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeCulturalNewsletterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void subscribeWeatherForecast(middleware.events.WeatherForecastSubscription request,
        io.grpc.stub.StreamObserver<middleware.events.WeatherForecastNotification> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeWeatherForecastMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NotificationsBlockingStub extends io.grpc.stub.AbstractBlockingStub<NotificationsBlockingStub> {
    private NotificationsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationsBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<middleware.events.CulturalEventNotification> subscribeCulturalNewsletter(
        middleware.events.CulturalNewsletterSubscription request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeCulturalNewsletterMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<middleware.events.WeatherForecastNotification> subscribeWeatherForecast(
        middleware.events.WeatherForecastSubscription request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeWeatherForecastMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NotificationsFutureStub extends io.grpc.stub.AbstractFutureStub<NotificationsFutureStub> {
    private NotificationsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationsFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE_CULTURAL_NEWSLETTER = 0;
  private static final int METHODID_SUBSCRIBE_WEATHER_FORECAST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NotificationsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NotificationsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE_CULTURAL_NEWSLETTER:
          serviceImpl.subscribeCulturalNewsletter((middleware.events.CulturalNewsletterSubscription) request,
              (io.grpc.stub.StreamObserver<middleware.events.CulturalEventNotification>) responseObserver);
          break;
        case METHODID_SUBSCRIBE_WEATHER_FORECAST:
          serviceImpl.subscribeWeatherForecast((middleware.events.WeatherForecastSubscription) request,
              (io.grpc.stub.StreamObserver<middleware.events.WeatherForecastNotification>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class NotificationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NotificationsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return middleware.events.EventNotification.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Notifications");
    }
  }

  private static final class NotificationsFileDescriptorSupplier
      extends NotificationsBaseDescriptorSupplier {
    NotificationsFileDescriptorSupplier() {}
  }

  private static final class NotificationsMethodDescriptorSupplier
      extends NotificationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NotificationsMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NotificationsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NotificationsFileDescriptorSupplier())
              .addMethod(getSubscribeCulturalNewsletterMethod())
              .addMethod(getSubscribeWeatherForecastMethod())
              .build();
        }
      }
    }
    return result;
  }
}
