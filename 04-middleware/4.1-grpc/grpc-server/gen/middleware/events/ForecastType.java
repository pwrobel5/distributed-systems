// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: event_notification.proto

package middleware.events;

/**
 * Protobuf enum {@code events.ForecastType}
 */
public enum ForecastType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>SNOW = 0;</code>
   */
  SNOW(0),
  /**
   * <code>SUNNY = 1;</code>
   */
  SUNNY(1),
  /**
   * <code>RAIN = 2;</code>
   */
  RAIN(2),
  /**
   * <code>CLOUDY = 3;</code>
   */
  CLOUDY(3),
  /**
   * <code>FOGGY = 4;</code>
   */
  FOGGY(4),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>SNOW = 0;</code>
   */
  public static final int SNOW_VALUE = 0;
  /**
   * <code>SUNNY = 1;</code>
   */
  public static final int SUNNY_VALUE = 1;
  /**
   * <code>RAIN = 2;</code>
   */
  public static final int RAIN_VALUE = 2;
  /**
   * <code>CLOUDY = 3;</code>
   */
  public static final int CLOUDY_VALUE = 3;
  /**
   * <code>FOGGY = 4;</code>
   */
  public static final int FOGGY_VALUE = 4;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static ForecastType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static ForecastType forNumber(int value) {
    switch (value) {
      case 0: return SNOW;
      case 1: return SUNNY;
      case 2: return RAIN;
      case 3: return CLOUDY;
      case 4: return FOGGY;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<ForecastType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      ForecastType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ForecastType>() {
          public ForecastType findValueByNumber(int number) {
            return ForecastType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return middleware.events.EventNotification.getDescriptor().getEnumTypes().get(1);
  }

  private static final ForecastType[] VALUES = values();

  public static ForecastType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private ForecastType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:events.ForecastType)
}
