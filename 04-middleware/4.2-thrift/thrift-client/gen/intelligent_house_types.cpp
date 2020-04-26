/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "intelligent_house_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>

namespace inthouse {

int _kDeviceStateValues[] = {
  DeviceState::ON,
  DeviceState::OFF,
  DeviceState::POWER_SAVING
};
const char* _kDeviceStateNames[] = {
  "ON",
  "OFF",
  "POWER_SAVING"
};
const std::map<int, const char*> _DeviceState_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(3, _kDeviceStateValues, _kDeviceStateNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

std::ostream& operator<<(std::ostream& out, const DeviceState::type& val) {
  std::map<int, const char*>::const_iterator it = _DeviceState_VALUES_TO_NAMES.find(val);
  if (it != _DeviceState_VALUES_TO_NAMES.end()) {
    out << it->second;
  } else {
    out << static_cast<int>(val);
  }
  return out;
}

std::string to_string(const DeviceState::type& val) {
  std::map<int, const char*>::const_iterator it = _DeviceState_VALUES_TO_NAMES.find(val);
  if (it != _DeviceState_VALUES_TO_NAMES.end()) {
    return std::string(it->second);
  } else {
    return std::to_string(static_cast<int>(val));
  }
}

int _kStatusValues[] = {
  Status::SUCCESS,
  Status::ERROR
};
const char* _kStatusNames[] = {
  "SUCCESS",
  "ERROR"
};
const std::map<int, const char*> _Status_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(2, _kStatusValues, _kStatusNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

std::ostream& operator<<(std::ostream& out, const Status::type& val) {
  std::map<int, const char*>::const_iterator it = _Status_VALUES_TO_NAMES.find(val);
  if (it != _Status_VALUES_TO_NAMES.end()) {
    out << it->second;
  } else {
    out << static_cast<int>(val);
  }
  return out;
}

std::string to_string(const Status::type& val) {
  std::map<int, const char*>::const_iterator it = _Status_VALUES_TO_NAMES.find(val);
  if (it != _Status_VALUES_TO_NAMES.end()) {
    return std::string(it->second);
  } else {
    return std::to_string(static_cast<int>(val));
  }
}


ReplyStatus::~ReplyStatus() noexcept {
}


void ReplyStatus::__set_status(const Status::type val) {
  this->status = val;
}

void ReplyStatus::__set_message(const std::string& val) {
  this->message = val;
__isset.message = true;
}
std::ostream& operator<<(std::ostream& out, const ReplyStatus& obj)
{
  obj.printTo(out);
  return out;
}


uint32_t ReplyStatus::read(::apache::thrift::protocol::TProtocol* iprot) {

  ::apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast0;
          xfer += iprot->readI32(ecast0);
          this->status = (Status::type)ecast0;
          this->__isset.status = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          this->__isset.message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t ReplyStatus::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  ::apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("ReplyStatus");

  xfer += oprot->writeFieldBegin("status", ::apache::thrift::protocol::T_I32, 1);
  xfer += oprot->writeI32((int32_t)this->status);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.message) {
    xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->message);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(ReplyStatus &a, ReplyStatus &b) {
  using ::std::swap;
  swap(a.status, b.status);
  swap(a.message, b.message);
  swap(a.__isset, b.__isset);
}

ReplyStatus::ReplyStatus(const ReplyStatus& other1) {
  status = other1.status;
  message = other1.message;
  __isset = other1.__isset;
}
ReplyStatus& ReplyStatus::operator=(const ReplyStatus& other2) {
  status = other2.status;
  message = other2.message;
  __isset = other2.__isset;
  return *this;
}
void ReplyStatus::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "ReplyStatus(";
  out << "status=" << to_string(status);
  out << ", " << "message="; (__isset.message ? (out << to_string(message)) : (out << "<null>"));
  out << ")";
}


Image::~Image() noexcept {
}


void Image::__set_data(const std::string& val) {
  this->data = val;
}

void Image::__set_dateTaken(const std::string& val) {
  this->dateTaken = val;
}
std::ostream& operator<<(std::ostream& out, const Image& obj)
{
  obj.printTo(out);
  return out;
}


uint32_t Image::read(::apache::thrift::protocol::TProtocol* iprot) {

  ::apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readBinary(this->data);
          this->__isset.data = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->dateTaken);
          this->__isset.dateTaken = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t Image::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  ::apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("Image");

  xfer += oprot->writeFieldBegin("data", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeBinary(this->data);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("dateTaken", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->dateTaken);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(Image &a, Image &b) {
  using ::std::swap;
  swap(a.data, b.data);
  swap(a.dateTaken, b.dateTaken);
  swap(a.__isset, b.__isset);
}

Image::Image(const Image& other3) {
  data = other3.data;
  dateTaken = other3.dateTaken;
  __isset = other3.__isset;
}
Image& Image::operator=(const Image& other4) {
  data = other4.data;
  dateTaken = other4.dateTaken;
  __isset = other4.__isset;
  return *this;
}
void Image::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "Image(";
  out << "data=" << to_string(data);
  out << ", " << "dateTaken=" << to_string(dateTaken);
  out << ")";
}


InvalidDateFormat::~InvalidDateFormat() noexcept {
}


void InvalidDateFormat::__set_desiredFormat(const std::string& val) {
  this->desiredFormat = val;
}
std::ostream& operator<<(std::ostream& out, const InvalidDateFormat& obj)
{
  obj.printTo(out);
  return out;
}


uint32_t InvalidDateFormat::read(::apache::thrift::protocol::TProtocol* iprot) {

  ::apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->desiredFormat);
          this->__isset.desiredFormat = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t InvalidDateFormat::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  ::apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("InvalidDateFormat");

  xfer += oprot->writeFieldBegin("desiredFormat", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->desiredFormat);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(InvalidDateFormat &a, InvalidDateFormat &b) {
  using ::std::swap;
  swap(a.desiredFormat, b.desiredFormat);
  swap(a.__isset, b.__isset);
}

InvalidDateFormat::InvalidDateFormat(const InvalidDateFormat& other5) : TException() {
  desiredFormat = other5.desiredFormat;
  __isset = other5.__isset;
}
InvalidDateFormat& InvalidDateFormat::operator=(const InvalidDateFormat& other6) {
  desiredFormat = other6.desiredFormat;
  __isset = other6.__isset;
  return *this;
}
void InvalidDateFormat::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "InvalidDateFormat(";
  out << "desiredFormat=" << to_string(desiredFormat);
  out << ")";
}

const char* InvalidDateFormat::what() const noexcept {
  try {
    std::stringstream ss;
    ss << "TException - service has thrown: " << *this;
    this->thriftTExceptionMessageHolder_ = ss.str();
    return this->thriftTExceptionMessageHolder_.c_str();
  } catch (const std::exception&) {
    return "TException - service has thrown: InvalidDateFormat";
  }
}


NoData::~NoData() noexcept {
}


void NoData::__set_why(const std::string& val) {
  this->why = val;
}
std::ostream& operator<<(std::ostream& out, const NoData& obj)
{
  obj.printTo(out);
  return out;
}


uint32_t NoData::read(::apache::thrift::protocol::TProtocol* iprot) {

  ::apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->why);
          this->__isset.why = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t NoData::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  ::apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("NoData");

  xfer += oprot->writeFieldBegin("why", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->why);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(NoData &a, NoData &b) {
  using ::std::swap;
  swap(a.why, b.why);
  swap(a.__isset, b.__isset);
}

NoData::NoData(const NoData& other7) : TException() {
  why = other7.why;
  __isset = other7.__isset;
}
NoData& NoData::operator=(const NoData& other8) {
  why = other8.why;
  __isset = other8.__isset;
  return *this;
}
void NoData::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "NoData(";
  out << "why=" << to_string(why);
  out << ")";
}

const char* NoData::what() const noexcept {
  try {
    std::stringstream ss;
    ss << "TException - service has thrown: " << *this;
    this->thriftTExceptionMessageHolder_ = ss.str();
    return this->thriftTExceptionMessageHolder_.c_str();
  } catch (const std::exception&) {
    return "TException - service has thrown: NoData";
  }
}

} // namespace