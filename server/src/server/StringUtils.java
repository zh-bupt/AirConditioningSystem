package server;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class StringUtils {
    public static String getHead(int length) {
        if (length > 9999) return "9999";
        if (length < 0) return "0000";
        char[] temp = {'0', '0', '0', '0'};
        int index = 3;
        while(length > 0) {
            temp[index] = (char)('0' + length % 10);
            length /= 10;
            index--;
        }
        return new String(temp);
    }

    public static String getSendString(Map<String, Object> jsonMap) {
        try {
            String result = new JSONObject(jsonMap).toString();
            result = getHead(result.length()) + result;
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
