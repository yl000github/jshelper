package enabler.thread;

public class ThreadEnabler {
	public static void newThread(String jsFilePath,String param){
		MyThread thread=new MyThread(jsFilePath, param);
		thread.start();
	}
}
