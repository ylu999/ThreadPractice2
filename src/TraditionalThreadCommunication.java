/*
 * 主线程和子线程轮流执行
 * 1. 线程交互代码放入同一个类，与线程分开
 * 2. wait notify， 为防止假唤醒， 用while(shouldSub)
 */
public class TraditionalThreadCommunication {

	public static void main(String[] args) {
		final Business business = new Business();
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

}
class Business{
	private boolean shouldSub = true;
	public void sub(int i){
		synchronized(this){
			while(!shouldSub){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int j=0;j<=10;j++){
				System.out.println("Sub thread sequece of "+j+", loop of "+i);
			}
			shouldSub = false;
			notify();
		}
	}
	public void main(int i){
		synchronized(this){
			while(shouldSub){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int j=0;j<=10;j++){
				System.out.println("Main thread sequece of "+j+", loop of "+i);
			}
			shouldSub = true;
			this.notify();
		}
	}
}