cmake_minimum_required(VERSION 3.16)
project(thrift_client)

find_library(THRIFT thrift)

set(CMAKE_CXX_STANDARD 14)
link_libraries(thrift)
add_executable(thrift_client main.cpp gen/Bulbulator.cpp gen/CCTV.cpp gen/Device.cpp gen/intelligent_house_constants.cpp gen/intelligent_house_types.cpp
        gen/RecordingCCTV.cpp gen/Thermometer.cpp)