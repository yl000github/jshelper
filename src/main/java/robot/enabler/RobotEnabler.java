package robot.enabler;

import java.io.IOException;
import java.util.Date;

import org.jnativehook.NativeHookException;

import utils.FileUtil;
import utils.LogUtil;

public class RobotEnabler extends ABasic{
	private static RobotEnabler instance;
	static{
		instance=new RobotEnabler();
	}
	Reappear reappear=new Reappear();
	public RobotEnabler() {
//		try {
//			unListenHook();
//			listenHook();
//		} catch (NativeHookException e) {
//			e.printStackTrace();
//		}
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
//		System.out.println("finalize");
//		unListenHook();
	}
	public static void record(String filePath,long duration,long startWait){
		System.out.println("record");
		try {
			Thread.sleep(startWait);
			instance._record(filePath, duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void reappear(String filePath,long duration,long startWait) {
		System.out.println("reappear");
//		System.out.println("filePath--"+filePath);
//		System.out.println("duration--"+duration);
//		System.out.println("startWait--"+startWait);
		try {
			Thread.sleep(startWait);
			instance._reappear(filePath, duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private  void _record(String filePath,long duration) throws IOException, InterruptedException, NativeHookException{
		path=filePath;
		FileUtil.createFile(path,true);  
		listenHook();
		isRecord=true;
		LogUtil.p("开始记录");
		recordStartTime=new Date();
//		long now=System.currentTimeMillis();
		sleep(duration);
//		isRecord=false;
		unListenHook();
		LogUtil.p("结束记录");
	}
	
	private void _reappear(String filePath,long duration) throws Exception{
		reappear.action(filePath, duration);
	}
	public static void main(String[] args) {
		long t=5000;
		long w=1000;
		record("e:/record1.txt",t,w);
		reappear("e:/record1.txt",t,w);
//		reappear("e:/omp.txt",5000,2000);
	}
}
