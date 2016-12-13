package robot.enabler;

import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseWheelEvent;

import exception.ErrorException;
import utils.FileUtil;
/**
 * 键盘监听，记录日志
 * @author Administrator
 *
 */
public abstract class ABasic implements IHook{
	
	Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	public ABasic(){
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
	}
	protected long getCurTime() throws ErrorException{
		if(recordStartTime==null) throw new ErrorException("起始时间为初始化");
		return new Date().getTime()-recordStartTime.getTime();
	}
	Date recordStartTime;
	String path;
	protected boolean isRecord=false;
	private void store(String msg){ 
//		System.out.println(isRecord);
		if(isRecord){
			long t;
			try { 
				t=getCurTime();
			} catch (ErrorException e) {
				throw new RuntimeException(e.getMessage());
			}
			msg+=",currentTime="+t;
			FileUtil.writeAdd(path, msg+"\n");
		}
	}
	protected void sleep(long duration){
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	protected void listenHook() throws NativeHookException{
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);
	}
	protected void unListenHook() throws NativeHookException{
		GlobalScreen.unregisterNativeHook();
	}
	
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		store(e.paramString());
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		store(e.paramString());		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		store(e.paramString());		
	}
	@Override	
	public void nativeMouseClicked(NativeMouseEvent e) {
		store(e.paramString());		
	}
	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		store(e.paramString());		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		store(e.paramString());		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		store(e.paramString());		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		store(e.paramString());		
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		store(e.paramString());		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		store(e.paramString());		
	}
	

}
