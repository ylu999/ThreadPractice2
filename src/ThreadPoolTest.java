import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ThreadPoolTest {

	public static void main(String[] args) {
		//ExecutorService threadPool = Executors.newFixedThreadPool(3); //Fixed number of running thread
		//ExecutorService threadPool = Executors.newCachedThreadPool(); //Size auto adjusted
		ExecutorService threadPool = Executors.newSingleThreadExecutor(); //Auto restart when the thread dead
		for(int i=0;i<10;i++){
			final int taskNum = i;
			threadPool.execute(new Runnable(){
				@Override
				public void run() {
					for(int j=0;j<=10;j++){
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+" is looping of "+ j + " for task "+ taskNum);
					}
				}
				
			});
		}
		
		Executors.newScheduledThreadPool(3).schedule(new Runnable(){
			@Override
			public void run(){
				System.out.println("bombing!");
			}
		},10,TimeUnit.SECONDS);

		ScheduledExecutorService schecudeServices = Executors.newScheduledThreadPool(1);
		schecudeServices.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run(){
				System.out.println("bombing1!");
			}
		},6,1,TimeUnit.SECONDS);
		schecudeServices.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run(){
				System.out.println("bombing2!");
			}
		},6,2,TimeUnit.SECONDS);
		schecudeServices.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run(){
				System.out.println("bombing3!");
			}
		},6,3,TimeUnit.SECONDS);
		schecudeServices.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run(){
				System.out.println("bombing4!");
			}
		},6,4,TimeUnit.SECONDS);
	}

}
