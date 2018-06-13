import org.json.JSONObject;
import simpleclass.RoomState;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private static StateManager stateManager = null;
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

    public Map<String, RoomState> getState() {
        return stateMap;
    }
}
