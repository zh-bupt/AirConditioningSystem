import java.net.Socket;

public abstract class BaseTask implements Runnable {
    protected Socket socket = null;
    public BaseTask(Socket socket) {
        this.socket = socket;
    }
}
