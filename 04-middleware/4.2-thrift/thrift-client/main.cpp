#include <iostream>
#include <vector>
#include <string>

#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/transport/TSocket.h>
#include <thrift/transport/TTransportUtils.h>
#include <thrift/protocol/TMultiplexedProtocol.h>

#include "gen/Thermometer.h"
#include "gen/Bulbulator.h"
#include "gen/CCTV.h"
#include "gen/RecordingCCTV.h"

using namespace apache::thrift;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;
using namespace inthouse;

std::string COMMON_MENU_ENTRY = "Choose operation:\n\ta - turn on\n\tb - turn off\n\tc - enter power saving mode\n\tx - exit\n";

void printReply(ReplyStatus reply) {
    std::cout << reply.status << std::endl;
    if (reply.__isset.message)
        std::cout << reply.message << std::endl;
}

bool handleCommonOptions(DeviceClient client, const std::string &chosenOption, bool &endOperation) {
    ReplyStatus reply;

    if (chosenOption == "x") {
        endOperation = true;
        return true;
    } else if (chosenOption == "a") {
        client.turnOn(reply);
        printReply(reply);
        return true;
    } else if (chosenOption == "b") {
        client.turnOff(reply);
        printReply(reply);
        return true;
    } else if (chosenOption == "c") {
        client.powerSavingMode(reply);
        printReply(reply);
        return true;
    }
    return false;
}

void handleThermometer(std::shared_ptr<TProtocol> protocol) {
    std::cout << "Enter thermometer service name: ";
    std::string serviceName;
    std::cin >> serviceName;

    std::shared_ptr<TProtocol> localProtocol = std::make_shared<TMultiplexedProtocol>(protocol, serviceName);
    ThermometerClient client(localProtocol);
    bool endOperation = false;

    while (!endOperation) {
        std::cout << COMMON_MENU_ENTRY;
        std::cout << "\td - read temperature\n\te - get temperature history\n\n";
        std::string chosenOption;
        std::cout << "Enter option: ";
        std::cin >> chosenOption;
        ReplyStatus reply;

        try {
            if (!handleCommonOptions(client, chosenOption, endOperation)) {
                if (chosenOption == "d") {
                    double temperature = client.getCurrentTemperature();
                    std::cout << "Read temperature: " << temperature << std::endl;
                } else if (chosenOption == "e") {
                    std::string dateFrom, dateTo;
                    std::cout << "Enter beginning hour: ";
                    std::cin >> dateFrom;
                    std::cout << "Enter end hour: ";
                    std::cin >> dateTo;

                    std::vector<double> temperatureHistory;
                    client.getTemperatureHistory(temperatureHistory, dateFrom, dateTo);
                    for (auto &i : temperatureHistory) {
                        std::cout << "\tTemperature: " << i << std::endl;
                    }
                } else {
                    std::cout << "Invalid option!" << std::endl;
                }
            }
        } catch (NoData &nd) {
            std::cout << "No data: " << nd.why << std::endl;
        } catch (InvalidDateFormat &idf) {
            std::cout << "Invalid date format! Correct format: " << idf.desiredFormat << std::endl;
        } catch (TException &tx) {
            std::cout << "Uncorrect service id!" << std::endl;
            endOperation = true;
        }
    }
}

void handleBulbulator(std::shared_ptr<TProtocol> protocol) {
    std::cout << "Enter Bulbulator service name: ";
    std::string serviceName;
    std::cin >> serviceName;

    std::shared_ptr<TProtocol> localProtocol = std::make_shared<TMultiplexedProtocol>(protocol, serviceName);
    BulbulatorClient client(localProtocol);
    bool endOperation = false;

    while (!endOperation) {
        std::cout << COMMON_MENU_ENTRY;
        std::cout << "\td - make bulbulbul\n\n";
        std::string chosenOption;
        std::cout << "Enter option: ";
        std::cin >> chosenOption;
        ReplyStatus reply;

        try {
            if (!handleCommonOptions(client, chosenOption, endOperation)) {
                if (chosenOption == "d") {
                    client.makeBulbulbul(reply);
                    printReply(reply);
                } else {
                    std::cout << "Invalid option!" << std::endl;
                }
            }
        } catch (NoData &nd) {
            std::cout << "No data: " << nd.why << std::endl;
        } catch (TException &tx) {
            std::cout << "Uncorrect service id!" << std::endl;
            endOperation = true;
        }
    }
}

void saveImage(Image image, std::string fileName) {
    FILE *outputFile = fopen(fileName.c_str(), "wb");
    fwrite(image.data.c_str(), 1, image.data.size(), outputFile);
    fclose(outputFile);
}

