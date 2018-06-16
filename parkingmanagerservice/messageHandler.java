package parkingmanagerservice;

public class messageHandler {
    public static void handleMessage(messages msg) {
        System.out.println("Handling message :\n");
        if (msg == null) {
            System.out.println("Message is null :\n");
            return;
        }
        msg.print();

        System.out.println("Decides to do nothing.\n");
    }
}
