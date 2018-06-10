import org.json.JSONException;
import org.json.JSONObject;
import org.stjs.javascript.JSON;
import simpleclass.Customer;

import java.net.Socket;

public class CustomerFacade {

    private static CustomerFacade customerFacade = null;
    private CustomerFacade(){}

    public static CustomerFacade getInstance() {
        if (customerFacade == null) customerFacade = new CustomerFacade();
        return customerFacade;
    }

    public void handleRequest(String request, Socket socket) {
        try {
            JSONObject jsonObject = new JSONObject(request);
            String type = jsonObject.getString("type");
            if (type.equals("login"))
                CustomerManager.getInstance().login(new Customer(jsonObject), socket);
            if (type.equals("wind_request"))
                RequestManager.getInstance().handleRequest(jsonObject, socket);
            if (type.equals("stop_wind"))
                RequestManager.getInstance().handleRequest(jsonObject, socket);
            if (type.equals("state_query_ack"))
                StateManager.getInstance().updateState(jsonObject, socket);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
