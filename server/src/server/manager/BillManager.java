package server.manager;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.TCPServer;
import server.mapper.BillMapper;
import server.simpleclass.Bill;
import server.simpleclass.Request;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class BillManager implements Observer {
    private HashMap<String, Bill> billMap =
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
    public Bill remove(String room_id) {
        if (billMap.containsKey(room_id)) {
            Bill bill = billMap.remove(room_id);
            try {
                new BillMapper().insert(bill);
            } catch (SQLServerException e) {
                e.printStackTrace();
            }
            return bill;
        }
        return null;
    }

    public void addBill(String room_id) {
        if (!billMap.containsKey(room_id)) billMap.put(room_id, new Bill(room_id));
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("update bill");
        for (String room_id : billMap.keySet()) {
            Request request = RequestManager.getInstance().getRequest(room_id);
            if (request == null) continue;
            String wind_power = request.getWindPower();
            float pre = billMap.get(room_id).getElectricity();
            billMap.get(room_id).setElectricity(pre + getElectricityPrice(wind_power) / 60);
        }
    }

    /*
     * @Description getBillMap 得到所有房间的账单信息
     * @Param
     * @Return HashMao<String, Float> 所有房间账单信息
     */
    public HashMap<String, Bill> getBillMap() {
        return billMap;
    }

    /*
     * @Description getBill 得到每个房间的账单, 包含电量和费用
     * @Param room_id 房间号
     * @Return Map<String, String> 账单
     */
    public Bill getBill(String room_id) {
        return billMap.get(room_id);
    }

    private float getElectricityPrice(String wind_power) {
        if (wind_power == "high") return server.getHigh();
        else if (wind_power == "medium") return server.getMedium();
        else return server.getLow();
    }
}
