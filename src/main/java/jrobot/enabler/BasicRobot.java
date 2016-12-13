package jrobot.enabler;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.LogUtil;

public class BasicRobot extends Robot{
	private Dimension dim;
    public double width;
    public double height;
	public BasicRobot() throws AWTException {
		super();
		dim=Toolkit.getDefaultToolkit().getScreenSize();
        width=dim.getWidth();
        height=dim.getHeight();
//        System.out.println(width+"*"+height);
	}
	/**
	 * 输入中文句子,配合unicode输入法
	 * @param src
	 */
	public void inputText(String src){
		for (int i = 0; i < src.length(); i++) {
			char c=src.charAt(i);
			if(c>='0'&&c<='9'||c=='#'||isInSymbol(c)){
				keyClick(KeyEvent.VK_CAPS_LOCK);
				inputChar(c);
				keyClick(KeyEvent.VK_CAPS_LOCK);
			}else if(c>='a'&&c<='z'){
				keyClick(KeyEvent.VK_CAPS_LOCK);
//				inputChar(c);
				inputChar((char)(c-32));
				keyClick(KeyEvent.VK_CAPS_LOCK);
			}else if(c>='A'&&c<='Z'){
				keyClick(KeyEvent.VK_CAPS_LOCK);
//				inputChar(c);
				inputChar((char)(c+32));
				keyClick(KeyEvent.VK_CAPS_LOCK);
			}else{
				inputChar(c);
			}
		}
	}
	protected void pause(){
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	public void mouseLeftClick(){
		this.mousePress(MouseEvent.BUTTON1_MASK);
		this.mouseRelease(MouseEvent.BUTTON1_MASK);
		pause();
	}
	public void mouseRightClick(){
		int t=MouseEvent.BUTTON3_MASK;
		this.mousePress(t);
		this.mouseRelease(t);
		pause();
	}
	protected void _mouseClick(String type,int times){
		int t;
		if(type.equals("left")){	
			t=MouseEvent.BUTTON1_MASK;
		}else{
			t=MouseEvent.BUTTON3_MASK;
		}
		for (int i = 0; i < times; i++) {
			this.mousePress(t);
			this.mouseRelease(t);
			pause();
		}
	}
	protected void keyClick(int c){
		this.keyPress(c);
		this.keyRelease(c);
		pause();
	}
	public void enter(){
		this.keyPress(KeyEvent.VK_ENTER);
		this.keyRelease(KeyEvent.VK_ENTER);
	}
	protected boolean isIn(int goal,int min,int max){
		return goal>=min&&goal<=max;
	}
	int []symbol=new int[]{
			KeyEvent.VK_SPACE,KeyEvent.VK_COMMA,KeyEvent.VK_PERIOD,
			KeyEvent.VK_MINUS,KeyEvent.VK_EQUALS
	};
	private boolean isInSymbol(int code){
		for (int i = 0; i < symbol.length; i++) {
			if(code==symbol[i]) return true;
		}
		return false;
	}
	/**
	 * 有效范围为：0-9,a-z,A-Z,少数标点,其他的可以扩充
	 * @param c
	 */
	private void inputChar(char c){
		if(isIn(c,48,57)){
			//0-9
			keyClick(c);
		}else if(isIn(c,65,90)){
			//A-Z
			this.keyPress(KeyEvent.VK_SHIFT);
			this.keyPress(c);
			this.keyRelease(KeyEvent.VK_SHIFT);
			this.keyRelease(c);
		}else if(isIn(c,97,122)){
			keyClick(c-32);
		}else if(isInSymbol(c)){
//			keyClick(KeyEvent.VK_SPACE);
			keyClick(c);
		}else if(isIn(c,0x4E00,0x9FA5)){
			//基本汉字,获取unicode，必须配合unicode输入法使用
			String s=Integer.toHexString(c);
			System.out.println(s);
			for (int i = 0; i < s.length(); i++) {
				char sc=s.charAt(i);
				inputChar(sc);
			}
//			keyClick(KeyEvent.VK_SPACE);
		}else if(c=='#'){
			//有问题
			keyClick(KeyEvent.VK_NUMBER_SIGN);
		}else{
			//not support  TODO
			
		}
	}
	public void move(int x,int y){
		this.mouseMove(x, y);
	}
	public void openQQ(){
		keyPress(KeyEvent.VK_CONTROL);
		keyPress(KeyEvent.VK_ALT);
		keyPress(KeyEvent.VK_Z);
		keyRelease(KeyEvent.VK_CONTROL);
		keyRelease(KeyEvent.VK_ALT);
		keyRelease(KeyEvent.VK_Z);
	}
	public void screenShot() throws IOException{
		BufferedImage image=createScreenCapture(new Rectangle(0, 0, (int)width, (int)height));
		ImageIO.write(image,"png",new File("e:/screenShot.png"));
	}
	public void screenShot(int sX,int sY,int width,int height) throws IOException{
		BufferedImage image=createScreenCapture(new Rectangle(sX, sY, width, height));
		ImageIO.write(image,"png",new File("e:/screenShot.png"));
	}
	public void copy(){
		keyPress(KeyEvent.VK_CONTROL);
		keyPress(KeyEvent.VK_C);
		pause();
		keyRelease(KeyEvent.VK_C);
		keyRelease(KeyEvent.VK_CONTROL);
	}
	public void paste(){
		keyPress(KeyEvent.VK_CONTROL);
		keyPress(KeyEvent.VK_V);
		keyRelease(KeyEvent.VK_CONTROL);
		keyRelease(KeyEvent.VK_V);
	}
	public void switchPage() throws InterruptedException{
		keyPress(KeyEvent.VK_ALT);
		keyPress(KeyEvent.VK_TAB);
		Thread.sleep(100);
		keyRelease(KeyEvent.VK_ALT);
		keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(100);
	}	
	public void switchDesk() throws InterruptedException{
		keyPress(KeyEvent.VK_WINDOWS);
		keyPress(KeyEvent.VK_D);
		Thread.sleep(100);
		keyRelease(KeyEvent.VK_WINDOWS);
		keyRelease(KeyEvent.VK_D);
		Thread.sleep(100);
	}	
	protected void twoKey(int key1,int key2) throws InterruptedException{
		keyPress(key1);
		keyPress(key2);
		Thread.sleep(100);
		keyRelease(key1);
		keyRelease(key2);
	}
	public void ctrlA() throws InterruptedException{
		twoKey(KeyEvent.VK_CONTROL,KeyEvent.VK_A);
		Thread.sleep(100);
	}
	public void qqSend() throws InterruptedException{
		twoKey(KeyEvent.VK_ALT,KeyEvent.VK_S);
	}
	public void inputNumber(String number){
		for (int i = 0; i < number.length(); i++) {
			char c=number.charAt(i);
			inputChar(c);
		}
	}
	//成功率好有限啊
	public String getMessage(int sX1,int sY1,int eX1,int eY1) throws Exception{
		int sX=(int)sX1;
		int sY=(int)sY1;
		int eX=(int)eX1;
		int eY=(int)eY1;
//		mouseMove((sX+eX)/2,(sY+eY)/2);
//		mouseLeftClick();
		mouseMove(sX,sY);
		this.mousePress(MouseEvent.BUTTON1_MASK);
		mouseFromTo(sX,sY,eX,eY);
		this.mouseRelease(MouseEvent.BUTTON1_MASK);
		Thread.sleep(500);//机器人的所有行为最好有个间隔时间，要不然好容易出错
		copy();
		String content=ClipboardOperate.getClipboardText();
		LogUtil.p("getMessage",content);
		return content;
	}
	private void mouseFromTo(int sX,int sY,int eX,int eY) throws InterruptedException{
		//模拟人手慢慢移动的特性
		int totalTime=500;
		int count=100;int x,y;
		int timeSpace=totalTime/count;
		float dX=(eX-sX)/(float)count;
		float dY=(eY-sY)/(float)count;
		for (int i = 0; i <= count; i++) {
			long currentTime=System.currentTimeMillis();
			x=(int) (sX+dX*i);
			y=(int) (sY+dY*i);
			mouseMove(x, y);
			long nowTime=System.currentTimeMillis();
			if((nowTime-currentTime)<timeSpace){
				Thread.sleep(timeSpace-(nowTime-currentTime));
			}
		}
	}
	public void moveAndClick(int x,int y){
		mouseMove(x, y);
		mouseLeftClick();
	}
	public void clearClipboard() throws Exception{
		ClipboardOperate.setClipboardText("");
	}
	public String getClipboard() throws Exception{
		return ClipboardOperate.getClipboardText();
	}
	public static void main(String[] args) throws Exception {
		System.out.println((char)('a'-32));
		BasicRobot a=new BasicRobot();
		a.switchDesk();
//		a.mouseMove((int)a.width/2, (int)a.height/2);
//		a.mousePress(MouseEvent.BUTTON1_MASK);
//		a.mousePress(MouseEvent.BUTTON1_MASK);
//		a.mousePress(MouseEvent.BUTTON1_MASK);
//		a.qiehuan();
//		String content=a.getMessage(54, 169, 441, 599);
//		System.out.println(content);
//		a.qiehuan();
//		a.inputText("startSTART");
//		char c='a';
//		a.keyClick(KeyEvent.VK_CAPS_LOCK);
////		a.inputChar(c);
//		a.inputChar((char)(c-32));
//		a.keyClick(KeyEvent.VK_CAPS_LOCK);
	}


}
