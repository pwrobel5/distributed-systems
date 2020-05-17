package distributed.systems.zookeeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            DataMonitor dataMonitor = new DataMonitor("127.0.0.1:2181", "/z", "./external.sh");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                String result = reader.readLine();
                if(result.equalsIgnoreCase("q"))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Error with creating DataMonitor");
            e.printStackTrace();
        }
    }
}
