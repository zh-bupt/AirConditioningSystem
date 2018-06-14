package server.manager;

import org.json.JSONException;
import org.json.JSONObject;
import server.Processor;
import server.StringUtils;
import server.simpleclass.Request;
import server.simpleclass.RoomState;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RequestManager {
    private static RequestManager requestManager = null;

    // 返回当前正在进行的温控请求map, key 为 room_id, value 为 Request
    private HashMap<String, Request> requestHashMap = new HashMap<>();

    private RequestManager(){}

    public static RequestManager getInstance() {
        if (requestManager == null) requestManager = new RequestManager();
        return requestManager;
    }

    public void handleRequest(JSONObject jsonObject, Socket socket){
        Processor.getInstance().runTask(new Runnable() {
            @Override
            public void run() {
                PrintWriter printWriter = null;
                try {
                    if ("wind_request".equals(jsonObject.getString("type")))
                        newRequest(jsonObject, socket);
                    else if ("stop_wind".equals(jsonObject.getString("type")))
                        stopRequest(jsonObject, socket);
//                    printWriter = new PrintWriter(socket.getOutputStream());
//                    Request request = new Request(jsonObject, server.manager.CustomerManager.getInstance().getRoomId(socket));
//                    int seq = jsonObject.getInt("seq");
//                    String ack;
//                    if (isValid(request)) {
//                        String room_id = server.manager.CustomerManager.getInstance().getRoomId(socket);
//                        if (requestHashMap.containsKey(room_id)) removeRequest(room_id);
//                        requestHashMap.put(room_id, request);
//                        ack  = "{"
//                                + "\"type\":\"wind_request_ack\","
//                                + "\"accept\":1,"
//                                + "\"seq\":" + Integer.toString(seq) + "}";
//                    } else {
//                        ack  = "{"
//                                + "\"type\":\"wind_request_ack\","
//                                + "\"accept\":0,"
//                                + "\"seq\":" + Integer.toString(seq) + "}";
//                    }
//                    ack = server.StringUtils.getHead(ack.length()) + ack;
//                    printWriter.print(ack);
//                    printWriter.flush();
//                    System.out.println(ack);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }

    private void newRequest(JSONObject jsonObject, Socket socket) throws JSONException, IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Request request = new Request(jsonObject, CustomerManager.getInstance().getRoomId(socket));
        int seq = jsonObject.getInt("seq");
        String ack;
        if (isValid(request)) {
            String room_id = CustomerManager.getInstance().getRoomId(socket);
            if (requestHashMap.containsKey(room_id)) removeRequest(room_id);
            requestHashMap.put(room_id, request);
            ack  = "{"
                    + "\"type\":\"wind_request_ack\","
                    + "\"accept\":1,"
                    + "\"seq\":" + Integer.toString(seq) + "}";
        } else {
            ack  = "{"
                    + "\"type\":\"wind_request_ack\","
                    + "\"accept\":0,"
                    + "\"seq\":" + Integer.toString(seq) + "}";
        }
        ack = StringUtils.getHead(ack.length()) + ack;
        printWriter.print(ack);
        printWriter.flush();
        System.out.println(ack);
    }

    private void stopRequest(JSONObject jsonObject, Socket socket) throws JSONException, IOException {
        String room_id = CustomerManager.getInstance().getRoomId(socket);
        int seq = jsonObject.getInt("seq");
        String ack;
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        if (room_id != null) {
            Request request = removeRequest(room_id);
            if (request != null) {
                ack = "{"
                        + "\"type\":\"stop_wind_ack\","
                        + "\"accept\":1,"
                        + "\"seq\":" + Integer.toString(seq) + "}";
            } else {
                ack = "{"
                        + "\"type\":\"stop_wind_ack\","
                        + "\"accept\":0,"
                        + "\"seq\":" + Integer.toString(seq) + "}";
            }
        } else {
            ack = "{"
                    + "\"type\":\"stop_wind_ack\","
                    + "\"accept\":0,"
                    + "\"seq\":" + Integer.toString(seq) + "}";
        }
        ack = StringUtils.getHead(ack.length()) + ack;
        printWriter.print(ack);
        printWriter.flush();
        System.out.println(ack);
    }

    private boolean isValid(Request request){
        return true;
    }

    public Request removeRequest(String room_id) {
        Request request = requestHashMap.remove(room_id);
        if (request != null) {
            // TODO 将request存入数据库
            RoomState state = StateManager.getInstance().getState(request.getRoomId());
            request.setStopTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (state != null) request.setEndTemp(state.getCurrentTemperature());
            System.out.println(request.toString());
        }
        return request;
    }

    /*
     * @Description getRequest 返回指定房间当前的温控请求
     * @Param room_id 房间号
     * @Return Request 对应房间号的Request, 如果当前没有Request则返回null
     */
    public Request getRequest(String room_id) { return requestHashMap.get(room_id); }

    /*
     * @Description getRequestMap 返回当前正在进行的温控请求map
     * @Param
     * @Return HashMap<String, Request>
     */
    public HashMap<String, Request> getRequestMap() {
        return requestHashMap;
    }
}
