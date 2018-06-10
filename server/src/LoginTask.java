import simpleclass.Customer;

import java.net.Socket;

public class LoginTask extends BaseTask {
    private Customer customer = null;

    public LoginTask(Customer customer, Socket socket) {
        super(socket);
        this.customer = customer;
    }

    @Override
    public void run() {
//        System.out.println("Welcome customer " + customer.getId() + " in room " + customer.getRoom_id());
//        try {
//            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//            String ack = "{\"type\":\"login_ack\",\"result\":\"succeed\"}";
//            ack = StringUtils.getHead(ack.length()) + ack;
//            printWriter.print(ack);
//            printWriter.flush();
//            System.out.println(ack);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
