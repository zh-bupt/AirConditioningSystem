package server;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TCPServer server = TCPServer.getInstance();
        Thread t = new Thread(server);
        t.start();
    }
}
