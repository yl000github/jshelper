package robot.enabler;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import exception.ErrorException;
import exception.InfoException;
import robot.operation.event.*;
import utils.LogUtil;

public class Reappear{
	IConsume [] consumers;
	public Reappear() {
		consumers=new IConsume[]{
				new MousePress(),new MouseRelease(),	
				new MouseMove(),new MouseWheel(),	
				new MouseClick(),new MouseDrag(),
			new KeyPress(),new KeyRelease(),	
			new KeyClick()
		};
	}
	Date startTime;
	protected long getCurTime() throws ErrorException{
		if(startTime==null) throw new ErrorException("起始时间未初始化");
		return new Date().getTime()-startTime.getTime();
	}
	String path;
	//因为模拟恐怕要有少许停顿
	private void pause(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}
	//耗时任务
	public void action(String path,long duration) throws Exception{
		LogUtil.p("开始重现");
		startTime=new Date(); 
		Reader r=new FileReader(path);
		BufferedReader reader=new BufferedReader(r);
		String msg;
		long now=System.currentTimeMillis();
		while((msg=reader.readLine())!=null){
			try { 
				consumeLine(msg);
				if((System.currentTimeMillis()-now)>duration) break;
			} catch (Exception e) {
				//有问题的行先仅输出
				e.printStackTrace();
			}
		}
		reader.close();
		LogUtil.p("结束重现");
	}
	public boolean consumeLine(String msg) throws Exception{
		String time=getValue(msg,"currentTime");
		Long t=Long.parseLong(time);
		long cur=getCurTime();
		if(t>cur){
			Thread.sleep(t-cur);
		}else{ 
			startTime=new Date(startTime.getTime()+cur-t);
		}
		for (int i = 0; i < consumers.length; i++) {
			try {
				if(consumers[i].consume(msg)) return true;
			} catch (Exception e) {
				//有问题，继续,罗列原因
//				e.printStackTrace();
			}
		}
		throw new ErrorException("该行未被处理："+msg);
	}
	public String getValue(String msg,String key) throws InfoException{
		int keyIndex=msg.indexOf(key);
		if(keyIndex==-1) throw new InfoException("找不到"+key);
		int commaIndex=msg.substring(keyIndex).indexOf(",");
		if(commaIndex!=-1){
			//表明不是最后一组
			String value=msg.substring(keyIndex+key.length()+1, keyIndex+commaIndex);
			return value;
		}else{
			//表明是最后一组
			String value=msg.substring(keyIndex+key.length()+1, msg.length());
			return value;
		}
	}
}
