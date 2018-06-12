import org.json.JSONException;
import org.json.JSONObject;
import simpleclass.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestManager {
    private static RequestManager requestManager = null;

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
                    printWriter = new PrintWriter(socket.getOutputStream());
                    Request request = new Request(jsonObject, CustomerManager.getInstance().getRoomId(socket));
                    int seq = jsonObject.getInt("seq");
                    String ack;
                    if (isValid(request)) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }

    private boolean isValid(Request request){
        return true;
    }
}
