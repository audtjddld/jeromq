package zmq.test;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class PublisherImpl implements Publisher {
	
	private Socket publisher;
	private String topicId;
	
	public PublisherImpl(String address, String topicId) throws Exception {
		if(address == null) {
			throw new Exception ("address is null");
		}
		if(topicId == null) {
			throw new Exception ("topicId is null");
		}
		this.topicId = topicId;
		this.publisher = new ZMQConfig()
							.address(address)
							.type(ZMQ.PUB)
							.build();
	}
	
	@Override
	public void sendMessage(String message) {
			publisher.send(topicId, ZMQ.SNDMORE);
			publisher.send(message);
	}
}
