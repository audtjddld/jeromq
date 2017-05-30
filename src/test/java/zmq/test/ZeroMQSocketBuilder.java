package zmq.test;

import java.util.concurrent.TimeUnit;

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
public class ZeroMQSocketBuilder {
	private ZContext context;
	private Socket socket;
	private String address;
	private Integer role;
	private Integer hwm;
	private Boolean bind;

	public ZeroMQSocketBuilder(String address) throws Exception {
		if (address == null) {
			throw new Exception("address is null");
		}
		this.context = new ZContext();
		this.address = address;
	}

	public ZeroMQSocketBuilder role(Integer role) {
		this.role = role;
		return this;
	}

	public ZeroMQSocketBuilder connect() {
		this.bind = false;
		return this;
	}

	public ZeroMQSocketBuilder bind() {
		this.bind = true;
		return this;
	}

	public ZeroMQSocketBuilder hwm(Integer hwm) throws Exception {
		if (hwm == null) {
			throw new Exception("HWM is null!");
		}
		this.hwm = hwm;
		return this;
	}

	public Socket build() throws Exception {
		if (address == null) {
			throw new Exception("address is null");
		}
		if (role == null) {
			throw new Exception("role is null");
		}
		if (bind == null) {
			throw new Exception("bind or connect is not defined.");
		}

		this.socket = context.createSocket(this.role);
		if (hwm != null && role == ZMQ.PUSH) {
			this.socket.setHWM(hwm);
		}
		if (bind) {
			socket.bind(address);
			if (role == ZMQ.SUB) {
				socket.subscribe(ZMQ.SUBSCRIPTION_ALL);
			}
		} else { // connect
			socket.connect(address);
		}

		return this.socket;
	}

	public ZContext getContext() {
		return this.context;
	}

	public static void main(String[] args) throws Exception {
		String address = "tcp://localhost:5557";
		// subscriber
		ZeroMQSocketBuilder zeroMQsocket = new ZeroMQSocketBuilder(address).role(ZMQ.PULL).bind();
		Socket subscriber = zeroMQsocket.build();
		ZContext context = zeroMQsocket.getContext();

		// publisher
		zeroMQsocket = new ZeroMQSocketBuilder(address).role(ZMQ.PUSH).hwm(10000).connect();
		Socket publisher = zeroMQsocket.build();
		ZContext context2 = zeroMQsocket.getContext();

		TimeUnit.SECONDS.sleep(2);

		publisher.send("1234", 0);
	
		System.out.println(subscriber.recv());

		subscriber.close();
		context.destroy();

		publisher.close();
		context2.close();
	}
}
