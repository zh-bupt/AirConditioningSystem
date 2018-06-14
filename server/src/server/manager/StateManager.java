package server.manager;

import org.json.JSONObject;
import server.simpleclass.RoomState;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private static StateManager stateManager = null;

    // 房间状态map, key为room_id, value为房间状态RoomState
    private Map<String, RoomState> stateMap = new HashMap<>();

    private StateManager(){}

    public static StateManager getInstance() {
        if (stateManager == null) stateManager = new StateManager();
        return stateManager;
    }

    public void updateState(JSONObject jsonObject, Socket socket) {
        String roomId = CustomerManager.getInstance().getRoomId(socket);
        System.out.println("Room " + roomId + " " + jsonObject.toString());
        if (roomId != null) {
            RoomState state = new RoomState(jsonObject, roomId);
            if (stateMap.containsKey(roomId)) stateMap.replace(roomId, state);
            else stateMap.put(roomId, state);
        }
    }

    public RoomState removeRoom(String room_id) {
        RoomState state = stateMap.remove(room_id);
        return state;
    }

    /*
     * @Description getStateMap 返回当前连接的所有房间的状态
     * @Param
     * @Return Map<String, RoomState>
     */
    public Map<String, RoomState> getStateMap() {
        return stateMap;
    }

    /*
     * @Description getState 得到指定房间号的房间状态
     * @Param room_id 房间号
     * @Return RoomState 房间状态
     */
    public RoomState getState(String room_id) {
        return stateMap.get(room_id);
    }
}
