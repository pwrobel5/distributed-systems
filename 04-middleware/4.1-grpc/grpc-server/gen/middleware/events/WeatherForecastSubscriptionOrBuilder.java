// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: event_notification.proto

package middleware.events;

public interface WeatherForecastSubscriptionOrBuilder extends
    // @@protoc_insertion_point(interface_extends:events.WeatherForecastSubscription)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .events.City cities = 1;</code>
   */
  java.util.List<middleware.events.City> 
      getCitiesList();
  /**
   * <code>repeated .events.City cities = 1;</code>
   */
  middleware.events.City getCities(int index);
  /**
   * <code>repeated .events.City cities = 1;</code>
   */
  int getCitiesCount();
  /**
   * <code>repeated .events.City cities = 1;</code>
   */
  java.util.List<? extends middleware.events.CityOrBuilder> 
      getCitiesOrBuilderList();
  /**
   * <code>repeated .events.City cities = 1;</code>
   */
  middleware.events.CityOrBuilder getCitiesOrBuilder(
      int index);
}
