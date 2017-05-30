package guide;

import org.zeromq.ZMQ;

//
//  Reading from multiple sockets in Java
//  This version uses ZMQ.Poller
//
public class mspoller {

    public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        // Connect to task ventilator
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5557");

        //  Connect to weather server
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:5556");
        subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);

        //  Initialize poll set
        ZMQ.Poller items = context.poller(2);
        items.register(receiver, ZMQ.Poller.POLLIN);
        items.register(subscriber, ZMQ.Poller.POLLIN);

        //  Process messages from both sockets
        while (!Thread.currentThread ().isInterrupted ()) {
            byte[] message;
            int rc = items.poll();
            //System.out.println(rc);
            if(rc == -1) {
            	throw new Exception("message not found");
            }
            if (items.pollin(0)) {
                message = receiver.recv(0);
                System.out.println(message);
            }
            if (items.pollin(1)) {
                message = subscriber.recv(0);
                System.out.println(message);
            }
        }
        receiver.close ();
        context.term ();
    }
}
