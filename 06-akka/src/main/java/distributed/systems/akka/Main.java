package distributed.systems.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
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
        final ActorRef client = actorSystem.actorOf(Props.create(Client.class, server), "client");

        DatabaseUtils.createHistoryTable();

        System.out.println("Started, type product name to get information or type 'quit' to exit, type 'test' to run tests");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean continueReading = true;
        while (continueReading) {
            try {
                String line = reader.readLine();
                if (line.equalsIgnoreCase("quit")) {
                    System.out.println("Finishing work...");
                    continueReading = false;
                } else if (line.equalsIgnoreCase("test")) {
                    System.out.println("Running tests");

                    final ActorRef client1 = actorSystem.actorOf(Props.create(Client.class, server));
                    final ActorRef client2 = actorSystem.actorOf(Props.create(Client.class, server));
                    final ActorRef client3 = actorSystem.actorOf(Props.create(Client.class, server));

                    client1.tell(new PriceRequest("laptop"), ActorRef.noSender());
                    client2.tell(new PriceRequest("iPhone"), ActorRef.noSender());
                    client3.tell(new PriceRequest("headphones"), ActorRef.noSender());

                    try {
                        Thread.sleep(Constants.TIMEOUT_MILLIS * 10);
                        client1.tell(PoisonPill.getInstance(), ActorRef.noSender());
                        client2.tell(PoisonPill.getInstance(), ActorRef.noSender());
                        client3.tell(PoisonPill.getInstance(), ActorRef.noSender());
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted tests");
                        System.err.println(e.getMessage());
                    }
                } else {
                    client.tell(new PriceRequest(line), ActorRef.noSender());
                }
            } catch (IOException e) {
                System.out.println("Error with reading input!");
            }
        }

        actorSystem.terminate();
    }
}
