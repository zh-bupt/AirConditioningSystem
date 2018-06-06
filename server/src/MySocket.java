import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket extends Socket {
    public void sendData(String data) {
        try{
            PrintWriter writer=new PrintWriter(this.getOutputStream());
            writer.println(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
