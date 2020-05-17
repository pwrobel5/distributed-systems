package distributed.systems.zookeeper.monitors;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.LinkedList;
import java.util.List;

public class ChildrenMonitor implements Watcher, AsyncCallback.StatCallback {
    private final ZooKeeper zooKeeper;
    private final String node;
    private final List<String> children;

    public ChildrenMonitor(ZooKeeper zooKeeper, String node) {
        this.zooKeeper = zooKeeper;
        this.node = node;
        this.children = new LinkedList<>();

        this.zooKeeper.exists(node, true, this, null);
    }

    public static int printChildren(ZooKeeper zooKeeper, String node, int level) {
        int result = 0;

        try {
            List<String> children = zooKeeper.getChildren(node, null);

            String prefix = "\t".repeat(level);
            System.out.println(prefix + node);
            result += children.size();

            for (String child : children) {
                String childNode = node + "/" + child;
                result += printChildren(zooKeeper, childNode, level + 1);
            }
        } catch (KeeperException | InterruptedException e) {
            System.out.println("Error while printing children");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        KeeperException.Code code = KeeperException.Code.get(i);
        if (code == KeeperException.Code.OK) {
            try {
                List<String> currentChildren = zooKeeper.getChildren(node, this);
                children.retainAll(currentChildren);
                currentChildren.removeAll(children);

                if (!currentChildren.isEmpty()) {
                    System.out.println("New children found");

                    for (String child : currentChildren) {
                        children.add(child);
                        String childPath = node + "/" + child;
                        zooKeeper.exists(childPath, true, new ChildrenMonitor(zooKeeper, childPath), null);
                    }

                    int childrenNumber = printChildren(zooKeeper, "/z", 0);
                    System.out.printf("\nChildren number: %d\n", childrenNumber);
                }
            } catch (KeeperException | InterruptedException e) {
                System.out.println("Error with reading children list");
                e.printStackTrace();
            }
        } else {
            zooKeeper.exists(node, true, this, null);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        zooKeeper.exists(node, true, this, null);
    }
}
