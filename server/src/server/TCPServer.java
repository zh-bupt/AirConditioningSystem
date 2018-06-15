package server;

import org.json.JSONException;
import org.json.JSONObject;
import server.manager.CustomerManager;

import java.io.*;
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
    private String mode;
    private float price;
    private float low;
    private float medium;
    private float high;
    private TimerSubject timerSubject;

    public static TCPServer getInstance() {
        if (tcpServer == null) tcpServer = new TCPServer();
        return tcpServer;
    }

    private TCPServer() {}

    public void init() {
        JSONObject configJson = readConfig();
        int queryInterval = 5, billSendInterval = 2, billUpdateInterval = 1;
        float pprice = 5, llow = (float) 0.8, mmedium = 1, hhigh = (float) 1.3;
        String mmode = "summer";
        if (configJson != null) {
            try {
                queryInterval = configJson.getInt("query_interval");
                billSendInterval = configJson.getInt("bill_send_interval");
                billUpdateInterval = configJson.getInt("bill_update_interval");
                mmode = configJson.getString("mode");
                pprice = configJson.getFloat("price");
                llow = configJson.getFloat("low");
                mmedium = configJson.getFloat("medium");
                hhigh = configJson.getFloat("high");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.timerSubject = new TimerSubject(queryInterval, billSendInterval, billUpdateInterval);
        this.price = pprice;
        this.low = llow;
        this.medium = mmedium;
        this.high = hhigh;
        this.mode = mmode;
    }

    private JSONObject readConfig() {
        File file = new File("src/server/configure.json");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[10];
            String jsonString = "";
            int byteRead;
            while ((byteRead = in.read(buffer)) != -1) {
                jsonString = jsonString + new String(buffer);
            }
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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

    public String getMode() {
        return mode;
    }

    public float getPrice() {
        return price;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getLow() {
        return low;
    }

    public float getMedium() {
        return medium;
    }

    public float getHigh() {
        return high;
    }
}
