import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReadWriteLockTest {

	public static void main(String[] args) {
		final Data data = new Data();
		for(int i=0;i<3;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						data.get();
					}
				}
			}).start();
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						data.set();
					}
				}
			}).start();
		}
	}

}
class Data{
	private int value;
	ReadWriteLock rwl = new ReentrantReadWriteLock();
	public void get() {
		rwl.readLock().lock();
		try {
			System.out.println("Start to Read");
			Thread.sleep(20);
			System.out.println("Value read: "+value);
			System.out.println("Finish Reading");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			rwl.readLock().unlock();
		}
	}

	public void set() {
		rwl.writeLock().lock();
		System.out.println("Start to Set");
		try {
			Thread.sleep(20);
			value = (int)(Math.random()*100);
			System.out.println("Value set: "+value);
			System.out.println("Finish Setting");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			rwl.writeLock().unlock();
		}
	}
	
}