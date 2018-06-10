import simpleclass.Request;

import java.net.Socket;

public class RequestTask extends BaseTask {

    public RequestTask(Request request, Socket socket) {
        super(socket);
    }

    @Override
    public void run() {

    }
}
