import org.json.JSONException;
import org.json.JSONObject;
import simpleclass.Customer;

import java.net.Socket;

public class TaskFactory {
    public static BaseTask getTask(String json, Socket socket) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String type = jsonObject.getString("type");
            if (type != null && type.equals("login")) {
                return new LoginTask(new Customer(jsonObject), socket);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
