package zmq.test;

import com.ichatt.noriter.protobuf.NoriterScheduleMessage.MailGetItemMessagePacket;
import com.ichatt.noriter.protobuf.NoriterScheduleMessage.Schedule;
import com.ichatt.noriter.protobuf.NoriterScheduleMessage.Schedule.Type;

public class ZMQPubTest {
	public static void main(String[] args) throws InterruptedException {
			ZeroMQPublisher publisher = new ZeroMQPublisher("tcp://localhost", 5558);

			MailGetItemMessagePacket packet = MailGetItemMessagePacket.newBuilder().setUserId(1).setMailId(2).build();
			Schedule message = Schedule.newBuilder().setType(Type.GETITEM)
					.setExtension(MailGetItemMessagePacket.cmd, packet).build();

			publisher.sendMessage(message.toByteArray());

	}
}
