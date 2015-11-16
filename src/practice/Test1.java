package practice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test1 {

	public static void main(String[] args) {		
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(16);
		for(int i=0;i<4;i++){
			Thread t = new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						try {
							parseLog(queue.take());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
		}
		for(int i=0;i<16;i++){
			try {
				queue.put(""+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected static void parseLog(String log) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(log+":"+System.currentTimeMillis()/1000);
	}

}
