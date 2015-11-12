import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 主线程和�?线程轮�?执行
 * 1. 线程交互代�?放入�?�一个类，与线程分开
 * 2. wait notify， 为防止�?�唤醒， 用while(shouldSub)
 * 
 * Replace wait notify with Condition class
 */
public class ConditionThreadCommunication {

	public static void main(String[] args) {
		final ConditionThreadCommunication.Business business = new ConditionThreadCommunication.Business();
		new Thread(new Runnable(){
			@Override
			public void run(){
				for(int i=0;i<50;i++){
					business.sub(i);
				}
			}
		}).start();
		for(int i=0;i<50;i++){
			business.main(i);
		}
	}
	static class Business{
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		private boolean shouldSub = true;
		public void sub(int i){
			lock.lock();
			try{
				while(!shouldSub){ // prevent self wake up
					
					try {
						condition.await(); //await
						//this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				for(int j=0;j<=10;j++){
					System.out.println("Sub thread sequece of "+j+", loop of "+i);
				}
				shouldSub = false;
				condition.signal();//signal
				//notify();
			}
			finally{
				lock.unlock();
			}
		}
		public void main(int i){
			lock.lock();
			try{
				while(shouldSub){ // prevent self wake up
					
					try {
						condition.await();
						//this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				for(int j=0;j<=10;j++){
					System.out.println("Main thread sequece of "+j+", loop of "+i);
				}
				shouldSub = true;
				condition.signal();
				//this.notify();
			}
			finally{
				lock.unlock();
			}
		}
	}
}

/*
 * Classic usage example from API
 */
class BoundedBuffer {
	   final Lock lock = new ReentrantLock();
	   final Condition notFull  = lock.newCondition(); 
	   final Condition notEmpty = lock.newCondition(); 

	   final Object[] items = new Object[100];
	   int putptr, takeptr, count;

	   public void put(Object x) throws InterruptedException {
	     lock.lock();
	     try {
	       while (count == items.length)
	         notFull.await();
	       items[putptr] = x;
	       if (++putptr == items.length) putptr = 0;
	       ++count;
	       notEmpty.signal();
	     } finally {
	       lock.unlock();
	     }
	   }

	   public Object take() throws InterruptedException {
	     lock.lock();
	     try {
	       while (count == 0)
	         notEmpty.await();
	       Object x = items[takeptr];
	       if (++takeptr == items.length) takeptr = 0;
	       --count;
	       notFull.signal();
	       return x;
	     } finally {
	       lock.unlock();
	     }
	   }
	 }
