package distributed.systems.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import distributed.systems.akka.actors.Client;
import distributed.systems.akka.actors.Server;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.utils.Constants;
import distributed.systems.akka.utils.DatabaseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        final ActorSystem actorSystem = ActorSystem.create(Constants.ACTOR_SYSTEM_NAME);
        final ActorRef server = actorSystem.actorOf(Props.create(Server.class), "server");
        final ActorRef client = actorSystem.actorOf(Props.create(Client.class), "client");

        DatabaseUtils.createHistoryTable();

        System.out.println("Started, type product name to get information or type 'quit' to exit");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean continueReading = true;
        while (continueReading) {
            try {
                String line = reader.readLine();
                if (line.equalsIgnoreCase("quit")) {
                    System.out.println("Finishing work...");
                    continueReading = false;
                } else {
                    client.tell(new PriceRequest(line, server), ActorRef.noSender());
                }
            } catch (IOException e) {
                System.out.println("Error with reading input!");
            }
        }

        actorSystem.terminate();
    }
}
