package guide;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
* Synchronized publisher.
*/
public class syncpub{
    /**
     * We wait for 10 subscribers
     */
    protected static int SUBSCRIBERS_EXPECTED = 1;

    public static void main (String[] args) {
        Context context = ZMQ.context(1);

        //  Socket to talk to clients
        Socket publisher = context.socket(ZMQ.PUB);
        publisher.setLinger(500);
        // In 0MQ 3.x pub socket could drop messages if sub can follow the generation of pub messages
        publisher.setSndHWM(0);
        publisher.bind("tcp://*:5561");

        //  Socket to receive signals
        Socket syncservice = context.socket(ZMQ.REP);
        syncservice.bind("tcp://*:5562");

        System.out.println("Waiting subscribers");
        //  Get synchronization from subscribers
        int subscribers = 0;
        while (subscribers < SUBSCRIBERS_EXPECTED) {
            //  - wait for synchronization request
            syncservice.recv(0);

            //  - send synchronization reply
            syncservice.send("", 0);
            subscribers++;
        }
        //  Now broadcast exactly 1M updates followed by END
        System.out.println ("Broadcasting messages");

        int update_nbr;
        for (update_nbr = 0; update_nbr < 1000000; update_nbr++){
            publisher.send("Rhubarb " + update_nbr, 0);
            System.out.println("sent message : " + "Rhubarb " + update_nbr);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        publisher.send("END", 0);

        // clean up
        publisher.close();
        syncservice.close();
        context.term();
    }
}