void handleStandardCCTV(std::shared_ptr<TProtocol> protocol) {
    std::cout << "Enter CCTV service name: ";
    std::string serviceName;
    std::cin >> serviceName;

    std::shared_ptr<TProtocol> localProtocol = std::make_shared<TMultiplexedProtocol>(protocol, serviceName);
    CCTVClient client(localProtocol);
    bool endOperation = false;

    while (!endOperation) {
        std::cout << COMMON_MENU_ENTRY;
        std::cout << "\td - capture image\n\n";
        std::string chosenOption;
        std::cout << "Enter option: ";
        std::cin >> chosenOption;
        ReplyStatus reply;

        try {
            if (!handleCommonOptions(client, chosenOption, endOperation)) {
                if (chosenOption == "d") {
                    Image image;
                    client.captureImage(image);
                    std::cout << "Captured image from: " << image.dateTaken << std::endl;
                    std::string fileName = "output_" + image.dateTaken + ".jpg";
                    saveImage(image, fileName);
                } else {
                    std::cout << "Invalid option!" << std::endl;
                }
            }
        } catch (NoData &nd) {
            std::cout << "No data: " << nd.why << std::endl;
        } catch (TException &tx) {
            std::cout << "Uncorrect service id!" << std::endl;
            endOperation = true;
        }
    }
}

void handleRecordingCCTV(std::shared_ptr<TProtocol> protocol) {
    std::cout << "Enter Recording CCTV service name: ";
    std::string serviceName;
    std::cin >> serviceName;

    std::shared_ptr<TProtocol> localProtocol = std::make_shared<TMultiplexedProtocol>(protocol, serviceName);
    RecordingCCTVClient client(localProtocol);
    bool endOperation = false;

    while (!endOperation) {
        std::cout << COMMON_MENU_ENTRY;
        std::cout << "\td - capture image\n\te - capture video\n\n";
        std::string chosenOption;
        std::cout << "Enter option: ";
        std::cin >> chosenOption;
        ReplyStatus reply;

        try {
            if (!handleCommonOptions(client, chosenOption, endOperation)) {
                if (chosenOption == "d") {
                    Image image;
                    client.captureImage(image);
                    std::cout << "Captured image from: " << image.dateTaken << std::endl;
                    std::string fileName = "output_" + image.dateTaken + ".jpg";
                    saveImage(image, fileName);
                } else if (chosenOption == "e") {
                    std::vector<Image> images;
                    std::string dateFrom, dateTo;
                    std::cout << "Enter beginning hour: ";
                    std::cin.ignore();
                    std::getline(std::cin, dateFrom);
                    std::cout << "Enter end hour: ";
                    std::getline(std::cin, dateTo);

                    client.captureVideo(images, dateFrom, dateTo);
                    std::cout << "Captured video" << std::endl;
                    for (int i = 0; i < images.size(); i++) {
                        saveImage(images[i], "output_" + images[i].dateTaken + std::to_string(i) + ".jpg");
                    }
                    std::cout << "Video saved" << std::endl;
                } else {
                    std::cout << "Invalid option!" << std::endl;
                }
            }
        } catch (NoData &nd) {
            std::cout << "No data: " << nd.why << std::endl;
        } catch (InvalidDateFormat &idf) {
            std::cout << "Invalid date format! Correct format: " << idf.desiredFormat << std::endl;
        } catch (TException &tx) {
            std::cout << "Uncorrect service id!" << std::endl;
            endOperation = true;
        }
    }
}

void handleCCTV(std::shared_ptr<TProtocol> protocol) {
    std::cout << "Choose CCTV device type:\n\ta - standard CCTV\n\tb - CCTV with recording tool\n\tx - back\n\n";
    std::string chosenOption;
    std::cout << "Enter option: ";
    std::cin >> chosenOption;

    if (chosenOption == "a") {
        handleStandardCCTV(protocol);
    } else if (chosenOption == "b") {
        handleRecordingCCTV(protocol);
    } else if (chosenOption == "x") {
        return;
    } else {
        std::cout << "Invalid choice!" << std::endl;
    }
}

int main() {
    const std::string MAIN_MENU_ENTRY = "Choose device type\n\ta - thermometer\n\tb - bulbulator\n\tc - CCTV\n\tx - end\n\n";
    const std::string EXIT_OPTION = "x";

    std::cout << "Thrift client" << std::endl;

    std::shared_ptr<TTransport> socket(new TSocket("localhost", 50060));
    std::shared_ptr<TTransport> transport(new TBufferedTransport(socket));
    std::shared_ptr<TProtocol> protocol(new TBinaryProtocol(transport));

    try {
        transport->open();

        std::cout << "Connected" << std::endl;
        bool endRunning = false;

        while (!endRunning) {
            std::cout << MAIN_MENU_ENTRY;
            std::string response;
            std::cin >> response;

            if (response == EXIT_OPTION)
                endRunning = true;
            else if (response == "a")
                handleThermometer(protocol);
            else if (response == "b")
                handleBulbulator(protocol);
            else if (response == "c")
                handleCCTV(protocol);
            else
                std::cout << "Invalid option!" << std::endl;
        }

        std::cout << "Closing connection..." << std::endl;
        transport->close();
        std::cout << "Connection closed" << std::endl;
    } catch (TException &tx) {
        std::cout << "ERROR: " << tx.what() << std::endl;
    }

    return 0;
}
