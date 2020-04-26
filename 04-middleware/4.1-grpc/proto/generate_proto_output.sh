#!/bin/bash

PROTO_FILE=event_notification.proto

echo "Compiling for Java"
protoc -I=. --java_out=../grpc-server/gen --plugin=protoc-gen-grpc-java=/usr/local/bin/protoc-grpc.exe --grpc-java_out=../grpc-server/gen $PROTO_FILE

echo "Compiling for Python"
python3 -m grpc_tools.protoc -I=. --python_out=../grpc-client/gen --grpc_python_out=../grpc-client/gen $PROTO_FILE

