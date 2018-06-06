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
    private static List<Socket> socketList = new ArrayList<>();

    public TCPServer() {}

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("第" + clientCount + "个连接,IP地址是：" + socket.getInetAddress());
                clientCount++;
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

    public static void addSocket(Socket socket) {
        socketList.add(socket);
    }

    public static void removeSocket(Socket socket) {
        socketList.remove(socket);
    }

    public void sendData(Socket socket, String data) {
        try{
            System.out.println("send to" + socket.toString());
            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            writer.print(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadCast(String data) {
        for (Socket s:socketList) {
            sendData(s, data);
        }
    }
}
