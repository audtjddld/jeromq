package custom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Tester t = new Tester();
		executorService.execute(t);
		TimeUnit.SECONDS.sleep(5);

		System.out.println("===== stop =====");
		t.setRun(true);

		TimeUnit.SECONDS.sleep(3);

		t.setRun(false);
		executorService.execute(t);
	}
}

class Tester implements Runnable {
	private volatile boolean run;

	@Override
	public void run() {
		while (!run) {
			System.out.println("im running");
			try {
				TimeUnit.SECONDS.sleep(1L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
}