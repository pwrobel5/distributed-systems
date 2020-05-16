package distributed.systems.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class DataMonitor implements Watcher, AsyncCallback.StatCallback {
    private ZooKeeper zooKeeper;
    private String zNode;
    private Watcher chainedWatcher;

    public DataMonitor(String hostPort, String zNode, Watcher chainedWatcher) throws IOException {
        this.zooKeeper = new ZooKeeper(hostPort, 3000, this);
        //this.zooKeeper = zooKeeper;
        this.zNode = zNode;
        this.chainedWatcher = chainedWatcher;

        zooKeeper.exists(zNode, true, this, null);
        System.out.println("Created monitor");
    }

    public void processResult(int i, String s, Object o, Stat stat) {
        System.out.println("Process result");
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Process");
    }
}
