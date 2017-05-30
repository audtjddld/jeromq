package guide;

import org.zeromq.ZMQ;

//
//  Reading from multiple sockets in Java
//  This version uses ZMQ.Poller
//
public class mspoller3 {

    public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        // Connect to task ventilator
        ZMQ.Socket pusher = context.socket(ZMQ.PUSH);
        pusher.bind("tcp://localhost:5557");

        //  Connect to weather server
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://localhost:5556");
        
        //  Process messages from both sockets
        while (true) {
                
        }
        
    }
}
