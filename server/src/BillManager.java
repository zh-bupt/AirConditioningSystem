import simpleclass.Request;

import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class BillManager implements Observer {
    private HashMap<String, Float> billMap =
            new HashMap<>();

    private static BillManager billManager = null;
    private BillManager() {

    }

    public static BillManager getInstance() {
        if (billManager == null) billManager = new BillManager();
        return billManager;
    }

    /*
     * @Description remove 移除账单记录(用户结账后)
     * @Param room_id 被移除的房间号
     * @Return float 被移除的账单的能耗
     */
    public float remove(Socket socket) {
        if (billMap.containsKey(socket)) return billMap.remove(socket);
        return -1;
    }

    public void addBill(String room_id) {
        if (!billMap.containsKey(room_id)) billMap.put(room_id, Float.valueOf(0));
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update bill");
        for (String room_id : billMap.keySet()) {
            Request request = RequestManager.getInstance().getRequest(room_id);
            if (request == null) continue;
            String wind_power = request.getWindPower();
            Float pre = billMap.get(room_id), cur;
            if (wind_power == "high") {
                cur = Float.valueOf(pre.floatValue() + (float) (0.8 / 60));
            } else if (wind_power == "medium") {
                cur = Float.valueOf(pre.floatValue() + (float) (1.0 / 60));
            } else {
                cur = Float.valueOf(pre.floatValue() + (float) (1.3 / 60));
            }
            billMap.replace(room_id, cur);
        }
    }

    public HashMap<String, Float> getBillMap() {
        return billMap;
    }
}
