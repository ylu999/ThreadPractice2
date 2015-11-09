
public class MultiThreadShareData {

	public static void main(String[] args) {
		ShareData sd = new ShareData();
		new Thread(new ShareDataRunnableInc(sd)).start();
		new Thread(new ShareDataRunnableDec(sd)).start();
	}
}
class ShareData{
	private int count = 1;
	public synchronized void increment(){
		count++;
		System.out.println(count);
	}
	public synchronized void decrement(){
		count--;
		System.out.println(count);
	}
}
class ShareDataRunnableInc implements Runnable{
	private ShareData sd;
	public ShareDataRunnableInc(ShareData sd){
		this.sd = sd;
	}
	@Override
	public void run() {
		while(true){
			sd.increment();
		}
	}
}

class ShareDataRunnableDec implements Runnable{
	private ShareData sd;
	public ShareDataRunnableDec(ShareData sd){
		this.sd = sd;
	}
	@Override
	public void run() {
		while(true){
			sd.decrement();
		}
	}
}