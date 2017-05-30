package zmq.test;

import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public class ZMQConfig {
	private String address;
	private Integer type;
	private Socket socket;
	private ZContext context;

	public ZMQConfig() {
		this.context = new ZContext(1);
	}

	public ZMQConfig address(String address) {
		this.address = address;
		return this;
	}

	public ZMQConfig type(int type) {
		this.type = type;
		return this;
	}

	public Socket build() throws Exception {
		if (address == null) {
			throw new Exception(" address or connection is null ");
		}
		if (type == null) {
			throw new Exception(" type is null ");
		}
		socket = context.createSocket(type);
		socket.setHWM(10);
		socket.bind(address);
		
		return this.socket;
	}
}
