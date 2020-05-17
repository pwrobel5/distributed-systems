package distributed.systems.zookeeper;

import distributed.systems.zookeeper.monitors.NodeMonitor;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    private static final String HOST_PORT = "127.0.0.1:2181";
    private static final String NODE = "/z";
    private static final String EXTERNAL_PROGRAM = "./external.sh";

    public static void printChildren(ZooKeeper zooKeeper, String node, int level) {
        try {
            List<String> children = zooKeeper.getChildren(node, null);

            String prefix = "\t".repeat(level);
            System.out.println(prefix + node);

            for (String child : children) {
                String childNode = node + "/" + child;
                printChildren(zooKeeper, childNode, level + 1);
            }
        } catch (KeeperException | InterruptedException e) {
            System.out.println("Error while printing children");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            NodeMonitor nodeMonitor = new NodeMonitor(HOST_PORT, NODE, EXTERNAL_PROGRAM);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String input = reader.readLine();
                if (input.equalsIgnoreCase("print")) {
                    Main.printChildren(new ZooKeeper(HOST_PORT, 3000, null), NODE, 0);
                }
                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Exiting program...");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error with creating DataMonitor");
            e.printStackTrace();
        }
    }
}
