package zmq.test;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

/**
 * <h1>zero mq configration</h1>
 * <p>
 * <b>Note:</b> ichatt zeromq configuration
 * 
 * @author msjung
 */
public class ZeroMQSocket {
	private ZContext context;
	private Socket socket;
	private String address;
	private int port;
	private int roll;
	
	
	public ZeroMQSocket(String address, Integer roll, Integer port) throws Exception {
		if (address == null) {
			throw new Exception("address is null");
		}
		if (roll == null) {
			throw new Exception("roll is null");
		}
		if (port == null) {
			throw new Exception("port is null");
		}

		this.context = new ZContext();
		this.port = port;
		this.address = address;
		this.roll = roll;
		this.socket = context.createSocket(roll);
		connect();
	}

	private void connect() {
		if(roll == 1) {
			socket.bind(address + ":" + port);
		} else if(roll == 2) {
			socket.connect(address + ":" + port);	
		}
		
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void disConnect() {
		this.socket.close();
		this.context.destroy();
	}

	public ZContext getContext() {
		return this.context;
	}
}
