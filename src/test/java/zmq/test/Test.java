package zmq.test;

import zmq.ZError;

public class Test {
	public static void main(String[] args) throws Exception {

		new Thread(new Pub()).start();

		/*
		 * / Sub sub = new Sub("ichatt"); new Thread(sub).start(); /
		 **/
	}
}

class Pub implements Runnable {
	Publisher pub;
	public Pub() {
		connect();
	}
	public void connect() {
		try {
			pub = new PublisherImpl("tcp://localhost:5558");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int i = 0;
		String message="";
		while (true) {
			try {
				/*
				MailGetItemMessagePacket packet = MailGetItemMessagePacket.newBuilder().setUserId(1).setMailId(2)
						.build();
				Schedule message = Schedule.newBuilder().setType(Type.GETITEM)
						.setExtension(MailGetItemMessagePacket.cmd, packet).build();

				TimeUnit.MICROSECONDS.sleep(10);

				pub.sendMessage(message.toByteArray());
				*/
				message = i+++"";
				pub.sendMessage(message);
				
			} catch (ZError.IOException ie) {
				System.out.println("buffer full");
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}
}

class Sub implements Runnable {
	Subscriber sub;

	public Sub(String topicId) throws Exception {
		sub = new SubscriberImpl(
				"tcp://ichatt-test-classic-load-balance-377765445.ap-northeast-2.elb.amazonaws.com:5555", topicId);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String topic = sub.recvStr();
				String data = sub.recvStr();
				System.out.println(String.format("subs : %s , message : %s received", topic, data));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
