package zmq.test;

import java.util.concurrent.TimeUnit;

public class Test {
	public static void main(String[] args) throws Exception {
		/**/
		Pub pub = new Pub("ichatt");
		new Thread(pub).start();
		/**/
		/*/
		Sub sub = new Sub("ichatt");
		new Thread(sub).start();
		/**/
	}
}

class Pub implements Runnable {
	Publisher pub;

	public Pub(String topicId) throws Exception {
		pub = new PublisherImpl("tcp://*:5555", topicId);
	}

	@Override
	public void run() {
		long num = 0L;
		while (true) {
			try {
				pub.sendMessage("hello sub? " + num++);

				TimeUnit.NANOSECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Sub implements Runnable {
	Subscriber sub;

	public Sub(String topicId) throws Exception {
		sub = new SubscriberImpl("tcp://127.0.0.1:5555", topicId);
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
