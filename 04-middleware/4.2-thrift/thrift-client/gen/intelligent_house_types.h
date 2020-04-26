/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef intelligent_house_TYPES_H
#define intelligent_house_TYPES_H

#include <iosfwd>

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/TBase.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <functional>
#include <memory>


namespace inthouse {

struct DeviceState {
  enum type {
    ON = 1,
    OFF = 2,
    POWER_SAVING = 3
  };
};

extern const std::map<int, const char*> _DeviceState_VALUES_TO_NAMES;

std::ostream& operator<<(std::ostream& out, const DeviceState::type& val);

std::string to_string(const DeviceState::type& val);

struct Status {
  enum type {
    SUCCESS = 1,
    ERROR = 2
  };
};

extern const std::map<int, const char*> _Status_VALUES_TO_NAMES;

std::ostream& operator<<(std::ostream& out, const Status::type& val);

std::string to_string(const Status::type& val);

class ReplyStatus;

class Image;

class InvalidDateFormat;

class NoData;

typedef struct _ReplyStatus__isset {
  _ReplyStatus__isset() : status(false), errorDescription(false) {}
  bool status :1;
  bool errorDescription :1;
} _ReplyStatus__isset;

class ReplyStatus : public virtual ::apache::thrift::TBase {
 public:

  ReplyStatus(const ReplyStatus&);
  ReplyStatus& operator=(const ReplyStatus&);
  ReplyStatus() : status((Status::type)0), errorDescription() {
  }

  virtual ~ReplyStatus() noexcept;
  Status::type status;
  std::string errorDescription;

  _ReplyStatus__isset __isset;

  void __set_status(const Status::type val);

  void __set_errorDescription(const std::string& val);

  bool operator == (const ReplyStatus & rhs) const
  {
    if (!(status == rhs.status))
      return false;
    if (__isset.errorDescription != rhs.__isset.errorDescription)
      return false;
    else if (__isset.errorDescription && !(errorDescription == rhs.errorDescription))
      return false;
    return true;
  }
  bool operator != (const ReplyStatus &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const ReplyStatus & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(ReplyStatus &a, ReplyStatus &b);

std::ostream& operator<<(std::ostream& out, const ReplyStatus& obj);

typedef struct _Image__isset {
  _Image__isset() : data(false), dateTaken(false) {}
  bool data :1;
  bool dateTaken :1;
} _Image__isset;

class Image : public virtual ::apache::thrift::TBase {
 public:

  Image(const Image&);
  Image& operator=(const Image&);
  Image() : data(), dateTaken() {
  }

  virtual ~Image() noexcept;
  std::string data;
  std::string dateTaken;

  _Image__isset __isset;

  void __set_data(const std::string& val);

  void __set_dateTaken(const std::string& val);

  bool operator == (const Image & rhs) const
  {
    if (!(data == rhs.data))
      return false;
    if (!(dateTaken == rhs.dateTaken))
      return false;
    return true;
  }
  bool operator != (const Image &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const Image & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(Image &a, Image &b);

std::ostream& operator<<(std::ostream& out, const Image& obj);

typedef struct _InvalidDateFormat__isset {
  _InvalidDateFormat__isset() : desiredFormat(false) {}
  bool desiredFormat :1;
} _InvalidDateFormat__isset;

class InvalidDateFormat : public ::apache::thrift::TException {
 public:

  InvalidDateFormat(const InvalidDateFormat&);
  InvalidDateFormat& operator=(const InvalidDateFormat&);
  InvalidDateFormat() : desiredFormat() {
  }

  virtual ~InvalidDateFormat() noexcept;
  std::string desiredFormat;

  _InvalidDateFormat__isset __isset;

  void __set_desiredFormat(const std::string& val);

  bool operator == (const InvalidDateFormat & rhs) const
  {
    if (!(desiredFormat == rhs.desiredFormat))
      return false;
    return true;
  }
  bool operator != (const InvalidDateFormat &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const InvalidDateFormat & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
  mutable std::string thriftTExceptionMessageHolder_;
  const char* what() const noexcept;
};

void swap(InvalidDateFormat &a, InvalidDateFormat &b);

std::ostream& operator<<(std::ostream& out, const InvalidDateFormat& obj);

typedef struct _NoData__isset {
  _NoData__isset() : why(false) {}
  bool why :1;
} _NoData__isset;

class NoData : public ::apache::thrift::TException {
 public:

  NoData(const NoData&);
  NoData& operator=(const NoData&);
  NoData() : why() {
  }

  virtual ~NoData() noexcept;
  std::string why;

  _NoData__isset __isset;

  void __set_why(const std::string& val);

  bool operator == (const NoData & rhs) const
  {
    if (!(why == rhs.why))
      return false;
    return true;
  }
  bool operator != (const NoData &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const NoData & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
  mutable std::string thriftTExceptionMessageHolder_;
  const char* what() const noexcept;
};

void swap(NoData &a, NoData &b);

std::ostream& operator<<(std::ostream& out, const NoData& obj);

} // namespace

#endif
