package zmq.test;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class PublisherImpl implements Publisher {

	private Socket publisher;

	public PublisherImpl(String address) throws Exception {
		if (address == null) {
			throw new Exception("address is null");
		}
		this.publisher = new ZMQConfig().address(address).type(ZMQ.PUSH).build();
	}

	@Override
	public void sendMessage(String message) {
		System.out.println(message);
		publisher.send(message);
	}

	@Override
	public void sendMessage(byte[] message) {
		System.out.println(message);
		publisher.send(message);
	}
}
