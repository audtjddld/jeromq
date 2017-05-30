package guide;

import org.zeromq.ZMQ;

//
//  Reading from multiple sockets in Java
//  This version uses ZMQ.Poller
//
public class mspoller2 {

    public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        // Connect to task ventilator
        ZMQ.Socket pusher = context.socket(ZMQ.PUSH);
        pusher.bind("tcp://localhost:5557");

        //  Connect to weather server
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://localhost:5556");

        //  Initialize poll set
        ZMQ.Poller items = context.poller(2);
        items.register(pusher, ZMQ.Poller.POLLIN);
        items.register(publisher, ZMQ.Poller.POLLIN);

        //  Process messages from both sockets
        while (!Thread.currentThread ().isInterrupted ()) {
                pusher.send("1234");
            	publisher.send("1341234");
        }
        pusher.close ();
        context.term ();
    }
}
