package zmq.test;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

/**
 * <h1>zeromq publisher</h1>
 * <p>
 * <b>Note:</b> zeromq publisher
 * 
 * @author msjung
 */
public class ZeroMQPublisher {
	private ZeroMQSocket publisherSocket;
	private ZContext context;
	private Socket publisher;

	public ZeroMQPublisher(String address, int port) {
		try {
			this.publisherSocket = new ZeroMQSocket(address, ZMQ.PUB, port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.publisher = publisherSocket.getSocket();
		this.context = publisherSocket.getContext();
	}

	/**
	 * send message include topicId and message.
	 * 
	 * @param
	 * @return
	 * @exception @see
	 */

	public void sendMessage(byte[] message) {
		this.publisher.send(message);
		close();
	}

	/**
	 * message must be delivered and closed.
	 * 
	 * @param
	 * @return
	 * @exception @see
	 */
	private void close() {
		if (publisher != null)
			publisher.close();
		if (context != null)
			context.destroy();
	}
}