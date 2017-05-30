package zmq.test;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class SubTest {
	
	public static void main(String[] args) {
		ZContext context = new ZContext();
		Socket socket = context.createSocket(ZMQ.PULL);
		
		socket.connect("tcp://localhost:5558");
		
		while(true) {
			System.out.println(socket.recv());
		}
	}
}
