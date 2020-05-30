package distributed.systems.akka.http;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.javadsl.server.RejectionHandler;
import akka.http.javadsl.server.Route;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import distributed.systems.akka.actors.Server;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.PriceResult;
import distributed.systems.akka.utils.Constants;
import distributed.systems.akka.utils.DatabaseUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.Patterns.ask;

public class HTTPServer {
    private static final Duration TERMINATION_TIMEOUT = Duration.ofSeconds(Constants.HTTP_TERMINATION_TIMEOUT);
    private final ActorSystem actorSystem;
    private final ActorRef server;
    private final Materializer materializer;
    private final LoggingAdapter log;
    private CompletionStage<ServerBinding> futureBinding;

    public HTTPServer(String actorSystemName) {
        this.actorSystem = ActorSystem.create(actorSystemName);
        this.server = actorSystem.actorOf(Props.create(Server.class), "http_server");
        this.materializer = Materializer.matFromSystem(actorSystem);
        this.log = Logging.getLogger(actorSystem, this);

        log.debug("Created HTTP server object");
    }

    public void startHttpServer() {
        Http http = Http.get(actorSystem);
        Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = getRoutes().flow(actorSystem, materializer);
        futureBinding = http.bindAndHandle(routeFlow, ConnectHttp.toHost(Constants.HTTP_HOST_NAME, Constants.HTTP_PORT), materializer);

        futureBinding.whenComplete((binding, exception) -> {
            if (binding != null) {
                InetSocketAddress address = binding.localAddress();
                log.debug("Server online at http://{}:{}/", address.getHostString(), address.getPort());
            } else {
                log.error("Failed to bind HTTP endpoint, terminating system");
                actorSystem.terminate();
            }
        });
    }

    private Route getRoutes() {
        RejectionHandler rejectionHandler = RejectionHandler.defaultHandler();
        ExceptionHandler exceptionHandler = ExceptionHandler.newBuilder()
                .matchAny(e -> complete(StatusCodes.BAD_REQUEST, "Error: " + e))
                .build();

        return concat(getPricesRoute(), getReviewsRoute()).seal(rejectionHandler, exceptionHandler);
    }

    private Route getPricesRoute() {
        return path(segment("price").slash(segment()), productName ->
                get(() -> {
                    CompletionStage<Object> actorSystemReply = ask(server, new PriceRequest(productName, server), Duration.ofSeconds(Constants.HTTP_ACTOR_REQUEST_TIMEOUT));

                    return onSuccess(actorSystemReply, (reply) -> {
                        PriceResult priceResult = (PriceResult) reply;
                        return complete((priceResult).getMessage());
                    });
                })
        );
    }

    private Route getReviewsRoute() {
        return path(segment("review").slash(segment()), productName ->
                get(() -> {
                    String url = Constants.OPINEO_URL + productName + Constants.OPINEO_END_PARAMETER;

                    CompletionStage<Object> remotePageReply = Http.get(actorSystem)
                            .singleRequest(HttpRequest.create(url))
                            .thenCompose(response -> response.entity().toStrict(Constants.HTTP_REMOTE_REQUEST_TIMEOUT, materializer))
                            .thenApply(entity -> entity.getData().utf8String())
                            .thenApply(html -> {
                                Element prosSection = Jsoup.parse(html)
                                        .body()
                                        .getElementById("page")
                                        .getElementById("content")
                                        .getElementById("screen")
                                        .getElementsByClass("pls")
                                        .first()
                                        .getElementsByClass("shl_i pl_i")
                                        .first()
                                        .getElementsByClass("pl_attr")
                                        .first();

                                Element spanTag = prosSection.getElementsByTag("span").first();
                                String text = spanTag.text();
                                if (!text.equalsIgnoreCase("zalety")) {
                                    return "No reviews found";
                                }

                                List<String> prosList = prosSection.getElementsByTag("li").eachText();
                                prosList.removeIf(s -> s.equalsIgnoreCase("..."));
                                return String.join("\n", prosList);
                            });

                    return onSuccess(remotePageReply, (reply) -> complete((String) reply));
                }));
    }

    public void terminateHttpServer() {
        futureBinding.thenCompose(binding -> binding.terminate(TERMINATION_TIMEOUT)).toCompletableFuture().thenAccept(httpTerminated -> actorSystem.terminate());
    }

    public static void main(String[] args) {
        DatabaseUtils.createHistoryTable();
        HTTPServer httpServer = new HTTPServer(Constants.HTTP_ACTOR_SYSTEM_NAME);

        httpServer.startHttpServer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean continueReading = true;
        while (continueReading) {
            try {
                String line = reader.readLine();
                if (line.equalsIgnoreCase("quit")) {
                    httpServer.terminateHttpServer();
                    continueReading = false;
                }
            } catch (IOException e) {
                System.out.println("Error with reading input!");
            }
        }
    }
}
