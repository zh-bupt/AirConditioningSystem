package server;

import org.json.JSONException;
import org.json.JSONObject;
import server.manager.CustomerManager;
import server.manager.RequestManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements Runnable {
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private int clientCount = 0;
    private List<Socket> sockets = new ArrayList<>();
    private static TCPServer tcpServer = null;
    private String mode;
    private float price;
    private float low;
    private float medium;
    private float high;
    private TimerSubject timerSubject;
    private String configFilePath = "src/server/configure.json";

    public static TCPServer getInstance() {
        if (tcpServer == null) tcpServer = new TCPServer();
        return tcpServer;
    }

    private TCPServer() {
    }

    public void init() {
        JSONObject configJson = readConfig();
        int queryInterval = 5, billSendInterval = 2;
        float pprice = 5, llow = (float) 0.8, mmedium = 1, hhigh = (float) 1.3;
        String mmode = "summer";
        if (configJson != null) {
            try {
                queryInterval = configJson.getInt("query_interval");
                billSendInterval = configJson.getInt("bill_send_interval");
                mmode = configJson.getString("mode");
                pprice = configJson.getFloat("price");
                llow = configJson.getFloat("low");
                mmedium = configJson.getFloat("medium");
                hhigh = configJson.getFloat("high");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.price = pprice;
        this.low = llow;
        this.medium = mmedium;
        this.high = hhigh;
        this.mode = mmode;
        this.timerSubject = new TimerSubject(queryInterval, billSendInterval, 1, 10);
    }

    private JSONObject readConfig() {
        File file = new File(configFilePath);
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
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean changeConfig(JSONObject jsonObject) {
        Writer writer = null;
        boolean result = false;
        try {
            writer = new FileWriter(configFilePath);
            String jsonString = jsonObject.toString();
            writer.write(jsonString);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public void run() {
        init();
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while(true) {
                Socket socket = serverSocket.accept();
                clientCount++;
                System.out.println("第" + clientCount + "个连接,IP地址是：" + socket.getInetAddress());
                // 为每一个socket连接分配一个线程
                cachedThreadPool.execute(new SocketThread(socket));
                addSocket(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSocket(Socket socket) {
        sockets.add(socket);
    }

    public void removeSocket(Socket socket) {
        System.out.println("Remove socket:" + socket.toString());
        clientCount--;
        if (sockets.remove(socket)) {
            String room_id = CustomerManager.getInstance().removeCustomer(socket);
            RequestManager.getInstance().removeRequest(room_id);
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

    public void shutDown() {
        for (Socket s : sockets) removeSocket(s);
    }
}
