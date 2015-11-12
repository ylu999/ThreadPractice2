import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class CachDemo {
	private final Map<String,Object> cache= new HashMap<String,Object>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	private int readCount = 0;
	public static void main(String[] args) {
		final CachDemo cd = new CachDemo();
		for(int i=0;i<3;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					int i=0;
					while(true){
						//String key = ""+(int)(100*Math.random());
						String key = ""+i++;
						System.out.println("Key:"+key+" Value:"+cd.getData(key));
						cd.readCount++;
						if(cd.cache.size()==100){
							System.out.println(cd.readCount);
							break;
						}
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}				
				}
				
			}).start();
		}
	}
	public Object getData(String key){ //classic CacheData function
		rwl.readLock().lock();
		Object value = null;
		try{
			value = cache.get(key);
			if(value==null){
				//Must release read lock before acquiring write lock
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				value = cache.get(key);
				// Recheck state because another thread might have acquired write lock and changed state before we did.
				if(value==null){
					//System.out.println("New value added for key: "+key);
					value = "aaaa"; //query get value
					cache.put(key, value); // set to cache
				}
				else{
					System.out.println("Doublecheck found competition on key: "+key);
				}
				//Downgrade by acquiring read lock before releasing write lock
				rwl.readLock().lock();
				rwl.writeLock().unlock();
			}
		}
		finally{
			rwl.readLock().unlock();
		}
		return value;
	}
}
