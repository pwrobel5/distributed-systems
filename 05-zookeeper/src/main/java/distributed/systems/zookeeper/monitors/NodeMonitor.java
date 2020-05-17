package distributed.systems.zookeeper.monitors;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class NodeMonitor implements Watcher, AsyncCallback.StatCallback {
    private final ZooKeeper zooKeeper;
    private final String zNode;
    private final String externalProgram;
    private Process externalProcess;

    public NodeMonitor(String hostPort, String zNode, String externalProgram) throws IOException {
        this.zooKeeper = new ZooKeeper(hostPort, 3000, this);
        this.zNode = zNode;
        this.externalProgram = externalProgram;
        this.externalProcess = null;

        zooKeeper.exists(zNode, true, this, null);
        zooKeeper.exists(zNode, true, new ChildrenMonitor(zooKeeper, zNode), null);
    }

    public void processResult(int i, String s, Object o, Stat stat) {
        KeeperException.Code code = KeeperException.Code.get(i);
        switch (code) {
            case OK -> {
                if (externalProcess == null) {
                    System.out.printf("Node %s exists\nTrying to execute external process...\n", zNode);

                    try {
                        ProcessBuilder builder = new ProcessBuilder(externalProgram);
                        builder.directory(new File(System.getProperty("user.dir")));
                        externalProcess = builder.start();

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(externalProcess.getInputStream()));

                        new Thread(() -> {
                            String line;
                            try {
                                while ((line = bufferedReader.readLine()) != null) {
                                    System.out.println("[EXTERNAL] " + line);
                                }
                            } catch (IOException e) {
                                System.out.println("Error with reading output from external process");
                                e.printStackTrace();
                            }
                        }).start();

                        System.out.println("External process started");
                    } catch (IOException e) {
                        System.out.println("Error with external process execution");
                        e.printStackTrace();
                    }
                }
            }
            case NONODE -> {
                if (externalProcess != null) {
                    System.out.printf("Node %s does not exist\nTrying to kill external process...\n", zNode);
                    externalProcess.destroy();
                    externalProcess = null;
                    System.out.println("External process killed");
                }
            }
            case SESSIONEXPIRED, NOAUTH -> System.out.println("Session expired or no authentication");
            default -> {
                if (externalProcess != null) {
                    externalProcess.destroy();
                    externalProcess = null;
                }

                zooKeeper.exists(zNode, true, this, null);
            }
        }
    }

    public void process(WatchedEvent watchedEvent) {
        zooKeeper.exists(zNode, true, this, null);
    }
}
