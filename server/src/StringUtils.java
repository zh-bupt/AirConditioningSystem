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
}
