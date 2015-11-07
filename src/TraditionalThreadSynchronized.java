
public class TraditionalThreadSynchronized {

	public static void main(String[] args) {
		new TraditionalThreadSynchronized().init();
	}
	private void init(){
		final Outputer outputer = new Outputer();
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					outputer.output("luyushan");
				}
			}
		}).start();
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					outputer.output("aaaaaaa");
				}
			}
		}).start();
	}
	class Outputer{
		public  void output(String name){
			int len = name.length();
			synchronized(this){
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
	}
}
