package distributed.systems.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final String HOST_PORT = "127.0.0.1:2181";
    private static final String NODE = "/z";
    private static final String EXTERNAL_PROGRAM = "./external.sh";

    public static void main(String[] args) {
        try {
            NodeMonitor nodeMonitor = new NodeMonitor(HOST_PORT, NODE, EXTERNAL_PROGRAM);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                String input = reader.readLine();
                if(input.equalsIgnoreCase("print")) {
                    ChildrenMonitor.printChildren(new ZooKeeper(HOST_PORT, 3000, null), NODE, 0);
                }
                if(input.equalsIgnoreCase("q"))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Error with creating DataMonitor");
            e.printStackTrace();
        }
    }
}
