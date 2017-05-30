package zmq.test;

public interface Publisher
{
    void sendMessage(String message);
    
    void sendMessage(byte[] message);
}
