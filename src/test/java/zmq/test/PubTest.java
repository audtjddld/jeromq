package zmq.test;

import java.util.concurrent.TimeUnit;

import com.ichatt.noriter.protobuf.NoriterScheduleMessage.MailGetItemMessagePacket;

public class PubTest {
	public static void main(String[] args) throws Exception {
		Publisher pub = new PublisherImpl("tcp://localhost:5558");

		MailGetItemMessagePacket packet = MailGetItemMessagePacket.newBuilder().setMailId(1).setUserId(1).build();

		// TimeUnit.SECONDS.sleep(10);
		// TimeUnit.MICROSECONDS.sleep(5);

		while (true) {
			pub.sendMessage(packet.toByteArray());
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
