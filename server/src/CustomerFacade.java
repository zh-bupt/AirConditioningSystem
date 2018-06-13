import org.json.JSONException;
import org.json.JSONObject;
import simpleclass.Customer;
import java.net.Socket;

/*
* CustomerFacade: 住客请求的外观类(单例模式)
* 根据socket提交的请求, 调用相应的处理类
 */
public class CustomerFacade {

    private static CustomerFacade customerFacade = null;
    private CustomerFacade(){}

    public static CustomerFacade getInstance() {
        if (customerFacade == null) customerFacade = new CustomerFacade();
        return customerFacade;
    }

    /*
     * @Description handleRequest 处理相应的请求
     * @param request 请求的字符串
     * @param socket 发起请求的socket
     */
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
