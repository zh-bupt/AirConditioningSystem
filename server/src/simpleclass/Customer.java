package simpleclass;

import org.json.JSONException;
import org.json.JSONObject;

/*
* Customer: 住客类，有住客的基本信息
*/
public class Customer {
    private String room_id = null;
    private String id = null;

    public Customer(String room_id, String id){
        this.room_id = id;
        this.id = id;
    }
    public Customer(JSONObject jsonObject) throws JSONException{
        room_id = jsonObject.getString("room_id");
        id = jsonObject.getString("id");
    }

    public String getId() {
        return id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
