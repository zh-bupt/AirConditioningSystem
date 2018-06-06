public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TCPServer server = new TCPServer();
        Thread t = new Thread(server);
        t.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        server.broadCast("haha");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
