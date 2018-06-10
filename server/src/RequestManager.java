import org.json.JSONObject;

import java.net.Socket;

public class RequestManager {
    private static RequestManager requestManager = null;

    private RequestManager(){}

    public static RequestManager getInstance() {
        if (requestManager == null) requestManager = new RequestManager();
        return requestManager;
    }

    public void handleRequest(JSONObject jsonObject, Socket socket){
        System.out.println("Handle request:" + jsonObject.toString());
    }
}
