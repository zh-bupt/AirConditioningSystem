package server;

import server.manager.CustomerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements Runnable{
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private int clientCount = 0;
    private List<Socket> socketList = new ArrayList<>();
    private static TCPServer tcpServer = null;

    public static TCPServer getInstance() {
        if (tcpServer == null) tcpServer = new TCPServer();
        return tcpServer;
    }

    private TCPServer() {
        TimerSubject timerSubject = new TimerSubject(5, 2, 1);
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("第" + clientCount + "个连接,IP地址是：" + socket.getInetAddress());
                /**
                 * 服务端使用多线程方便多客户端的连接
                 * 这里将服务端的socket传给内部类，方便每个客户端都创建一个线程
                 */
                cachedThreadPool.execute(new SocketThread(socket));
                addSocket(socket);
                sendData(socket, "Hello!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSocket(Socket socket) {
        socketList.add(socket);
        clientCount++;
    }

    public void removeSocket(Socket socket) {
        if (socketList.remove(socket)) {
            CustomerManager.getInstance().removeCustomer(socket);
            clientCount--;
        }
    }

    public void sendData(Socket socket, String data) {
        try{
            System.out.println("send to" + socket.toString());
            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            writer.print(StringUtils.getHead(data.length()) + data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
