import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 
 */
public class ThreeConditionThreadCommunication {

	public static void main(String[] args) {
		final ThreeConditionThreadCommunication.Business business = new ThreeConditionThreadCommunication.Business();
		new Thread(new Runnable(){
			@Override
			public void run(){
				for(int i=0;i<50;i++){
					business.sub2(i);
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run(){
				for(int i=0;i<50;i++){
					business.sub3(i);
				}
			}
		}).start();
		for(int i=0;i<50;i++){
			business.main(i);
		}
	}
	static class Business{
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		private int shouldSub = 1;
		public void sub2(int i){
			lock.lock();
			try{
				while(shouldSub!=2){ // prevent self wake up
					
					try {
						condition2.await(); //await
						//this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				for(int j=0;j<=10;j++){
					System.out.println("Sub2 thread sequece of "+j+", loop of "+i);
				}
				shouldSub = 3;
				condition3.signal();//signal
				//notify();
			}
			finally{
				lock.unlock();
			}
		}
		public void sub3(int i){
			lock.lock();
			try{
				while(shouldSub!=3){ // prevent self wake up
					
					try {
						condition3.await(); //await
						//this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				for(int j=0;j<=20;j++){
					System.out.println("Sub3 thread sequece of "+j+", loop of "+i);
				}
				shouldSub = 1;
				condition1.signal();//signal
				//notify();
			}
			finally{
				lock.unlock();
			}
		}
		public void main(int i){
			lock.lock();
			try{
				while(shouldSub!=1){ // prevent self wake up
					
					try {
						condition1.await();
						//this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				for(int j=0;j<=10;j++){
					System.out.println("Main thread sequece of "+j+", loop of "+i);
				}
				shouldSub = 2;
				condition2.signal();
				//this.notify();
			}
			finally{
				lock.unlock();
			}
		}
	}
}
