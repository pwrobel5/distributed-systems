namespace cpp inthouse
namespace java middleware.inthouse

enum DeviceState {
  ON = 1,
  OFF = 2,
  POWER_SAVING = 3
}

enum Status {
  SUCCESS = 1,
  ERROR = 2
}

struct ReplyStatus {
  1: Status status,
  2: optional string message
}

struct Image {
  1: binary data,
  2: string dateTaken
}

exception InvalidDateFormat {
  1: string desiredFormat
}

exception NoData {
  1: string why
}

service Device {
  ReplyStatus turnOn(),
  ReplyStatus turnOff(),
  ReplyStatus powerSavingMode(),
  DeviceState getDeviceState()
}

service Thermometer extends Device {
  double getCurrentTemperature() throws (1: NoData ex),
  list<double> getTemperatureHistory(1:string dateFrom, 2:string dateTo) throws (1: InvalidDateFormat ex, 2: NoData ex2)
}

service Bulbulator extends Device {
  ReplyStatus makeBulbulbul() throws (1: NoData ex)
}

service CCTV extends Device {
  Image captureImage() throws (1: NoData ex)
}

service RecordingCCTV extends CCTV {
  list<Image> captureVideo(1:string dateFrom, 2:string dateTo) throws (1: InvalidDateFormat ex, 2: NoData ex2)
}
