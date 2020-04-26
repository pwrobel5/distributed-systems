package middleware.generators;


import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import middleware.events.CulturalEventNotification;
import middleware.events.CulturalEventType;
import middleware.events.CulturalNewsletterSubscription;

import java.util.*;

import static com.google.protobuf.util.Timestamps.fromMillis;
import static java.util.Map.entry;

public class CulturalEventGenerator {
    private final List<CulturalEventType> subscribedEvents;
    private final Random random;
    private Timestamp timestamp;

    private static final Map<CulturalEventType, List<String>> EVENT_TITLES = Map.ofEntries(
            entry(CulturalEventType.CONCERT, new ArrayList<>(Arrays.asList("Philharmonic concert", "Sacrum Profanum Festival", "Underground band concert"))),
            entry(CulturalEventType.EXHIBITION, new ArrayList<>(Arrays.asList("Modern Art Exhibition", "World Press Photo", "Vernissage of famous artist"))),
            entry(CulturalEventType.THEATER_PLAY, new ArrayList<>(Arrays.asList("Wesele", "Romeo and Juliet", "Lady Macbeth"))),
            entry(CulturalEventType.FILM_FESTIVAL, new ArrayList<>(Arrays.asList("Off film festival", "Mountain film festival", "Festival of silent movies")))
    );
    private static final int SECONDS_IN_DAY = 86400;
    private static final Duration TIME_DURATION = Duration.newBuilder().setSeconds(SECONDS_IN_DAY).build();


    public CulturalEventGenerator(CulturalNewsletterSubscription request) {
        subscribedEvents = new ArrayList<>(request.getTypesList());

        for (CulturalEventType eventType : subscribedEvents) {
            if (!EVENT_TITLES.containsKey(eventType))
                throw new IllegalArgumentException();
        }

        timestamp = fromMillis(System.currentTimeMillis());
        random = new Random();
    }

    public CulturalEventNotification getEvent() {
        CulturalEventType eventType = subscribedEvents.get(random.nextInt(subscribedEvents.size()));
        List<String> titlesList = EVENT_TITLES.get(eventType);
        String title = titlesList.get(random.nextInt(titlesList.size()));
        timestamp = Timestamps.add(timestamp, TIME_DURATION);

        return CulturalEventNotification.newBuilder().setTitle(title).setType(eventType).setDate(timestamp).build();
    }
}
