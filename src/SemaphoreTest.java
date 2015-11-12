import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3,true);
		for(int i=0;i<10;i++){
			service.execute(new Runnable(){
				@Override
				public void run() {
					try {
						sp.acquire();
						System.out.println(Thread.currentThread().getName()+" IN");
						Thread.sleep(200);
						System.out.println(Thread.currentThread().getName()+" OUT");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally{
						sp.release();
					}
				}});
		}
	}

}
