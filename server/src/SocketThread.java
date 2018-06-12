
import java.io.*;
import java.net.Socket;

public class SocketThread implements Runnable {
    private Socket socket;
    private InputStream ins = null;
    private OutputStream outs = null;

    public SocketThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            //获取服务端输入的消息
            ins = socket.getInputStream();
            DataInputStream din = new DataInputStream(ins);
            //服务端返回的消息
            outs = socket.getOutputStream();
            while (true) {
                byte[] lengthBuf = new byte[4];
                int result = din.read(lengthBuf);
                if (result == 0) continue;
                if (result == -1) break;
                String lengthString = new String(lengthBuf);
                try {
                    int length = Integer.parseInt(lengthString);
                    byte[] dataBuf = new byte[length];
                    result = din.read(dataBuf);
                    if (result != length) continue;
                    if (result == -1) break;
                    String jsonString = new String(dataBuf);
                    CustomerFacade.getInstance().handleRequest(jsonString, socket);
//                    Processor.getInstance().runTask(TaskFactory.getTask(jsonString, socket));
                } catch (NumberFormatException e){
                    byte[] dataBuf = new byte[1024];
                    din.read(dataBuf);
                    System.out.println("NumberFormatException");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                ins.close();
                outs.close();
                socket.close();
                TCPServer.getInstance().removeSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("socket closed");
    }
}
