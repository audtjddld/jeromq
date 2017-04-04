package custom;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class SubscriberImpl implements Subscriber 
{

	private Socket subscriber;
	private String topicId;

	public SubscriberImpl(String address, String topicId) throws Exception 
	{
		if (address == null) {
			throw new Exception("address is null");
		}
		if (topicId == null) {
			throw new Exception("topicId is null");
		}
		this.topicId = topicId;
		this.subscriber = new ZMQConfig().address(address).type(2).build();
		subscriber.subscribe(topicId.getBytes(ZMQ.CHARSET));
	}

	@Override
	public void getMessage() 
	{

	}

	@Override
	public String recvStr() 
	{
		return subscriber.recvStr();
	}

	@Override
	public String getTopicId() 
	{
		return topicId;
	}

}
