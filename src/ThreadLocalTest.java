import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ThreadLocalTest {
	static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	public static void main(String[] args) {
		for(int i=0;i<2;i++){//两个Thread
			new Thread(new Runnable(){
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()+" has put data:"+data);
					x.set(data);
					new A().get();
					new B().get();
					x.remove();
				}
				
			}).start();
		}
	}
	static class A{
		public int get(){
			int data = x.get();
			System.out.println("A from "+Thread.currentThread().getName()+" has put data:"+data);
			return data;
		}
	}
	static class B{
		public int get(){
			int data = x.get();
			System.out.println("B from "+Thread.currentThread().getName()+" has put data:"+data);
			return data;
		}
	}
}
