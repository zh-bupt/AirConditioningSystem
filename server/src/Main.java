
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TCPServer server = TCPServer.getInstance();
        Thread t = new Thread(server);
        t.start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int seq = 0;
//                try {
//                    while (true) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("type", "state_query");
//                        map.put("seq", seq++);
//                        String jsonString = new JSONObject(map).toString();
//                        jsonString = StringUtils.getHead(jsonString.length()) + jsonString;
//                        Thread.sleep(1000);
//                        server.broadCast(jsonString);
//                        System.out.println("Send state query info");
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
