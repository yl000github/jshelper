package enabler.jrobot;

import java.awt.AWTException;

/**
 * 单向性的操作，不考虑互动
 * @author Administrator
 *
 */
public class JRobotEnabler extends BasicRobot{
	 
	public JRobotEnabler() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 支持
	 * 中文--需输入法支持
	 * 英文、数字、某些特殊符号
	 * @param src
	 */
	public void inputText(String src){
		super.inputText(src);
	}
	public void keyClick(String src){
		int c=KeyMap.map(src);
		super.keyClick(c);
	}
	/**
	 * 支持常用快捷键
	 * 切换、切到桌面、打开qq、qq发送信息
	 * @param code
	 * @throws Exception 
	 */
	public void shortCut(String code) throws Exception{
		switch (code) {
		case "switchPage":
			this.switchPage();
			break;
		case "switchDesk":
			this.switchDesk();
			break;
		case "qqOpen":
			this.openQQ();
			break;
		case "qqSend":
			this.qqSend();
			break;
		case "screenShot":
			this.screenShot();
			break;
		case "backTab":
			this.backTab();
			break;
		}
	}
	/**
	 * 参数取值0-1
	 * @param sX
	 * @param sY
	 * @param eX
	 * @param eY
	 * @return
	 * @throws Exception
	 */
	public String getRegionText(Object sX,Object sY,Object eX,Object eY) throws Exception{
		return this.getMessage(ob2int(sX,width), ob2int(sY,height), ob2int(eX,width), ob2int(eY,height));
	}
	//鼠标类
	/**
	 * 
	 * @param type  left right
	 * @param x
	 * @param y
	 * @param times
	 */
	public void mouseClick(String type,int times){
//		LogUtil.p("ob2int(x,width)",ob2int(x,width));
//		LogUtil.p("ob2int(y,height)",ob2int(y,height));
		super._mouseClick(type , times);
	}
	private int ob2int(Object o,double len){
		if(o instanceof Integer){
			return (int) o;
		}
		if(o instanceof Double){
			return (int) (((Double) o)*len);
		}
		return 0;
	}
	public void sleep(long t){
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param isPress 左键是否点击
	 */
	public void mouseMove(Object x,Object y,boolean isPress){
		super.mouseMove(ob2int(x,width), ob2int(y,height));
		if(isPress){
			super.mouseLeftClick();
		}
	}
	public static void main(String[] args) throws Exception {
		JRobotEnabler enabler=new JRobotEnabler();
		enabler.shortCut("switchPage");
//		enabler.shortCut("switchPage");
//		enabler.shortCut("switchDesk");
//		enabler.shortCut("qqOpen");
//		enabler.shortCut("qqSend");
//		enabler.shortCut("screenShot");
//		enabler.getRegionTextRate(0.4, 0.3, 0.6, 0.7);
//		enabler.mouseClick("left", 1);
//		enabler.mouseMove(0.5, 0.5, true);
//		enabler.mouseMove(17, 30, true);
//		enabler.getRegionText(0.3, 0.3, 0.6, 0.6);
		
//		enabler.mouseMove(0.5, 0.5, false);
//		enabler.inputText("-=【】、；‘，。、");	
		enabler.inputText("一，");	
//		enabler.keyClick("Tab");
		//-=,.
		//-=
	}
}
