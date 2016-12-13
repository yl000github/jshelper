package robot.operation.study;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import robot.enabler.Reappear;
import utils.QueueUtil;

public class StudyMechanism implements NativeKeyListener{
	Reappear reappear=new Reappear();
	Queue<String> first=new LinkedList<>();
	Queue<String> second=new LinkedList<>();
	int round;
	Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	public StudyMechanism(){
		logger.setLevel(Level.OFF); 
		logger.setUseParentHandlers(false);
	}
	public static void main(String[] args) {
		StudyMechanism sm=new StudyMechanism();
		try {
			sm.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int getCircle(){
		if(first.size()>=2) return 2;
		else return 1;
	}
	private void parse(String msg){
		if(getCircle()==1){
			first.add(msg);
		}else{
			second.add(msg);
		}
	}		
	public void showResult(){
		StringBuffer sb=new StringBuffer();
		String result;
		if(QueueUtil.compare(first, second)){
			result="成功";
		}else{
			result="失败";
		}
		sb.append("真实按键对应的报文\n");
		while(!first.isEmpty()){
			sb.append(first.poll());sb.append("\n");
		}
		sb.append("模拟按键对应的报文\n");
		while(!second.isEmpty()){
			sb.append(second.poll());sb.append("\n");
		}
		sb.append(result);sb.append("\n");
		System.out.println(sb);
	}
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		parse(e.paramString());
	} 
	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		parse(e.paramString());		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
	public void recordStart() throws Exception {
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(this);
	}
	public void recordStop() throws Exception {
		GlobalScreen.unregisterNativeHook();
	}
	public void process() throws Exception{
		recordStart();
		Thread.sleep(2000);//按键
		simulate();
		System.out.println("输出结果");
		showResult();
		recordStop();
	}		
	private void simulate() {
		for (String string : first) {
			try {
				reappear.consumeLine(string);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}  //;;''''[[]]\\\ 99..++//**--
		
		
		
	}
}
