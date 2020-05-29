package distributed.systems.akka.messages;

public class DatabaseResult {
    private final int queriesCount;

    public DatabaseResult(int queriesCount) {
        this.queriesCount = queriesCount;
    }

    public int getQueriesCount() {
        return queriesCount;
    }
}
