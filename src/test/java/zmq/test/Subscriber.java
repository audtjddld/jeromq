package zmq.test;

public interface Subscriber {
	//String getMessage();
	void getMessage();
	
	String recvStr();
	
	String getTopicId();
}
