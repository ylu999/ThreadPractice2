import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;


public class CallableAndFuture {
	public static void main(String[] args){
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Callable<String> c = new Callable<String>(){
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "hello";
			}
			
		};
		System.out.println("Wait for result");
		Future<String> result = threadPool.submit(c);
		while(!result.isDone()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Asked once");
		}
		
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		completionServiceTest();
	}
	public static void completionServiceTest(){ //get all result to a queue, based on completion sequence
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		BlockingQueue<Future<String>> queue = new LinkedBlockingQueue<Future<String>>();
		CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPool,queue);
		for(int i=1;i<=10;i++){
			final int num = 1+(int)(Math.random()*10);
			//System.out.println("Random Num "+ num);
			completionService.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					Thread.sleep(num);
					return Thread.currentThread().getName()+" sleep time: "+num;
				}
				
			});
		}
		/*
		try {
			System.out.println("Winning Thread:"+completionService.take().get());
			Future<String> f;
			while(( f = completionService.take())!=null){
				System.out.println(f.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		*/
		while(queue.size()<10){}
		System.out.println("============================================");
		while(!queue.isEmpty()){
			try {
				System.out.println(queue.poll().get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
