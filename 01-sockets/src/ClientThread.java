public class ClientThread implements Runnable {
    private String clientNick;

    public ClientThread(String clientNick) {
        this.clientNick = clientNick;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
