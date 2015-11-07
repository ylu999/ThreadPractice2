import java.util.Timer;
import java.util.TimerTask;


public class TraditionalTimerTask {

	public static void main(String[] args) {
		
		final Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				System.out.println("Hello");
				timer.cancel();
			}}, 2000);
	}

}
