package server.manager;

import server.TCPServer;
import server.simpleclass.Request;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class BillManager implements Observer {
    private HashMap<String, Float> billMap =
            new HashMap<>();

    private static BillManager billManager = null;
    private float high;
    private float medium;
    private float low;
    private TCPServer server;

    private BillManager() {
        server = TCPServer.getInstance();
        this.high = server.getHigh();
        this.medium = server.getMedium();
        this.low = server.getLow();
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
    public float remove(String room_id) {
        if (billMap.containsKey(room_id)) return billMap.remove(room_id);
        return -1;
    }

    public void addBill(String room_id) {
        if (!billMap.containsKey(room_id)) billMap.put(room_id, Float.valueOf(0));
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("update bill");
        for (String room_id : billMap.keySet()) {
            Request request = RequestManager.getInstance().getRequest(room_id);
            if (request == null) continue;
            String wind_power = request.getWindPower();
            Float pre = billMap.get(room_id), cur;
            if (wind_power == "high") {
                cur = Float.valueOf(pre.floatValue() + low / 60);
            } else if (wind_power == "medium") {
                cur = Float.valueOf(pre.floatValue() + medium / 60);
            } else {
                cur = Float.valueOf(pre.floatValue() + high / 60);
            }
            billMap.replace(room_id, cur);
        }
    }

    /*
     * @Description getBillMap 得到所有房间的账单信息
     * @Param
     * @Return HashMao<String, Float> 所有房间账单信息
     */
    public HashMap<String, Float> getBillMap() {
        return billMap;
    }

    /*
     * @Description getBill 得到每个房间的账单, 包含电量和费用
     * @Param room_id 房间号
     * @Return Map<String, String> 账单
     */
    public Map<String, Float> getBill(String room_id) {
        Float f = billMap.get(room_id);
        float elec = 0;
        if (f != null) elec = f.floatValue();
        Map<String, Float> map = new HashMap<>();
        map.put("electricity", f);
        map.put("cost", Float.valueOf(f * TCPServer.getInstance().getPrice()));
        return map;
    }
}
