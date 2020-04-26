#!/bin/bash

THRIFT_FILE=intelligent_house.thrift

echo "Compiling for Java"
thrift --gen java -out ../thrift-server/gen $THRIFT_FILE

echo "Compiling for C++"
thrift --gen cpp -out ../thrift-client/gen $THRIFT_FILE

