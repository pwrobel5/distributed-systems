package distributed.systems.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.*;

public class DataMonitor implements Watcher, AsyncCallback.StatCallback {
    private final ZooKeeper zooKeeper;
    private final String zNode;
    private final String externalProgram;
    private Process externalProcess;

    public DataMonitor(String hostPort, String zNode, String externalProgram) throws IOException {
        this.zooKeeper = new ZooKeeper(hostPort, 3000, this);
        this.zNode = zNode;
        this.externalProgram = externalProgram;
        this.externalProcess = null;

        zooKeeper.exists(zNode, true, this, null);
        System.out.println("Created monitor");
    }

    public void processResult(int i, String s, Object o, Stat stat) {
        KeeperException.Code code = KeeperException.Code.get(i);
        switch (code) {
            case OK -> {
                System.out.println("Node exists!");

                try {
                    System.out.println("" + zooKeeper.getChildren(zNode, true));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (externalProcess == null) {
                    System.out.println("Trying to execute external process...");

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
                System.out.println("Node does not exist");

                if (externalProcess != null) {
                    externalProcess.destroy();
                    //externalProcess.waitFor();
                    externalProcess = null;
                    System.out.println("External process killed");
                }
            }
            case SESSIONEXPIRED, NOAUTH -> System.out.println("Session expired or no authentication");
            default -> zooKeeper.exists(zNode, true, this, null);
        }

        System.out.println("Process result");
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Process");
        zooKeeper.exists(zNode, true, this, null);
    }
}
