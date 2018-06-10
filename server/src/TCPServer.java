import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int seq = 0;
//                try {
//                    while (true) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("type", "state_query");
//                        map.put("seq", seq++);
//                        Thread.sleep(1000);
//                        broadCast(StringUtils.getSendString(map));
////                        System.out.println("Send state query info");
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int seq = 0;
//                while (true) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("type", "bill");
//                    map.put("seq", seq++);
//                    map.put("power", 123);
//                    map.put("money", 12.22);
//                    broadCast(StringUtils.getSendString(map));
//                }
//            }
//        }).start();
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
        if (socketList.remove(socket))
            clientCount--;
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
