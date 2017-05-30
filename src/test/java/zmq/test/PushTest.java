package zmq.test;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class PushTest {
	public static void main(String[] args) throws Exception {
		ZeroMQSocketBuilder builder = new ZeroMQSocketBuilder("tcp://*:5558");
		Socket socket = builder.role(ZMQ.PUSH).bind().build();

		long i = 0;
		while (true) {
			socket.send((i++ + "").getBytes());
			if(i == Long.MAX_VALUE) {
				break;
			}
		}
		
	}
}
